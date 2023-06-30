package com.isaruff.cryptotrackerapp.presentation.views

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat

fun ImageView.changeBackgroundColor(context: Context, color: Int) {
    val colorState = ContextCompat.getColor(context, color)
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(colorState))
}