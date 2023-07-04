package com.isaruff.cryptotrackerapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
/**
 * Use With Caution. Only applicable to Items that are simple, otherwise you can suffer from frame drops
 * */
class GenericListAdapter<D : Any, VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val onBind: VB.(data: D, position: Int) -> Unit,
    diffCallBack: DiffUtil.ItemCallback<D> = GenericDiffCallback()
) : ListAdapter<D, GenericViewHolder<D, VB>>(diffCallBack) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<D, VB> {
        return GenericViewHolder.create(parent, inflate, onBind)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<D, VB>, position: Int) {
        holder.bind(getItem(position))
    }
}