package io.multiverse.application

import io.multiverse.domain.model.solarSystem.SolarSystem

trait SolarSystemServiceComponent {
  def solarSystemService: SolarSystemService

  /**
   * Append-only store of aggregate-related events.
   */
  trait SolarSystemService {
    /**
     * Returns the store's inconsistent solar systems with compensation events applied.
     * @param store
     * @return
     */
    def getCompensatedSolarSystems(store: String): List[SolarSystem]
  }
}
