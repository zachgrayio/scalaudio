package scalaudio.core

/**
  * Created by johnmcgill on 5/22/16.
  */
package object types {
  type Sample = Double
  type Frame = Array[Sample]

  type AudioSignal = Array[Double]
  type AudioRate[T] = Array[T]

  type ControlRate[T] = T

  type Signal[T] = Either[ControlRate[T], AudioRate[T]]

  type MultichannelAudio = List[AudioSignal]
}