package scalaudio.buffer.unitgen

/**
  * Created by johnmcgill on 12/21/15.
  */

import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._
import scalaudio.buffer.BufferSyntax
import scalaudio.buffer.filter.mix.Splitter
import scalaudio.core.engine.Timeline
import scalaudio.core.engine.bufferwise.BufferOutputTerminal
import scalaudio.core.{AudioContext, ScalaudioConfig}

class GeneratorsTest extends FlatSpec with Matchers with BufferSyntax {

  "Noise Generator" should "create buffer of random noise on every call" in {
    implicit val audioContext : AudioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    val noiseGen = new NoiseGen
    val output = BufferOutputTerminal(noiseGen)
    Timeline.playFor(5 seconds, List(output))
  }

  "Sine Generator" should "create buffer of sine on every call" in {
    implicit val audioContext : AudioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    val sineGen = new SineGen(440.Hz)
    val output = BufferOutputTerminal(sineGen)
    Timeline.playFor(1000.buffers, List(output))
  }

  "Square Generator" should "create buffer of squarewave on every call" in {
    implicit val audioContext : AudioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    val squareGen = new SquareGen(300.Hz)
    BufferOutputTerminal(squareGen).play(1000 buffers)
  }

  "Sawtooth Generator" should "create buffer of sawtooth on every call" in {
    implicit val audioContext : AudioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    val sawtoothGen = new SawtoothGen((880 * 2).Hz)
    BufferOutputTerminal(sawtoothGen).play(1000 buffers)
  }

  "Signal chain" should "play noise" in {
    implicit val audioContext : AudioContext = AudioContext(ScalaudioConfig(nOutChannels = 2))

    val chain = new SignalChain(new NoiseGen, List(Splitter(2)))
    BufferOutputTerminal(chain).play(1000 buffers)
  }
}