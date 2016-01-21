package com.scalaudio.unitgen

/**
  * Created by johnmcgill on 12/21/15.
  */


import com.scalaudio.filter.mix.Splitter
import com.scalaudio.syntax.ScalaudioSyntaxHelpers
import com.scalaudio.{Config, AudioContext}
import com.scalaudio.engine.Playback
import org.scalatest.{FlatSpec, Matchers}

class GeneratorsTest extends FlatSpec with Matchers with ScalaudioSyntaxHelpers {

  "Noise Generator" should "create buffer of random noise on every call" in {

    Config.NOutChannels = 1

    val noiseGen = new NoiseGen with Playback
    noiseGen.start
    noiseGen.play(1000 buffers)
    noiseGen.stop
  }

  "Sine Generator" should "create buffer of sine on every call" in {
    Config.NOutChannels = 1

    val sineGen = new SineGen(440 Hz) with Playback
    sineGen.start
    sineGen.play(1000 buffers)
    sineGen.stop
  }

  "Square Generator" should "create buffer of squarewave on every call" in {
    Config.NOutChannels = 1

    val squareGen = new SquareGen(300 Hz) with Playback
    squareGen.start
    squareGen.play(1000 buffers)
    squareGen.stop
  }

  "Sawtooth Generator" should "create buffer of sawtooth on every call" in {
    Config.NOutChannels = 1

    val sawtoothGen = new SawtoothGen(880 * 2 Hz) with Playback
    sawtoothGen.start
    sawtoothGen.play(1000 buffers)
    sawtoothGen.stop
  }

  "Signal chain" should "play noise" in {
    Config.NOutChannels = 2

    val chain = new SignalChain(new NoiseGen, List(Splitter(2))) with Playback
    chain.start
    chain.play(1000 buffers)
    chain.stop
  }
}