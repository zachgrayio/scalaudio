package com.scalaudio.unitgen

import com.scalaudio.AudioContext
import com.scalaudio.syntax.{Pitch, PitchRichInt}

/**
  * Created by johnmcgill on 12/21/15.
  */
class SawtoothGen(override val initFreq : Pitch = PitchRichInt(440).Hz,
                  override val initPhase : Double = 0)(implicit audioContext: AudioContext) extends UnitOsc(initFreq, initPhase) {

  override def computeBuffer(paramsOpt : Option[UnitParams] = None) = {
    0 until audioContext.config.framesPerBuffer foreach { i =>
      paramsOpt foreach (params => handleParams(params, i))
      internalBuffers.head(i) = (((w * i + phi) % period) / period) * 2 - 1
    }
    phi += phiInc
  }
}