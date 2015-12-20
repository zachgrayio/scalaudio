package com.scalaudio

import com.scalaudio.filter.{Filter, GainFilter}
import com.scalaudio.mix.OutputSummer
import com.scalaudio.unitgen.{MonoSignalChain, SineGen, NoiseGen}
import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by johnmcgill on 12/19/15.
  */
class ChainTest extends FlatSpec with Matchers with ScalaudioConfig {
  "List of modules" should "chain using outputBuffer" in {
    val filterChain : List[Filter] = List(new GainFilter(.5), new GainFilter(.75))
    val startUnit = new SineGen

    // Creates function variable to generate frames from this filter chain
    val frameFunc = () => filterChain.foldLeft(startUnit.outputBuffer)((r,c) => c.processBuffer(r))

    1 to 1000 foreach {_ => AudioContext.audioOutput.write(frameFunc())}

    AudioContext.audioOutput.stop
  }

  "SignalChain abstraction" should "playback in same fashion" in {
    val filterChain : List[Filter] = List(new GainFilter(.5), new GainFilter(.75))

    val sigChainList = List(MonoSignalChain(new NoiseGen, filterChain), MonoSignalChain(new SineGen, filterChain))
    val summer = OutputSummer(sigChainList)

    summer.play(1000)

    AudioContext.audioOutput.stop
  }
}