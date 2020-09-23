package com.bradie.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bradie.app.BR
import kotlinx.android.synthetic.main.item_cards_layout.view.*

/**
 * A generic adapter, helps in removing boilerplate code.
 * This adapter extends the ListAdapter. ListAdapter uses DiffUtil internally.
 * DiffUtils helps in maintaining the smoothness of recyclerView. It updates only those items
 * which are not present already in the database. Unlike recyclerview, which refreshes whole list
 * of data to update recycleView even for a single item, listAdapter, on the other hand, updates
 * only the required new item.
 *
 * Read more about listAdapter @see {https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter}
 * Read more about DiffUtil @see {https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil}
 */
abstract class DataBindingAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val onItemClick: OnItemClick,
    private val onMoreOptionsClick: OnMoreOptionsClick
) :
    ListAdapter<T, DataBindingAdapter.DataBindingViewHolder<T>>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)

        val viewHolder: DataBindingViewHolder<T> = DataBindingViewHolder(binding)

        /**
         * Dispatching the click events. Note that these 'clickOnListeners' should not be set in
         * onBindViewHolder as it may get called multiple times. It may lag the recyclerview scroll.
         */
        try {
            binding.root.imMore.setOnClickListener {
                onMoreOptionsClick.onOptionMenuClick(viewHolder.adapterPosition)
            }

            binding.root.setOnClickListener {
                onItemClick.onItemClick(viewHolder.adapterPosition)
            }
        } catch (e: Exception) {
            println("ViewType is different")
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) =
        holder.bind(getItem(position))

    class DataBindingViewHolder<T>(
        private val binding: ViewDataBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         *  Be sure that you have set 'item' as variable in your recyclerView item layout. It won't
         *  find the 'item' variable otherwise. Also, since this is the generic adapter, make sure
         *  to use same variable in all recyclerView item layouts.
         */
        fun bind(item: T) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}

/**
 * Interface which are triggered by click events on items layout.
 */
interface OnItemClick {
    fun onItemClick(position: Int)
}

interface OnMoreOptionsClick {
    fun onOptionMenuClick(position: Int)
}
