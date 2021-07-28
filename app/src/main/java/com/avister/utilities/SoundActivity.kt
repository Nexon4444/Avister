package com.avister.utilities
import com.avister.R
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SoundActivity : AppCompatActivity() {


    // originally from http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
    // and modified by Steve Pomeroy <steve@staticfree.info>
    private val duration = 3 // seconds

    private val sampleRate = 8000
    private val numSamples = duration * sampleRate
    private val sample = DoubleArray(numSamples)
    private val freqOfTone = 440.0 // hz


    private val generatedSnd = ByteArray(2 * numSamples)

    var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)
        playSound()
    }

//    override fun onResume() {
//        super.onResume()
//
//        // Use a new tread as this can take a while
//        val thread = Thread {
//            genTone()
//            handler.post(Runnable { playSound() })
//        }
//        thread.start()
//    }
//
//    fun genTone() {
//        // fill out the array
//        for (i in 0 until numSamples) {
//            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone))
//        }
//
//        // convert to 16 bit pcm sound array
//        // assumes the sample buffer is normalised.
//        var idx = 0
//        for (dVal in sample) {
//            // scale to maximum amplitude
//            val `val` = (dVal * 32767).toInt().toShort()
//            // in 16 bit wav PCM, first byte is the low order byte
////            generatedSnd[idx++] = (`val` & 0x00ff) as Byte
////            generatedSnd[idx++] = (`val` & 0xff00 ushr 8) as Byte
//        }
//    }

    fun playSound() {
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.test)
        mp.start()
    }
}