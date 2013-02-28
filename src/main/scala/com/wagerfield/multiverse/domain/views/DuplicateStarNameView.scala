package com.wagerfield.multiverse.domain.views

import com.wagerfield.multiverse.domain.model.solarSystem.StarId

trait DuplicateStarNameViewComponent {
  def duplicateStarNameView: DuplicateStarNameQuery

  /**
   * Append-only store of aggregate-related events.
   */
  trait DuplicateStarNameQuery {
    /**
     * Returns the store's inconsistent solar systems with compensation events applied.
     * @param store
     * @return
     */
    def read(store: String): Set[(StarId, String)]
  }
}

