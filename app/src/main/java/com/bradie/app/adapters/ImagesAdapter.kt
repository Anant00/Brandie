package com.bradie.app.adapters

import androidx.recyclerview.widget.DiffUtil
import com.bradie.app.R
import com.bradie.app.apiservice.Hit

class ImagesAdapter :
    DataBindingAdapter<Hit>(DiffCallBack()) {

    class DiffCallBack : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int) = R.layout.item_cards_layout
}
