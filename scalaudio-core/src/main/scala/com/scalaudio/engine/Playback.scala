package com.scalaudio.engine


import com.scalaudio.AudioContext

/**
  * Playback trait -- mix this in to a UnitGen to make it playable
  *
  *  Created by johnmcgill on 12/20/15.
  */
case class Playback()(implicit audioContext: AudioContext) extends OutputEngine {
  def handleBuffer(buffers : List[Array[Double]]) = play(buffers)

  def play(buffers : List[Array[Double]]) = {
      if (buffers.length != audioContext.config.nOutChannels)
        throw new Exception("Playback -- this device outputs incompatible number of channels. This playback system requires " + audioContext.config.nOutChannels)

    if (buffers.length > 1) // TODO: Can logical optimizations like this be macro-ized or does it have to be run-time?
      audioContext.audioOutput.write(Interleaver.interleave(buffers))
    else
      audioContext.audioOutput.write(buffers.head)
  }
}
