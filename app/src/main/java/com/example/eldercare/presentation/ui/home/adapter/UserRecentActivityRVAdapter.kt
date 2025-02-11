package com.example.eldercare.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.eldercare.base.adapter.BaseAdapter
import com.example.eldercare.base.adapter.BaseViewHolder
import com.example.eldercare.databinding.ItemNormalBinding
import com.example.eldercare.domain.model.recent.UserRecentActivityData
import com.example.eldercare.presentation.ui.home.diff.UserRecentActivityDiffCallback

// todo : 아이템 레이아웃 배경 색에만 차이가 있어서 하나의 레이아웃을 사용하면서 배경색만 변경할지 각 유형에 따라 다른 레이아웃을 사용할지 추후 결정 예정
class UserRecentActivityRVAdapter :
    BaseAdapter<UserRecentActivityData, ItemNormalBinding, UserRecentActivityRVAdapter.UserRecentActivityViewHolder>(
        UserRecentActivityDiffCallback(),
    ) {
    var list: List<UserRecentActivityData> = emptyList()

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean,
    ): ItemNormalBinding {
        val binding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding
    }

    override fun createViewHolder(binding: ItemNormalBinding): UserRecentActivityViewHolder = UserRecentActivityViewHolder(binding)

    inner class UserRecentActivityViewHolder(
        binding: ItemNormalBinding,
    ) : BaseViewHolder<UserRecentActivityData>(binding.root) {
        override fun bind(item: UserRecentActivityData) {
        }
    }
}
