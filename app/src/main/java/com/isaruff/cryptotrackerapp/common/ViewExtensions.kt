package com.isaruff.cryptotrackerapp.common

import android.content.Context
import android.content.res.ColorStateList
import android.text.Editable
import android.text.SpannableStringBuilder
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat

fun ImageView.changeBackgroundColor(context: Context, color: Int) {
    val colorState = ContextCompat.getColor(context, color)
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(colorState))
}

fun String.toEditable(): Editable{
    return SpannableStringBuilder(this)
}