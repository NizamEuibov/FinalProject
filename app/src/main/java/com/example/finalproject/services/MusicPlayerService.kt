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
//        if (!::mediaPlayer.isInitialized) {
//            mediaPlayer = MediaPlayer()
//        }

        when (intent?.action) {
            Actions.PLAY.toString() -> notificationUpdate()


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
        val playIntent = Intent(applicationContext, BroadCastReceiver::class.java).apply {
            action = BroadCastReceiver.ACTION_PLAY
        }
        val playPendingIntent = PendingIntent.getBroadcast(applicationContext, 0, playIntent, PendingIntent.FLAG_MUTABLE)

        val pauseIntent = Intent(applicationContext, BroadCastReceiver::class.java).apply {
            action = BroadCastReceiver.ACTION_PAUSE
        }
        val pausePendingIntent = PendingIntent.getBroadcast(applicationContext, 1, pauseIntent, PendingIntent.FLAG_MUTABLE)

        val nextIntent = Intent(applicationContext, BroadCastReceiver::class.java).apply {
            action = BroadCastReceiver.ACTION_NEXT
        }
        val nextPendingIntent = PendingIntent.getBroadcast(applicationContext, 2, nextIntent, PendingIntent.FLAG_MUTABLE)

        val previousIntent = Intent(applicationContext, BroadCastReceiver::class.java).apply {
            action = BroadCastReceiver.ACTION_PREVIOUS
        }
        val previousPendingIntent = PendingIntent.getBroadcast(applicationContext, 3, previousIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, MY_CHANNEL_ID)
            .setContentTitle("Music Player")
            .setContentText("Playing your favorite music")
            .setSmallIcon(R.drawable.ic_play)
            .addAction(R.drawable.ic_previous, "Previous", previousPendingIntent)
            .addAction(R.drawable.ic_play, "Play", playPendingIntent)
            .addAction(R.drawable.ic_pause, "Pause", pausePendingIntent)
            .addAction(R.drawable.ic_next, "Next", nextPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
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

