package com.scalaudio.amp.midi

import com.scalaudio.amp.engine.FrameFuncAmpOutput
import com.scalaudio.amp.immutable.envelope.AdsrEnvelope
import com.scalaudio.amp.immutable.synth.MonosynthStateGen
import com.scalaudio.amp.immutable.ugen.{SineStateGen, SquareStateGen}
import com.scalaudio.core.midi.MidiConnector
import com.scalaudio.core.types.{AudioDuration, Pitch}
import com.scalaudio.core.{AudioContext, IntegrationTestHarness, ScalaudioConfig}

import scala.collection.immutable.TreeMap
import scala.concurrent.duration._

/**
  * Created by johnmcgill on 6/2/16.
  */
class MonosynthMidiReceiverDemo extends IntegrationTestHarness {

  "Monosynth Midi receiver" should "play sines in real-time" in {
    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))
    val adsrTemplate = AdsrEnvelope(10.millis,
      .95,
      40.millis,
      .5,
      60.millis,
      115.millis)

    var monosynthState = MonosynthStateGen.decodeInitialState(TreeMap.empty[AudioDuration, (Pitch, AdsrEnvelope)])

    val midiReceiver = MonosynthMidiReceiver(adsrTemplate, 150.millis)
    MidiConnector.connectKeyboard(midiReceiver)

    val frameFunc = () => {
      monosynthState = MonosynthStateGen.nextState(
        midiReceiver.processMidiCommandsIntoState(monosynthState),
        SineStateGen
      )
      List(monosynthState.sample)
    }

    FrameFuncAmpOutput(frameFunc).play(5000.seconds)
  }
}