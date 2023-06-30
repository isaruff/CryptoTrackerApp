package com.isaruff.cryptotrackerapp.presentation.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class GenericDiffCallback<D : Any> : DiffUtil.ItemCallback<D>() {
    override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
        return oldItem == newItem
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
        return newItem == newItem
    }
}