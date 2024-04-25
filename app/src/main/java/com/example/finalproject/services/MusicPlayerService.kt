package com.example.finalproject.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.finalproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicPlayerService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying: Boolean = false
    private var audio: String? = null
    private lateinit var mediaSessionCompat: MediaSessionCompat

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
    }

    private fun stopService() {
        stopSelf()
    }

    private fun notificationUpdate() {
        if (!::mediaSessionCompat.isInitialized) {
            mediaSessionCompat = MediaSessionCompat(applicationContext, "Music")
        }

        val mediaStyle = androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(mediaSessionCompat.sessionToken)
        val intentPlayPause = Intent(this, MusicPlayerService::class.java).apply {
            action = if (isPlaying) Actions.PAUSE.toString() else Actions.PLAY.toString()
        }

        val pendingIntentPlayPause =
            PendingIntent.getService(this, 0, intentPlayPause, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext, MY_CHANNEL_ID)
            .setContentTitle("Nizam")
            .setContentText("Eiubov")
            .setSmallIcon(R.drawable.ic_spotify)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_next2, "Previous", null)
            .addAction(R.drawable.ic_next2, "Pause", pendingIntentPlayPause)
            .addAction(R.drawable.ic_next2, "Next", null)
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

