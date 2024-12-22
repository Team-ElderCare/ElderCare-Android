package com.example.eldercare.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.eldercare.base.diffutil.BaseDiffCallback


abstract class BaseAdapter<T : Any, VB : ViewBinding, VH : BaseViewHolder<T>>(
    diffCallback: BaseDiffCallback<T>,
) : ListAdapter<T, VH>(diffCallback) {

    protected lateinit var binding: VB

    // 클릭 리스너 처리를 위한 프로퍼티
    private var itemClickListener: ((T) -> Unit)? = null

    fun setOnItemClickListener(listener: (T) -> Unit) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        binding = inflateBinding(LayoutInflater.from(parent.context), parent, false)
        return createViewHolder(binding)
    }

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

    }

    // ViewBinding inflate를 위한 추상 메서드
    protected abstract fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): VB

    // ViewHolder 생성을 위한 추상 메서드
    protected abstract fun createViewHolder(binding: VB): VH
}
