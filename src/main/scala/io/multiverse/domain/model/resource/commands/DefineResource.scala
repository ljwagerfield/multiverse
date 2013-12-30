package io.multiverse.domain.model.resource.commands

import io.multiverse.domain.model.resource.{ResourceDefined, Resource, ResourceCommand, ResourceId}
import io.multiverse.domain.model.common.values.{IntegralPercentage, ShortAlphabeticName}
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.common.Aggregate

/**
 * Defines a new resource within the universe.
 * @param resourceId Unique ID for new resource.
 * @param name Unique resource name within the universe.
 * @param description Description of the resource's unique attributes.
 * @param abundance Resource abundance, ranging from 0-100.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class DefineResource(resourceId:ResourceId,
                  name:ShortAlphabeticName,
                  description:String,
                  abundance:IntegralPercentage,
                  instanceId:InstanceId,
                  timestamp:Long)
  extends ResourceCommand with HeadCommand[Resource] {

  require(description.length >= 100 && description.length <= 150, "Description must be between 100 and 150 characters, inclusive.")

  /**
   * The evaluation of this command as a new aggregate.
   */
  lazy val evaluation: Aggregate[Resource] =
    Resource(ResourceDefined(resourceId, name, description, abundance, instanceId, timestamp))
}
