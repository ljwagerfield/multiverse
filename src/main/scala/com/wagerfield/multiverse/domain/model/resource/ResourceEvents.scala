package com.wagerfield.multiverse.domain.model.resource

import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Resource defined.
 * @param instanceId Instance the event occurred in.
 * @param resourceId Unique ID for the new resource.
 * @param name Name of the resource.
 * @param description The resource description.
 * @param abundance The resource abundance, range from 0-100.
 */
case class ResourceDefined(instanceId:InstanceId, resourceId:ResourceId, name:String, description:String, abundance:Int) extends ResourceEvent