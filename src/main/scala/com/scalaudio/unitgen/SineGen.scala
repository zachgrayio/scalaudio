package com.scalaudio.unitgen

import com.scalaudio.AudioContext
import com.scalaudio.syntax.{Pitch, PitchRichDouble}
import com.scalaudio.types.Signal

/**
  * Created by johnmcgill on 12/18/15.
  */
case class SineGen(override val initFreq : Pitch = PitchRichDouble(440).Hz,
                   override val initPhase : Double = 0)(implicit audioContext: AudioContext) extends UnitOsc(initFreq, initPhase) {

  def computeBuffer(paramsOpt: Option[OscillatorParams] = None) = {

    0 until audioContext.config.framesPerBuffer foreach { i =>
      paramsOpt foreach (params => handleParams(params, i))
      internalBuffers.head(i) = Math.sin(w * i + phi)
    }
    phi += phiInc
  }
}