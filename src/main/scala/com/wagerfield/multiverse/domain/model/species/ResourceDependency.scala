package com.wagerfield.multiverse.domain.model.species

import com.wagerfield.multiverse.domain.model.resource.ResourceId

/**
 * Dependency of a given quantity of a particular resource.
 * @param resourceId The dependable resource.
 * @param unitSize The amount of resource required to meet the dependency.
 */
case class ResourceDependency(resourceId:ResourceId, unitSize:Int)