package scalaudio.units.filter

import scalaudio.core.AudioContext
import scalaudio.core.types.{Frame, Sample}

/**
  * Created by johnmcgill on 5/28/16.
  */

object GainFilter {
  // Don't use "StateGen" approach since gain is actually pretty stateless?
  def applyGainToSample(sample: Sample, gain: Double): Sample = sample * gain

  def applyShortCircuitGainToSample(sample: Sample, gain: Double): Sample =
    if (gain == 0) 0.0 else sample * gain

  def applyGainToFrame(gain: Double)(f: Frame): Frame = f.map(_ * gain)

  def applyShortCircuitGainToFrame(frame: Frame, gain: Double)(implicit audioContext: AudioContext): Frame =
    if (gain == 0) frame.map(_ => 0.0)
    else frame.map(_ * gain)
}
