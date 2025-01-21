package com.example.eldercare.presentation.ui.alarm.count.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.eldercare.base.adapter.BaseAdapter
import com.example.eldercare.base.adapter.BaseViewHolder
import com.example.eldercare.databinding.ItemAlarmBinding
import com.example.eldercare.presentation.ui.alarm.count.diff.DrugAlarmTimeDiffCallback
import timber.log.Timber

data class DrugAlarmTime(
    val id: Int,
    var time: String = "08:00",
)

class SetAlarmTimeAdapter :
    BaseAdapter<DrugAlarmTime, ItemAlarmBinding, SetAlarmTimeAdapter.SetAlarmTimeViewHolder>(
        diffCallback = DrugAlarmTimeDiffCallback(),
    ) {
    private val drugAlarmData: MutableList<DrugAlarmTime> = mutableListOf()

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    var listener: OnClickListener? = null

    fun updateAlarmTime(
        time: String,
        position: Int,
    ) {
        drugAlarmData[position].time = time
        notifyItemChanged(position)
    }

    fun deleteDrugAlarm() {
        if (drugAlarmData.size == 0) return
        drugAlarmData.removeAt(drugAlarmData.lastIndex)
        // 여기서 같은 drugAlarmData 객체를 전달하면 인식 X , 새로운 객체를 전달해야함 !!
        submitList(drugAlarmData.toList())
    }

    fun addDrugAlarm() {
        drugAlarmData.add(
            0,
            DrugAlarmTime(
                id = 0,
                time = "08:00",
            ),
        )
        submitList(drugAlarmData.toList())
    }

    inner class SetAlarmTimeViewHolder(
        binding: ItemAlarmBinding,
    ) : BaseViewHolder<DrugAlarmTime>(
            itemView = binding.root,
        ) {
        private val time: TextView = binding.txtTime
        private val btnDelete: ImageButton = binding.btnDeleteAlarm
        val view = binding.customAlarmView

        override fun bind(item: DrugAlarmTime) {
            time.text = item.time

            view.setOnClickListener {
                it.isSelected = !it.isSelected
                listener?.onItemClick(bindingAdapterPosition)
            }
            btnDelete.setOnClickListener {
                try {
                    drugAlarmData.removeAt(bindingAdapterPosition)
                    submitList(drugAlarmData.toList())
                } catch (
                    e: IndexOutOfBoundsException,
                ) {
                    e.printStackTrace()
                    Timber.e("제거 bindingAdapterPosition $bindingAdapterPosition")
                    Timber.e("제거 에러 ${e.message}")
                }
            }
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean,
    ): ItemAlarmBinding {
        val binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding
    }

    override fun createViewHolder(binding: ItemAlarmBinding): SetAlarmTimeViewHolder = SetAlarmTimeViewHolder(binding)
}
