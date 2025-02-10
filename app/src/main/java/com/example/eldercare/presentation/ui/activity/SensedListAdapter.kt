package com.example.eldercare.presentation.ui.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.eldercare.base.adapter.BaseAdapter
import com.example.eldercare.base.adapter.BaseViewHolder
import com.example.eldercare.base.diffutil.BaseDiffCallback
import com.example.eldercare.databinding.ItemSensedBinding
import timber.log.Timber

class SensedListDiffUtilCallback :
    BaseDiffCallback<SensedItem>(
        itemTheSame = { old, new -> old.id == new.id },
        contentsTheSame = { oldItem, newItem ->
            oldItem.sensedTime == newItem.sensedTime &&
                oldItem.deviceName == newItem.deviceName
        },
    )

// 홛동 기록 내부 태그 데이터 어댑터
class SensedListAdapter :
    BaseAdapter<SensedItem, ItemSensedBinding, SensedListAdapter.SensedListViewHolder>(
        diffCallback = SensedListDiffUtilCallback(),
    ) {
    val list: List<SensedItem> = emptyList()

    fun setList(newList: List<SensedItem>) {
        Timber.d("감지된 리스트 :$newList")
        submitList(newList)
    }

    inner class SensedListViewHolder(
        private val binding: ItemSensedBinding,
    ) : BaseViewHolder<SensedItem>(binding.root) {
        private val time = binding.tvSensedTime
        private val deviceName = binding.tvDeviceName

        override fun bind(item: SensedItem) {
            time.text = item.sensedTime
            deviceName.text = item.deviceName
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean,
    ): ItemSensedBinding {
        val binding = ItemSensedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding
    }

    override fun createViewHolder(binding: ItemSensedBinding): SensedListViewHolder = SensedListViewHolder(binding)
}
