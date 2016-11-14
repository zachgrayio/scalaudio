package scalaudio.jmathplot.examples

import scala.collection.mutable.ListBuffer
import scalaudio.amp.immutable.ugen.{OscState, SineStateGen}
import scalaudio.core.{CoreSyntax, DefaultAudioContext}
import scalaudio.jmathplot.ConvenientPlot

/**
  * Created by johnmcgill on 6/15/16.
  */
object SinePlot extends App with CoreSyntax with DefaultAudioContext {

  var oscState = OscState(0, 440.Hz, 0)
  val yVals = ListBuffer.empty[Double]

  1 to 300 foreach {_ =>
    yVals += oscState.sample
    oscState = SineStateGen.nextState(oscState)
  }

  val x = (1 to yVals.length).map(_.toDouble).toArray

  ConvenientPlot.plot2d(x, yVals.toArray)
}