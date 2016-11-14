package scalaudio.actor.mutable.ugen

import scala.concurrent.duration._
import scalaudio.core.engine.samplewise.AmpOutput
import scalaudio.core.{AudioContext, ScalaudioConfig, ScalaudioCoreTestHarness}
/**
  * Created by johnmcgill on 6/6/16.
  */
class SineActorDemo extends ScalaudioCoreTestHarness {
  "Sine actor" should "produce sine audio output" in {
    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    val sineActor = new SineActor(440.Hz)

    val frameFunc = () => {
      Array(sineActor.nextSample())
    }

    AmpOutput(frameFunc).play(5 seconds)
  }
}