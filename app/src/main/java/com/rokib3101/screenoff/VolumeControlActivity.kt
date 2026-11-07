package com.rokib3101.screenoff

import android.media.AudioManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class VolumeControlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
            audioManager.adjustSuggestedStreamVolume(
                AudioManager.ADJUST_SAME,
                AudioManager.USE_DEFAULT_STREAM_TYPE,
                AudioManager.FLAG_SHOW_UI,
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        finish()
    }
}
