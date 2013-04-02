# Add validation to aggregate roots [WIP]

# Add unit tests [WIP]

# Refactor the application of events to overcome cyclomatic complexity

# Plan how deployment will work
- Consider behavioral and structural changes, both internal and external (i.e. schema changes, tech changes, etc.)

# Add eventually consistent queries
- Determine best composition pattern. Cake pattern seems to add verbosity.
- Determine if these be included within the domain model.
- Add queries for set validation.

# Add cargo to ship specification

# Add population data
- Ships contain a certain number of that population.
- Decommissioning then requires a ship to go back to a particular planet.

# Add player-to-player asset transfer
- One-time transfers.
- Recurring transfers.
- Resources can include: resources; cash; ships

# Add statistical queries
- The most violent players. Vigilantes could use this list to hunt down troublesome players that target trade ships.
- Security of each region. Similarly to above, self-proclaimed security forces could aim to keep this value high.

# Add sophisticated cost variance on resources
- Uncertain on details, but make a distinction between civilised space and uncivilised space.
- Players building ships can turn a higher profit building them out in the middle of nowhere.
- Perhaps organically due to low overheads on defense and abundance of resource?

# Add custom macro ability for species
- Allow species to send new ships to a specific star, or the most vulnerable, etc.

# Implement support for GAE

# Implement serialization

