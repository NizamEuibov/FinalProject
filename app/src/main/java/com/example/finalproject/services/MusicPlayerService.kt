package com.example.finalproject.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.finalproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicPlayerService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying: Boolean = false
    private var title: String? = null
    private var text: String? = null
    private var audio: String? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!::mediaPlayer.isInitialized) {
            mediaPlayer = MediaPlayer()
        }

        when (intent?.action) {
            Actions.PLAY.toString() -> {
                audio = intent.getStringExtra("audio")
                audio?.let { playMusic(it) }
                Log.d("Audio1","$audio")
            }

            Actions.PAUSE.toString() -> pauseMusic()
            Actions.CLOSE.toString() -> stopService()
        }
        return START_NOT_STICKY
    }


    private fun playMusic(audio: String) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.reset()
        mediaPlayer.setDataSource(audio)
        mediaPlayer.isLooping = true
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            it.start()
        }
        isPlaying = true
        notificationUpdate()
    }

    private fun pauseMusic() {
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            isPlaying = false
        }
        notificationUpdate()
    }

    private fun stopService() {
        stopSelf()
    }

    private fun notificationUpdate() {

        val intentPlayPause = Intent(this, MusicPlayerService::class.java).apply {
            action = if (isPlaying) Actions.PAUSE.toString() else Actions.PLAY.toString()
        }

        val pendingIntentPlayPause =
            PendingIntent.getService(this, 0, intentPlayPause, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext, MY_CHANNEL_ID)
            .setContentTitle("oiyifvuyi")
            .setContentText("pugbuyyijkl")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(
                if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play,
                if (isPlaying) "Pause" else "Play",
                pendingIntentPlayPause
            )
            .build()
        startForeground(ID, notification)
    }

    companion object {
        const val MY_CHANNEL_ID = "my_channel_id"
        const val MY_CHANNEL_NAME = "my_channel_name"
        const val ID = 10
    }

    enum class Actions {
        PLAY, PAUSE, CLOSE
    }
}
