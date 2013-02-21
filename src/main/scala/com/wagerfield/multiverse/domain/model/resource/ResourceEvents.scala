package com.wagerfield.multiverse.domain.model.resource

import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Resource defined.
 */
case class ResourceDefined(instanceId:InstanceId, resourceId:ResourceId, name:String, description:String, rarity:Int) extends ResourceEvent