package com.scalaudio.amp.immutable.filter

import com.scalaudio.core.types.{Frame, Sample}

/**
  * Created by johnmcgill on 5/28/16.
  */
object SplitFilter {
  def split(sample: Sample, nChannels: Int) : Frame = List.fill(nChannels)(sample)
}