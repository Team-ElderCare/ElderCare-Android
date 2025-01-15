package com.example.eldercare.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.eldercare.base.adapter.BaseAdapter
import com.example.eldercare.base.adapter.BaseViewHolder
import com.example.eldercare.databinding.ItemRecentActivityBinding
import com.example.eldercare.domain.model.recent.UserRecentActivityData
import com.example.eldercare.presentation.ui.home.diff.UserRecentActivityDiffCallback

class UserRecentActivityRVAdapter :
    BaseAdapter<UserRecentActivityData, ItemRecentActivityBinding, UserRecentActivityRVAdapter.UserRecentActivityViewHolder>(
        UserRecentActivityDiffCallback(),
    ) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean,
    ): ItemRecentActivityBinding {
        val binding = ItemRecentActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding
    }

    override fun createViewHolder(binding: ItemRecentActivityBinding): UserRecentActivityViewHolder = UserRecentActivityViewHolder(binding)

    inner class UserRecentActivityViewHolder(
        binding: ItemRecentActivityBinding,
    ) : BaseViewHolder<UserRecentActivityData>(binding.root) {
        override fun bind(item: UserRecentActivityData) {
        }
    }
}
