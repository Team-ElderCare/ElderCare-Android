package com.example.eldercare.base.adapter

import androidx.recyclerview.widget.ListAdapter
import com.example.eldercare.base.diffutil.BaseDiffCallback

abstract class BaseAdapter<T : Any, VH : BaseViewHolder<T>>(
    diffCallback: BaseDiffCallback<T>,
) : ListAdapter<T, VH>(diffCallback) {
    // 클릭 리스너 처리를 위한 프로퍼티
    private var itemClickListener: ((T) -> Unit)? = null

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.apply {
            setOnClickListener {
                itemClickListener?.invoke(item)
            }
        }

        fun setOnItemClickListener(listener: (T) -> Unit) {
            itemClickListener = listener
        }
    }
}
