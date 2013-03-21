# Architecture Notes

## CAP
- Multiverse requires partition tolerance for horizontal scalability.
- Consistency and availability requirements vary between subsystems. E.g. battles must be consistent, whereas trade does not.
- This can be architected using the DDD concept of 'aggregates'.

### Aggregates
- All objects belong to an aggregate.
- The root object in each aggregate is known as the aggregate root (AR).
- Aggregate roots are acquired through repositories.
- Each entry in the event log is associated with one aggregate using the ID of the aggregate root.
- Optimistic concurrency used when committing new events for a given aggregate.
- *Optimisation:* Single event aggregates (or 'atomic aggregates') do not require conflict checking. 
- *Optimisation 2:* Aggregates which do not perform validation do not need to be loaded from history or a snapshot; there is no state within the aggregate.
### Scaling and Load Balancing
- Sharding based on aggregate ID.
- Queries spanning multiple aggregates are considered eventually consistent.
- Queries on single aggregates are considered strongly consistent.
- Provides horizontal scalability - load balancing across comodity hardware.

### Redundancy and Geolocation
- Latency reduced for users by introducing replicas in near datacenters.
- This also minimises impact of datacenter failure.
- Strongly consistent replication must be used to ensure aggregates remain internally consistency.
- *Optimisation:* Weaker consistency rules can be used for replicating events for atomic aggregates while still providing strong internal consistency. This is only safe *if* the event is not pinged, as this would give other aggregates premature knowledge of the aggregate before it may exist in their cluster.

### Google App Engine
GAE uses Paxos to achieve a distributed 2PC, ensuring strong consistency between datacenters.

### Cassandra
Cassandra uses variable consistency for fine tunable read/write performance with varying consistency guarantees.

Users define how many replicas must synchronously receive each write: none, one, quorum, all.

Reads are also tunable: one, quorum, all.

#### Strong consistency (R + W > N)
- High resilience fast read: R(One) W(All)
- Medium resilience: R(Quorum) W(Quorum)
- Low resilience fast write: R(All) W(One)

#### Eventual consistency
- Medium resilience fast read: R(One) W(Quorum)
- Low resilience fast read/write: R(One) W(One)
	- Typical multi-master replication.

#### Weak consistency
- No sync writes.
- Impacted by non-server failures. 
- Ideal if write is never gauranteed.
- Memcache will provide this, which is provided by GAE.

## Ping Events
- Users subscribe to receive events from a subset of servers, since subscribing to all servers is not scalable.
- Sometimes interesting events occur on unsubscribed servers.
- These events are relayed to subscribed servers by raising *ping* events against aggregates which are known to be subscribed against by interested parties.
- See ship journey events and the *GetShipsInSolarSystem* query for an example.

## Behaviour Versioning
- Behavior may change between versions.
- Instances will often run on different versions.
- Event versions will bleed within the event log. E.g. 1, 1, 2, 1, 2, 2.
- Denormalizer must switch between versions when processing events.
- Snapshots can be used to reduce amount of versions of runnable logic required.

## Query Processing
- Canonical logic exists server-side.
- Used to validate commands and to satisfy client requests.
- Certain queries can be duplicated client-side to remove need to keep polling server.

## Abstraction
- Events, identities, entities *and* aggregates are concrete classes. All must be easily serializable and none contain non-domain-model implementation that is likely to change.

## Time
- Ordering of event sequences is handled by the underlying persistence layer.
- Timestamps still need to be present in certain events for reactions to be timed.
- To support adding reactions to events which otherwise have no reactions, timestamps shall be present in all events.
- **Important:** Timestamps are *not* present to support deferred commands. Commands produce events which represent immediate state change; a timestamp set for any time other than the present is a bug.
