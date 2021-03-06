package scalaudio.units.io

import java.io.File

import com.jsyn.util.WaveFileWriter
import signalz.SequentialState
import scalaudio.core.{AudioContext, ScalaudioConfig}
import scalaudio.core.types._

/**
  * Created by johnmcgill on 12/5/16.
  */
case class Recording(filename: String)(implicit audioContext: AudioContext)
  extends SequentialState[Frame, AudioContext] {

  val waveFile: File = new File(filename + ".wav")
  val writer = new WaveFileWriter(waveFile)
  writer.setFrameRate(audioContext.config.samplingRate)
  writer.setSamplesPerFrame(audioContext.config.nOutChannels)
  writer.setBitsPerSample(16)

  val c: ScalaudioConfig = audioContext.config
  val outBufferSize: Int = c.framesPerBuffer * c.nOutChannels
  var bufferedOutput: AudioRate[Sample] = Array.fill(outBufferSize)(0.0)
  var currentIndex = 0

  println(s"Writing to WAV file ${waveFile.getAbsolutePath}")

  //  TODO: override def stop(): Unit = writer.close()

  def record(audioSignal: AudioSignal): Unit = writer.write(audioSignal)

  // TODO: Solve issue of redundant buffers / dupe code here (with Playback)
  override def nextState(frame: Frame)(implicit context: AudioContext): Frame = {
    frame.foreach { sample =>
      bufferedOutput(currentIndex) = sample
      currentIndex = (currentIndex + 1) % outBufferSize
    }

    if (currentIndex == 0) record(bufferedOutput)

    frame
  }
}
