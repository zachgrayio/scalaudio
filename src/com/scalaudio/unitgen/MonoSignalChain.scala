package com.scalaudio.unitgen

import com.scalaudio.AudioContext
import com.scalaudio.filter.Filter

/**
  * Created by johnmcgill on 12/19/15.
  */
case class MonoSignalChain(val startGen : UnitGen, val filterChain : List[Filter]) extends UnitGen {
  val frameFunc = () => filterChain.foldLeft(startGen.outputBuffer)((r,c) => c.processBuffer(r))

  def outputBuffer : Array[Double] = frameFunc()
}