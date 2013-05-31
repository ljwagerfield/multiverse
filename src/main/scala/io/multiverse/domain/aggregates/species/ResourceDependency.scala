package io.multiverse.domain.aggregates.species

import io.multiverse.domain.aggregates.resource.ResourceId

/**
 * Dependency of a given quantity of a particular resource.
 * @param resourceId The dependable resource.
 * @param unitSize The amount of resource required to meet the dependency.
 */
case class ResourceDependency(resourceId:ResourceId, unitSize:Int) {
  require(unitSize > 0, "Unit size must be greater than 0.")
}
