package com.wagerfield.multiverse.application

import com.wagerfield.multiverse.domain.shared.{EventToken, Event}
import scala.xml._
import java.util.UUID
import com.wagerfield.multiverse.domain.model.solarSystem.SolarSystem

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