# Inter-aggregate commands

## Example: Ship build commissioning
1. We need to know which ships exist.
2. Ship existence starts with a build commissioned event. However, the ship does not exist at this point.
3. Given a build commissioned event, we need to know the current queue of builds on the commissioned planet to determine when the ship will be built.
4. Ships being commissioned before this ship affect the time it begins its existence.
5. Ship existence can end due to a number of other commands (colonise, attacked, etc)

### Attempt A:
Initialise the ship aggregate with the build commissioned event. This would require the following commands to be validated against the ship's existence:

- ship.attack
- ship.move
- ship.decommission

Disadvantage: Validation requires the aggregate to query a read model containing a (possibly denormalized) state of all existing ships. This results in a bleeding between read and write models.

### Attempt B:
Introduce validation state by issuing commands against aggregates which produce redundant events. For example, 'ShipBuilt' and 'ShipDestroyed' events could be applied to ship aggregates to allow validation to occur entirely within the aggregate (i.e. the write model).

Disadvantage: Redundant entries in event log; superfluous to read model.

### Attempt C:
Introduce redundant validation state within each aggregate by utilising its snapshot without producing any events. For example, 'ShipBuilt' and 'ShipDestroyed' events could be applied to ship aggregates to allow validation to occur entirely within the aggregate (i.e. the write model).

Disadvantage: Aggregate snapshots must be persisted after such commands are issued; potentially confusing since aggregate will have no uncommitted events; not idiomatic to DDD (snapshots should be an optimisation, not essential for validation).

# Fictitious constraints
- Pausing system example. Allow multiple pauses. Idempotency.
