package com.example.wheatherapplication.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<B : ViewDataBinding, T>(
    @LayoutRes private val rowLayout: Int,
    private val list: List<T>
) : RecyclerView.Adapter<BaseAdapter.ViewHolder<B>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                rowLayout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder<B : ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)
}