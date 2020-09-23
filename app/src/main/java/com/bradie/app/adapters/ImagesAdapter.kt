package com.bradie.app.adapters

import androidx.recyclerview.widget.DiffUtil
import com.bradie.app.R
import com.bradie.app.apiservice.Hit

/**
 * Adapter extending the generic adapter, DataBindingAdapter.
 */
class ImagesAdapter constructor(onItemClick: OnItemClick, optionsClick: OnMoreOptionsClick) :
    DataBindingAdapter<Hit>(DiffCallBack(), onItemClick, optionsClick) {

    /**
     * DiffUtil callback which decides when to update the data and how to compare it.
     * These overrides methods returns boolean value.
     */
    class DiffCallBack : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            /**
             * Return true if id of new and old items are same. If data items are same then no
             * update will be dispatched
             */
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            /**
             * Return true if old data is equal to new data.
             */
            return oldItem == newItem
        }
    }

    // Set the layout to be used by the recyclerView
    override fun getItemViewType(position: Int) = R.layout.item_cards_layout
}
