package scalaudio.core

import scalaudio.core.types.{PitchRichDouble, PitchRichInt}
import scalaudio.core.types._
import scala.concurrent.duration.FiniteDuration
import scalaudio.core.engine.{FunctionGraph, SignalProcessingGraph, StreamGraph, Timeline}
import scalaudio.core.types.AudioDuration

/**
  * This is a bag-of-junk file to magically make all syntax work. Maybe there is a better pattern to split this up by function?
  * Could include implicits with relevant classes but then can only be really assured they're available if some flagship class
  * is imported? Considering case-by-case for now.
  *
  * Created by johnmcgill on 12/22/15.
  */
trait CoreSyntax {
  // "Durations" syntax
  implicit def finiteDuration2AudioDuration(duration : FiniteDuration)(implicit audioContext: AudioContext) : AudioDuration = AudioDuration(DurationConverter.finiteDuration2Samples(duration))

  implicit def int2AudioDurationRichInt(n : Int)(implicit audioContext: AudioContext) : AudioDurationRichInt = AudioDurationRichInt(n)

  // "Pitch" syntax
  implicit def int2PitchRichInt(i : Int) : PitchRichInt = PitchRichInt(i)
  implicit def double2PitchRichDouble(d : Double) : PitchRichDouble = PitchRichDouble(d)


  // Unit func vs. func0
  implicit def unitFuncToFunction0[T](unitFunc: Unit => T): () => T = () => unitFunc()
  implicit def function0ToUnitFunc[T](func0: () => T): Unit => T = (u: Unit) => func0()

  // Timeline
  implicit def emptyParensFunc2FunctionTimeline(func: () => _): FunctionGraph = Timeline.functionGraph(func)
  implicit def emptyParensFunc2StreamTimeline(stream: Stream[_]): StreamGraph = Timeline.streamGraph(stream)

  // TODO: Is this syntax better? "Safer looking" func vs implicit conversion OO style
  def play(graphTimeline: SignalProcessingGraph, duration: AudioDuration) = graphTimeline.play(duration)
}