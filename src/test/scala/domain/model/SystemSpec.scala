package domain.model

import baseSpecifications.CommandCombinators.chainToTestChain
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.commands.CommandCombinators.aggregateToChain
import io.multiverse.domain.model.common.commands.Commit
import io.multiverse.domain.model.system.commands.{ResumeGame, PauseGame}
import io.multiverse.domain.model.system.{GameResumed, GamePaused, SystemId, System}
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * System specification.
 */
class SystemSpec extends Specification {

  "new system" should {
    "support pausing" in new SystemScope {
      (newSystem
        after PauseGame(systemId, pauseMessage, instanceId, timestamp)
        yields GamePaused(systemId, pauseMessage, instanceId, timestamp))
    }

    "support superfluous resuming" in new SystemScope {
      (newSystem
        after ResumeGame(systemId, instanceId, timestamp)
        yields GameResumed(systemId, instanceId, timestamp))
    }
  }

  "paused system" should {
    "support resuming" in new PausedSystemScope {
      (pausedSystem
        after ResumeGame(systemId, instanceId, timestamp)
        yields GameResumed(systemId, instanceId, timestamp))
    }

    "support idempotent pausing" in new PausedSystemScope {
      val updatedPauseMessage = "Paused for testing 2"
      (pausedSystem
        after PauseGame(systemId, updatedPauseMessage, instanceId, timestamp)
        yields GamePaused(systemId, updatedPauseMessage, instanceId, timestamp))
    }
  }

  /**
   * Predefined test values for new systems.
   */
  trait SystemScope extends InstanceScope {
    val systemId = SystemId(UUID.randomUUID)
    val pauseMessage = "Paused for testing"
    val newSystem = System(systemId)
  }

  /**
   * Predefined test values for paused systems.
   */
  trait PausedSystemScope extends SystemScope {
    val pausedSystem = newSystem after PauseGame(systemId, pauseMessage, instanceId, timestamp) after Commit()
  }
}
