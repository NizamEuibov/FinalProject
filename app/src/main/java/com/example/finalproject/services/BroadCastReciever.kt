package com.example.finalproject.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadCastReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_PLAY -> {
                // Handle play action
                Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show()
            }
            ACTION_PAUSE -> {
                // Handle pause action
                Toast.makeText(context, "Pause", Toast.LENGTH_SHORT).show()
            }
            ACTION_NEXT -> {
                // Handle next action
                Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show()
            }
            ACTION_PREVIOUS -> {
                // Handle previous action
                Toast.makeText(context, "Previous", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val ACTION_PLAY = "com.example.musicapp.ACTION_PLAY"
        const val ACTION_PAUSE = "com.example.musicapp.ACTION_PAUSE"
        const val ACTION_NEXT = "com.example.musicapp.ACTION_NEXT"
        const val ACTION_PREVIOUS = "com.example.musicapp.ACTION_PREVIOUS"
    }
}