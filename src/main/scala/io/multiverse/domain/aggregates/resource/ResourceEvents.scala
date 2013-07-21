package io.multiverse.domain.aggregates.resource

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.common.{IntegralPercentage, ShortAlphabeticName}

/**
 * Resource defined.
 * @param resourceId Unique ID for the new resource.
 * @param name Name of the resource.
 * @param description Description of the resource's unique attributes.
 * @param abundance Resource abundance, ranging from 0-100.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ResourceDefined(resourceId:ResourceId,
                           name:ShortAlphabeticName,
                           description:String,
                           abundance:IntegralPercentage,
                           instanceId:InstanceId,
                           timestamp:Long) extends ResourceEvent
