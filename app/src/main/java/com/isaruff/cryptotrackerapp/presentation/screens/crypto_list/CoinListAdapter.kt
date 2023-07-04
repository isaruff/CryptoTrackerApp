package com.isaruff.cryptotrackerapp.presentation.screens.crypto_list


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.isaruff.cryptotrackerapp.common.convertUTCtoLocal
import com.isaruff.cryptotrackerapp.databinding.ItemCryptoCoinBinding
import com.isaruff.cryptotrackerapp.domain.model.CoinListModel


class CoinListAdapter(
    private val changeCurrencyColor: (imageView: ImageView) -> Unit,
    private val onClickListener: (coinDetails: CoinListModel) -> Unit
) : ListAdapter<CoinListModel, CoinListAdapter.CoinListViewHolder>(CoinListDiffCallback) {

    inner class CoinListViewHolder(val binding: ItemCryptoCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: CoinListModel) {
            binding.apply {
                imageViewCoin.load(itemData.image)
                textViewCoinName.text = itemData.name
                textViewLastUpdated.text = convertUTCtoLocal(itemData.lastUpdated)
                textViewCoinPrice.text = itemData.currentPrice.toString()
                changeCurrencyColor.invoke(imageViewCoinStatus)

                root.setOnClickListener {
                    onClickListener.invoke(itemData)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        return CoinListViewHolder(
            ItemCryptoCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)

    }

}

object CoinListDiffCallback : DiffUtil.ItemCallback<CoinListModel>() {
    override fun areItemsTheSame(oldItem: CoinListModel, newItem: CoinListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CoinListModel, newItem: CoinListModel): Boolean {
        return oldItem == newItem
    }

}

