package com.example.finalproject.ui.extension

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.example.finalproject.R

object Button {
    fun Button.enable() {
        this.isEnabled = true
        this.isClickable = true
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
    }

    fun Button.disable() {
        this.isEnabled = false
        this.isClickable = false
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.bbColor))
    }
}



object ImageButton{
    fun ImageButton.enable(){
        this.isEnabled = true
        this.isClickable =true
        this.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)

    }

    fun ImageButton.disable(){
        this.isClickable=false
        this.isEnabled =true
        this.setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_ATOP)
    }
}