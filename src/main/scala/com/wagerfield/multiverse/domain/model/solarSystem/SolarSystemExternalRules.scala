package com.wagerfield.multiverse.domain.model.solarSystem

/**
 * Validation rules for solar systems which span multiple aggregates.
 */
class SolarSystemExternalRules {
	/**
	 * Returns a subset of the provided solar systems which require compensation.
	 * @param solarSystems The set of solar systems to validate.
	 * @return Solar systems with compensatory events applied. Nil implies consistent input.
	 */
	def validate(solarSystems:List[SolarSystem]):List[SolarSystem] = ???
}
