# Instance

## InstanceCreated
- InstanceId, Time, Version
- Former two included in all events. InstanceId used instead of NodeId as this may cause confusion between application nodes and database nodes.
- Vector clock used for time param.
- Version upgrades require instance to be abandoned and new one created.
- Instances include clients, web services and web processes.

## UserSignedIn
- SpeciesId
- Certain commands only valid if current instance has a signed-in user.

## UserSignedOut
- SpeciesId
- AI is enabled for species when not in user mode.

# Resource (Atomic)

## ResourceDefined
- ResourceId, Name, Description, Rarity
- Oxygen, Water, Crystherium Utilia, etc 

# SolarSystem

## SolarSystemCreated
- StarId, NearStarIds, PlanetIds
- 0 near stars indicates centre of new galaxy
- no limit to # of stars (milkyway contains 200-400 billion)
- cannot be near itself
- must form planar graph with max degrees 4
- planets in order from distance from star
- planet attributes dynamically generated

## PlanetNamedBySpecies
- StarId, PlanetId, SpeciesId, Name
- Name is unique within aggregate.

## StarNamed
- StarId, Name

## StarNameDuplicateRenamed
- OriginalStarId, RenamedStarId, Name

## ShipBoundForSolarSystem
- StarId, ShipId, JourneyEvent
- *Ping* event type.
- Ignore ping if last journey for ship does not match one provided. 

# SpeciesAssets (Atomic)

## SpeciesAssetsSet
- SpeciesAssetsId, Hash

# ShipAssets (Atomic)

## ShipAssetsSet
- ShipAssetsId, Size, Hash
- Id and Size form composite key
- Size 1 to 5 (scout, destroyer, transporter, carrier, leviathan)
- Evasion bonus awarded to smaller ships. Attacks suffer accuracy.

# Species

## SpeciesEvolved
- SpeciesId, SpeciesAssetsId, ShipAssetsId, PlanetId, BaseBonus, HabitatTemperature, {RequiredResourceId,UnitSize}, {BonusResourceId, UnitSize, Bonus}
- Species always existed on this planet.
- Bonus includes: ResearchFactor, WeaponAccuracyFactor
- Unit size allows, say, amphibians to require more water than land species.

# ShipResearch (Atomic)

## ShipResearchFocused
- SpeciesId, Engines, Armour, Shields, ColonizingUnits, Weapons, Economy, Size, Effectiveness, Maintainability
- Args 1 to 5 must total 100.
- Args 6 to 8 must total 100.

# ShipSpecification (Atomic)

## ShipSpecified
- SpeciesId, ShipSpecificationId, ShipAssetsId, Size, Name, EngineId, ArmourId, ShieldId, {ColonizingUnitId,Quantity}[], {WeaponId,Quantity}[], DecommissionOnColonize
- Combined mass of engine, armour, shield and weapons must not exceed limit relative to ship size

# Ship

## ShipBuildCommissioned
- PlanetId, ShipSpecificationId, ShipId
- Ships always built in serial.

## SolarSystemEntryOrdered
- ShipId, StarId
- Ship orbits wormhole on arrival.

## PlanetOrbitOrdered
- ShipId, PlanetId
- Aborts if planet becomes hostile.

## PlanetColonizationOrdered
- ShipId, PlanetId
- Aborts if planet becomes colonised.
- Ship decomissioned after final colonization.

## PlanetAttackOrdered
- ShipId, PlanetId
- Aborts if planet becomes friendly or vacant.

## ShipAttackOrdered
- ShipId, TargetShipId
- Aborts if ship becomes friendly.

## ShipCoordinatesOrdered
- ShipId, StarId, XOffset, YOffset

## ShipOrderRevoked
- ShipId

## InboundShipDetected
- ShipId, InboundShipId, JourneyEvent
- *Ping* event type.
- Ignore ping if last journey for ship does not match one provided. 

## ShipDecommissioned
- ShipId
- Ships have an incurring cost at a fraction of their price.

# PlanetOwnership

## ShipBoundForPlanet
- PlanetId, ShipId, JourneyEvent
- *Ping* event type.
- Ignore ping if last journey for ship does not match one provided. 

## PlanetAbandoned
- PlanetId