# Reactions
The following queries return a clock reading, denoted as 't':

- GetEngineResearchTime(SpeciesId)
- GetArmourResearchTime(SpeciesId)
- GetShieldResearchTime(SpeciesId)
- GetColonizingUnitResearchTime(SpeciesId)
- GetWeaponResearchTime(SpeciesId)
- GetShipBuildTime(ShipId)
- GetShipJourneyTime(ShipId)

# Standard

- GetCash(t, SpeciesId): Int
- GetShipsInSolarSystem(t, StarId): {ShipId,X,Y}[]
	- Immediate consistency granted through ship journey ping events against star.
- GetResearchedEngine(t, SpeciesId): EngineId, Name, Power, JumpRange, Mass, Cost
	- Speed is quotient of mass and power.
- GetResearchedArmour(t, SpeciesId): ArmourId, Name, Strength, Mass, Cost, Maintainability
- GetResearchedShield(t, SpeciesId): ShieldId, Name, Strength, Mass, Cost, Maintainability
- GetResearchedColonizingUnit(t, SpeciesId): ColonizingUnitId, Name, Capacity, Mass, Cost, Maintainability
- GetResearchedWeapon(t, SpeciesId): WeaponId, Name, Range, Type, AccuracyPercent, Damage, FireLatency, Mass, Cost, Maintainability
	- Range 1 to 4 (Short, Medium, Long, XLong)
	- Type 1 to 4 (Bomb, Rocket, Projectile, Energy)
		- Bombs only for destroying planets.
		- Rockets defended by Energy weapons
		- Energy weapons defended by shields
		- Projectile always hit if accurate.
		- Range stereotype: Rocket, Projectile, Energy
		- ROF stereotype: Energy, Projectile, Rocket
		- Accuracy stereotype: Projectile, Rocket, Energy
		- Damage stereotype: Rocket, Energy, Projectile