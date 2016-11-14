package scalaudio.benchmark.ugen

import java.time.Instant

import scala.concurrent.duration._
import scalaudio.actor.mutable.filter.GainActor
import scalaudio.actor.mutable.ugen.SineActor
import scalaudio.amp.immutable.ugen.{OscState, SineStateGen}
import scalaudio.core.engine.SpeedTestDummy
import scalaudio.core.engine.samplewise.AmpOutput
import scalaudio.core.{AudioContext, ScalaudioConfig, ScalaudioCoreTestHarness}

/**
  * Created by johnmcgill on 6/6/16.
  */
class SineBenchmark extends ScalaudioCoreTestHarness {

  "scalaudioAMP" should "clock sine production" in {
    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    var state: OscState = OscState(0, 440.Hz, 0)

    val frameFunc = () => {
      state = SineStateGen.nextState(state)
      Array(state.sample)
    }

    val start = Instant.now.toEpochMilli
    AmpOutput(frameFunc, Some(List(SpeedTestDummy()))).play(5 hours)
    val end = Instant.now.toEpochMilli

    println((end - start).millis)

    // 10 mins
    // withGain: 4050 without: 4170

    // 5 hrs
    // 430568 (430 secs) (6.5 mins)
  }

  "scalaudioActor" should "clock sine production" in {
    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    val sineActor = new SineActor(440.Hz)
    val gainActor = new GainActor(.7)

    val frameFunc = () => {
      Array(sineActor.nextSample())
    }

    val start = Instant.now.toEpochMilli
    AmpOutput(frameFunc, Some(List(SpeedTestDummy()))).play(5 hours)
    val end = Instant.now.toEpochMilli

    println((end - start).millis)

    // 10 mins
    // withGain: 6740 without: 3975

    // 403782 milliseconds
  }
}