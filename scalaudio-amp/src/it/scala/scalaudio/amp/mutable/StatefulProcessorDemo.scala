package scalaudio.amp.mutable

import signalz.StatefulProcessor

import scala.concurrent.duration._
import scalaudio.amp.immutable.filter.{DelayFilterState, DelayFilterStateGen}
import scalaudio.amp.immutable.ugen.{OscState, SineStateGen}
import scalaudio.core.engine.StreamCollector
import scalaudio.core.{AudioContext, ScalaudioConfig, ScalaudioCoreTestHarness}
import scalaz.Scalaz._

/**
  * Created by johnmcgill on 7/11/16.
  */
class StatefulProcessorDemo extends ScalaudioCoreTestHarness {
  "StatefulProcessor" should "produce sine without var in outer scope" in {
    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    val ff = StatefulProcessor(SineStateGen.nextState, OscState(0, 440.Hz, 0)).nextState
      .map(state => Array(state.sample))

    StreamCollector(ff).play(5.seconds)
  }

  "StatefulProcessor" should "use pre-transformer for automation" in {
    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    val preTransformer = (s: OscState, u: Unit) => s.copy(
      pitch = (s.pitch.toHz + .2).Hz
    )
    val ff = StatefulProcessor.withModifier(SineStateGen.nextState,
      OscState(0, 440.Hz, 0),
      preTransformer
    ).nextState map (state => Array(state.sample))

    StreamCollector(ff).play(5 seconds)
  }

  "StatefulProcessors" should "be chainable" in {

    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    val delayFilter = StatefulProcessor.withModifier(DelayFilterStateGen.nextState,
      DelayFilterStateGen.initialState(3.seconds),
      (delayFilterState: DelayFilterState, newSample: Double) => delayFilterState.copy(sample = newSample)
    )

    val ff = StatefulProcessor(SineStateGen.nextState, OscState(0, 440.Hz, 0)).nextState
      .map(_.sample)
      .map(delayFilter.nextState)
      .map(_.sample)
      .map(Array(_))

    StreamCollector(ff).play(5.seconds)
  }
}