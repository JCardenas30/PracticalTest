package com.sunwise.practicaltest.view.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Ignore
import com.sunwise.practicaltest.BR

class GenericAdapter<T : ListItemViewModel>(@LayoutRes val layoutId: Int) :
        RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    private val items = mutableListOf<T>()
    private var inflater: LayoutInflater? = null
    private var onListItemViewClickListener: OnListItemViewClickListener? = null
    private var offsetMarginHorizontal = 0f

    fun addItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T? {
        if(position >= this.items.size) return null
        return this.items[position]
    }

    fun getItems() = items

    fun setOffsetHorizontalMargin(margin: Float){
        if(margin > 0)
            this.offsetMarginHorizontal = margin
    }

    fun addItem(item: T){
        this.items.add(item)
        this.notifyItemInserted(items.size-1)
    }

    fun removeItem(position: Int) {
        this.items.removeAt(position)
        this.notifyItemRemoved(position)
        notifyItemRangeChanged(position,items.size)
    }

    fun restoreItem(item: T, position: Int) {
        this.items.add(position, item)
        //notifyItemInserted(position)
        notifyItemRangeChanged(position, this.items.size-1)
    }

    fun setOnListItemViewClickListener(onListItemViewClickListener: OnListItemViewClickListener?){
        this.onListItemViewClickListener = onListItemViewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = items[position]
        itemViewModel.adapterPosition = position
        onListItemViewClickListener?.let { itemViewModel.onListItemViewClickListener = it }
        holder.bind(itemViewModel)
    }

    class GenericViewHolder<T : ListItemViewModel>(private val binding: ViewDataBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewModel: T) {
            binding.setVariable(BR.itemViewModel, itemViewModel)
            binding.executePendingBindings()
        }

    }

    interface OnListItemViewClickListener{
        fun onClick(view: View, position: Int)
    }
}

abstract class ListItemViewModel{
    @Ignore var adapterPosition: Int = -1
    @Ignore var onListItemViewClickListener: GenericAdapter.OnListItemViewClickListener? = null
}