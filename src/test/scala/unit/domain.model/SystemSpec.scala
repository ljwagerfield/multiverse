package unit.domain.model

import _root_.shared.InstanceScope
import io.multiverse.domain.model.system.{GameResumed, GamePaused, SystemId, System}
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * System specification.
 */
class SystemSpec extends Specification {

  "new system" should {
    "support pausing" in new SystemScope {
      newSystem.pauseGame(pauseMessage, instanceId, timestamp)
        .changes must contain(GamePaused(instanceId, timestamp, systemId, pauseMessage))
    }

    "support superflous resuming" in new SystemScope {
      newSystem.resumeGame(instanceId, timestamp)
        .changes must contain(GameResumed(instanceId, timestamp, systemId))
    }
  }

  "paused system" should {
    "support resuming" in new PausedSystemScope {
      pausedSystem.resumeGame(instanceId, timestamp)
        .changes must contain(GameResumed(instanceId, timestamp, systemId))
    }

    "support idempotent pausing" in new PausedSystemScope {
      val updatedPauseMessage = "Paused for testing 2"
      pausedSystem.pauseGame(updatedPauseMessage, instanceId, timestamp)
        .changes must contain(GamePaused(instanceId, timestamp, systemId, updatedPauseMessage))
    }
  }

  /**
   * Predefined test values for new systems.
   */
  trait SystemScope extends InstanceScope {
    val systemId = SystemId(UUID.randomUUID)
    val pauseMessage = "Paused for testing"
    val newSystem = System.init(systemId)
  }

  /**
   * Predefined test values for paused systems.
   */
  trait PausedSystemScope extends SystemScope {
    val pausedSystem = newSystem.pauseGame(pauseMessage, instanceId, timestamp)
  }
}
