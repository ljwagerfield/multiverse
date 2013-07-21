package domain.aggregates

import _root_.common.InstanceScope
import io.multiverse.domain.aggregates.system.{GameResumed, GamePaused, SystemId, System}
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * System specification.
 */
class SystemSpec extends Specification {

  "new system" should {
    "support pausing" in new SystemScope {
      newSystem
        .pauseGame(pauseMessage, instanceId, timestamp)
        .changes must beEqualTo(List(
          GamePaused(systemId, pauseMessage, instanceId, timestamp)))
    }

    "support superfluous resuming" in new SystemScope {
      newSystem
        .resumeGame(instanceId, timestamp)
        .changes must beEqualTo(List(
          GameResumed(systemId, instanceId, timestamp)))
    }
  }

  "paused system" should {
    "support resuming" in new PausedSystemScope {
      pausedSystem
        .resumeGame(instanceId, timestamp)
        .changes must beEqualTo(List(
          GameResumed(systemId, instanceId, timestamp)))
    }

    "support idempotent pausing" in new PausedSystemScope {
      val updatedPauseMessage = "Paused for testing 2"
      pausedSystem
        .pauseGame(updatedPauseMessage, instanceId, timestamp)
        .changes must beEqualTo(List(
          GamePaused(systemId, updatedPauseMessage, instanceId, timestamp)))
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
    val pausedSystem = newSystem.pauseGame(pauseMessage, instanceId, timestamp).markCommitted
  }
}
