package com.example.eldercare.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) :
    RecyclerView.ViewHolder(itemView.rootView) {
    abstract fun bind(item: T)
}
