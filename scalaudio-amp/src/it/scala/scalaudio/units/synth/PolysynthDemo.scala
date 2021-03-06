package scalaudio.units.synth

import scala.collection.immutable.{SortedMap, TreeMap}
import scala.concurrent.duration._
import scalaudio.core.types.{AudioDuration, Pitch}
import scalaudio.core.{AudioContext, ScalaudioConfig, ScalaudioCoreTestHarness}
import scalaudio.units.control.AdsrEnvelope
import scalaudio.units.ugen.{ImmutableSine, Sine}

/**
  * Created by johnmcgill on 5/30/16.
  */
class PolysynthDemo extends ScalaudioCoreTestHarness {

  //TODO: Type alias or something? ("Synth note"?)
  implicit def note2NoteList(note: (Pitch, AdsrEnvelope)): List[(Pitch, AdsrEnvelope)] = List(note)

  "Polysynth" should "jam concurrent beefy sinewaves" in {
    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))
    val adsrTemplate = AdsrEnvelope(300.millis,
      .95,
      400.millis,
      .5,
      500.millis, //600
      115.millis) //15

    val notes: SortedMap[AudioDuration, List[(Pitch, AdsrEnvelope)]] =
      TreeMap(
        (1.second: AudioDuration) ->(440.Hz, adsrTemplate),
        (2.second: AudioDuration) ->(550.Hz, adsrTemplate),
        (3.second: AudioDuration) ->(660.Hz, adsrTemplate),
        (4.second: AudioDuration) -> List((880.Hz, adsrTemplate),
          (445.Hz, adsrTemplate)),
        (5.second: AudioDuration) ->(220.Hz, adsrTemplate),
        (7.second: AudioDuration) -> List((770.Hz, adsrTemplate),
          (330.Hz, adsrTemplate)),
        (8.second: AudioDuration) ->(660.Hz, adsrTemplate),
        (8200.millis: AudioDuration) ->(550.Hz, adsrTemplate),
        (8500.millis: AudioDuration) ->(660.Hz, adsrTemplate),
        (9.second: AudioDuration) ->(440.Hz, adsrTemplate)
      )

    var polysynthState = PolysynthState(0, notes, Nil)

    val frameFunc = () => {
      polysynthState = Polysynth.immutable(Sine.immutable).nextState(polysynthState)
      Array(polysynthState.sample * .2)
    }

    frameFunc.play(11 seconds)
  }
}
