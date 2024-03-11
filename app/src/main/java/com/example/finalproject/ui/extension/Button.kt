package com.example.finalproject.ui.extension

import android.widget.Button
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