package scalaudio.amp.midi

import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._
import scalaudio.units.control.AdsrEnvelope
import scalaudio.units.synth.{Monosynth, MonosynthMidiReceiver}
import scalaudio.core.midi.NoteOn
import scalaudio.core.{AudioContext, CoreSyntax, ScalaudioConfig}

/**
  * Created by johnmcgill on 6/2/16.
  */
class MonosynthMidiReceiverSpec extends FlatSpec with Matchers with CoreSyntax {

  "MonosynthReceiver" should "add a single attack/decay for a note on" in {
    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    audioContext.State.currentSample = 7000

    val adsrTemplate = AdsrEnvelope(30.millis,
      .95,
      40.millis,
      .5,
      60.millis,
      15.millis)

    val midiReceiver = MonosynthMidiReceiver(adsrTemplate, 30.millis)

    val newState = midiReceiver.addCommandToState(Monosynth.decodeInitialState(), NoteOn(1, 22, 100))

    println(newState)
  }

}
