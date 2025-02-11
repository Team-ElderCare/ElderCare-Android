package com.example.eldercare.presentation.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eldercare.R
import com.example.eldercare.base.adapter.BaseAdapter
import com.example.eldercare.base.adapter.BaseViewHolder
import com.example.eldercare.base.diffutil.BaseDiffCallback
import com.example.eldercare.databinding.ItemUserActivityBinding

data class SensedItem(
    val id: String,
    val sensedTime: String,
    val deviceName: String,
)

data class UserActivityItem(
    val id: String,
    val firstTime: String,
    val lastTime: String,
    val deviceName: String,
    val sensedCount: Int,
    val sensedTagList: List<SensedItem>,
)

class UserActivityDiffUtilCallback :
    BaseDiffCallback<UserActivityItem>(
        itemTheSame = { old, new -> old.id == new.id },
        contentsTheSame = { old, new ->
            old.firstTime == new.firstTime &&
                old.lastTime == new.lastTime &&
                old.deviceName == new.deviceName &&
                old.sensedCount == new.sensedCount &&
                old.sensedTagList == new.sensedTagList
        },
    )

// 감지된 활동 기록 어댑터
class UserActivityAdapter :
    BaseAdapter<UserActivityItem, ItemUserActivityBinding, UserActivityAdapter.UserActivityViewHolder>(
        diffCallback = UserActivityDiffUtilCallback(),
    ) {
    var list: List<UserActivityItem> =
        listOf(
            UserActivityItem(
                id = "0",
                firstTime = "오전 09:30",
                lastTime = "오후 09:30",
                deviceName = "약장",
                sensedTagList =
                    listOf(
                        SensedItem(
                            id = "0",
                            sensedTime = "오후 01:00",
                            deviceName = "약장",
                        ),
                        SensedItem(
                            id = "1",
                            sensedTime = "오후 03:00",
                            deviceName = "약장",
                        ),
                        SensedItem(
                            id = "2",
                            sensedTime = "오후 04:00",
                            deviceName = "거실",
                        ),
                    ),
                sensedCount = 1,
            ),
            UserActivityItem(
                id = "1",
                firstTime = "오전 08:00",
                lastTime = "오후 07:30",
                deviceName = "약장",
                sensedTagList =
                    listOf(
                        SensedItem(
                            id = "1",
                            sensedTime = "오후 01:00",
                            deviceName = "약장",
                        ),
                    ),
                sensedCount = 1,
            ),
            UserActivityItem(
                id = "2",
                firstTime = "오전 09:30",
                lastTime = "오후 09:30",
                deviceName = "약장",
                sensedTagList =
                    listOf(
                        SensedItem(
                            id = "2",
                            sensedTime = "오후 01:00",
                            deviceName = "거실",
                        ),
                    ),
                sensedCount = 1,
            ),
        )

    // todo: 선택된 요일에 맞는 활동 리스트 불러오기
    fun setData() {
        submitList(list)
    }

    inner class UserActivityViewHolder(
        binding: ItemUserActivityBinding,
    ) : BaseViewHolder<UserActivityItem>(binding.root) {
        private val firstTime = binding.tvFirstTime
        private val lastTime = binding.tvLastTime
        private val deviceName = binding.tvDeviceName
        private val sensedCount = binding.tvSensedCount
        private val btnTagOpen = binding.btnOpen
        private val recyclerview = binding.sensedListRecyclerview
        private val layout = binding.layout
        val adapter = SensedListAdapter()

        init {
            binding.sensedListRecyclerview.adapter = adapter
        }

        override fun bind(item: UserActivityItem) {
            if (item.sensedTagList.isNotEmpty()) {
                layout.setBackgroundResource(R.drawable.rectangle_yellow_with_stroke)
            }
            firstTime.text = item.firstTime
            lastTime.text = item.lastTime
            deviceName.text = item.deviceName
            sensedCount.text = "${item.sensedTagList.size}회 감지"
            adapter.setList(item.sensedTagList)
            btnTagOpen.setOnClickListener {
                it.isSelected = !it.isSelected
                if (it.isSelected) {
                    recyclerview.visibility = View.VISIBLE
                } else {
                    recyclerview.visibility = View.GONE
                }
            }
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean,
    ): ItemUserActivityBinding {
        val binding = ItemUserActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding
    }

    override fun createViewHolder(binding: ItemUserActivityBinding): UserActivityViewHolder = UserActivityViewHolder(binding)
}
