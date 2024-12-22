package com.example.eldercare.base.diffutil

import androidx.recyclerview.widget.DiffUtil

// DiffUtil 사용 시 구현할 Base CallBack
abstract class BaseDiffCallback<T : Any>(
    private val itemTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val contentsTheSame: (oldItem: T, newItem: T) -> Boolean,
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean {
        return itemTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean {
        return contentsTheSame(oldItem, newItem)
    }
}
