package com.scalaudio.syntax

import com.scalaudio.AudioContext
import com.scalaudio.engine.{Playback, OutputEngine}
import com.scalaudio.timing.{TimedCompositeEvent, TimedEvent, ValueChange}

import scala.concurrent.duration.FiniteDuration

/**
  * This is a bag-of-junk file to magically make all syntax work. Maybe there is a better pattern to split this up by function?
  * Could include implicits with relevant classes but then can only be really assured they're available if some flagship class
  * is imported? Considering case-by-case for now.
  *
  * Created by johnmcgill on 12/22/15.
  */
trait ScalaudioSyntaxHelpers {
  // "Signal Flow" syntax
  implicit def bufferList2ChannelSetManipulator(bufferList: List[Array[Double]]) : ChannelSetManipulator = new ChannelSetManipulator(bufferList)

  // "Durations" syntax
  implicit def finiteDuration2AudioDuration(duration : FiniteDuration)(implicit audioContext: AudioContext) : AudioDuration = AudioDuration(DurationConverter.finiteDuration2Samples(duration))

  implicit def int2AudioDurationRichInt(n : Int)(implicit audioContext: AudioContext) : AudioDurationRichInt = AudioDurationRichInt(n)

  // "Pitch" syntax
  implicit def int2PitchRichInt(i : Int) : PitchRichInt = PitchRichInt(i)
  implicit def double2PitchRichDouble(d : Double) : PitchRichDouble = PitchRichDouble(d)

  // "Timing Events" syntax
  implicit def tuple2ValueChange(eventTuple : (Int, Double))(implicit audioContext: AudioContext) : TimedEvent =
    TimedEvent(eventTuple._1 buffers, ValueChange(eventTuple._2))

  implicit def timedCompositeEvent2TimedEventList(tce : TimedCompositeEvent) : List[TimedEvent] =
    tce.compositeEvent.toTimedEventList(tce.startTime)

  // Default output engines
  implicit def defaultOutputEngines(implicit audioContext: AudioContext) : List[OutputEngine] = List(Playback())
}
