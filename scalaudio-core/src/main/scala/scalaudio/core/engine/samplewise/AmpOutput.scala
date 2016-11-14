package scalaudio.core.engine.samplewise

import scalaudio.core.AudioContext
import scalaudio.core.engine.{OutputEngine, OutputTerminal, Samplewise}
import scalaudio.core.types.{AudioDuration, AudioSignal, Frame}

/**
  * Created by johnmcgill on 5/27/16.
  */
case class AmpOutput(frameFunc: () => Frame,
                     explicitOutputEngines: Option[List[OutputEngine]] = None)
                    (implicit audioContext: AudioContext) extends OutputTerminal {

  val processingRate = Samplewise

  var bufferedOutput: AudioSignal = Array.empty[Double]

  // Multichannel sample (frame?)
  def sampleOut(implicit audioContext: AudioContext): Frame = frameFunc()

  override def preStart() = {
    val c = audioContext.config
    bufferedOutput = Array.fill(c.framesPerBuffer * c.nOutChannels)(0.0)
  }

  def processTick(currentTime: AudioDuration)(implicit audioContext: AudioContext) = {
    val absoluteSample = currentTime.toSamples.toInt

    val offset = absoluteSample % audioContext.config.framesPerBuffer

    if (offset == 0)
      outputEngines foreach (_.handleBuffers(Left(bufferedOutput)))

    // This all might be stupid waste of time because filling internal buffer is
    // essentially an unnecessary interleave/de-interleave? Can pass interleaved
    // 1-D array straight to output engine
    val frame = sampleOut
    frame.zipWithIndex.foreach { case (s: Double, i: Int) => bufferedOutput(offset * frame.length + i) = s }
  }
}