package io.multiverse.domain.model.resource

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.shared.{ShortAlphabeticName, IntegralPercentage}

/**
 * Resource defined.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param resourceId Unique ID for the new resource.
 * @param name Name of the resource.
 * @param description Description of the resource's unique attributes.
 * @param abundance Resource abundance, ranging from 0-100.
 */
case class ResourceDefined(instanceId:InstanceId,
                           timeStamp:Long,
                           resourceId:ResourceId,
                           name:ShortAlphabeticName,
                           description:String,
                           abundance:IntegralPercentage) extends ResourceEvent
