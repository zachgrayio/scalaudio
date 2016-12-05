package scalaudio.amp.engine

import scala.concurrent.duration._
import scalaudio.core.engine.io.AnalysisFuncFileWriter
import scalaudio.units.analysis.{EnergyAnalyzerState, EnergyAnalyzerStateGen}
import scalaudio.units.ugen.{OscState, Sine}
import scalaudio.core.{AudioContext, ScalaudioConfig, ScalaudioCoreTestHarness}

/**
  * Created by johnmcgill on 6/7/16.
  */
class AnalysisFuncFileWriterDemo extends ScalaudioCoreTestHarness {
  "Analysis file writer" should "capture some RMS values" in {
    implicit val audioContext = AudioContext(ScalaudioConfig(nOutChannels = 1))

    var sineState : OscState = OscState(0, 440.Hz, 0)
    var rmsState: EnergyAnalyzerState = EnergyAnalyzerState(0, None)

    val analysisFunc = () => {
      sineState = Sine.nextState(sineState)
      rmsState = EnergyAnalyzerStateGen.nextState(rmsState.copy(sampleIn = sineState.sample))
      rmsState.energyLevel.map(Array(_))
    }

    val writer = AnalysisFuncFileWriter(analysisFunc, "rms")
    writer.write("test-analysis", 5 seconds)
  }
}
