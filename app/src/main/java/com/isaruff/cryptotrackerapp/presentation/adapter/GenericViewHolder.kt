package com.isaruff.cryptotrackerapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericViewHolder<D, VB : ViewBinding>(
    private val itemBinding: VB,
    private val onBind: VB.(data: D, position: Int) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(data: D){
        onBind.invoke(itemBinding, data, adapterPosition)
    }

    companion object {
        fun <VB : ViewBinding, D> create(
            viewGroup: ViewGroup,
            onInflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
            onBind: VB.(D, Int) -> Unit
        ): GenericViewHolder<D,VB> {
            return GenericViewHolder(
                onInflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                ),
                onBind
            )
        }
    }


}