package io.multiverse.domain.model.common.commands

import io.multiverse.domain.model.common.{AggregateMeta, Aggregate}

/**
 * Command with an evaluation that describes the creation of a new aggregate. These commands are static by nature.
 * @tparam A Aggregate meta type.
 */
trait HeadCommand[A <: AggregateMeta]  {

  /**
   * The evaluation of this command as a new aggregate.
   */
  val evaluation: Aggregate[A]
}
