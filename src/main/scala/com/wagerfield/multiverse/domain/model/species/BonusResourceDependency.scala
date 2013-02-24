package com.wagerfield.multiverse.domain.model.species

/**
 * Bonus resource dependency.
 * @param resourceDependency The dependable resource.
 * @param speciesBonus The bonus applied when the resource is obtained.
 */
case class BonusResourceDependency(resourceDependency:ResourceDependency, speciesBonus:SpeciesBonus)