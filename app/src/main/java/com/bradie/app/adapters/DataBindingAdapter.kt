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
        fun bind(item: T) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}

interface OnItemClick {
    fun onItemClick(position: Int)
}

interface OnMoreOptionsClick {
    fun onOptionMenuClick(position: Int)
}
