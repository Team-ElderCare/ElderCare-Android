package com.example.eldercare.presentation.ui.alarm.count.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
    private val drugAlarmDataList: MutableList<DrugAlarmTime> = mutableListOf()

    interface OnClickListener {
        fun onItemClick(position: Int)

        fun onDeleteClick(position: Int)
    }

    var listener: OnClickListener? = null

    fun updateAlarmTime(
        time: String,
        position: Int,
    ) {
        val updateList = drugAlarmDataList.toMutableList()
        updateList[position] = updateList[position].copy(time = time)

        // 원본 데이터에 변경 반영
        drugAlarmDataList.clear()
        drugAlarmDataList.addAll(updateList)

        // 업데이트된 리스트 제출
        submitList(updateList)
    }

    fun deleteDrugAlarm() {
        if (drugAlarmDataList.size == 0) return
        val newList = drugAlarmDataList.toMutableList()

        newList.removeAt(drugAlarmDataList.lastIndex)
        drugAlarmDataList.clear()
        drugAlarmDataList.addAll(newList)
        // 여기서 같은 drugAlarmData 객체를 전달하면 인식 X , 새로운 객체를 전달해야함 !!
        submitList(newList)
    }

    fun deleteDrugAlarmItem(position: Int) {
        listener?.onDeleteClick(position)
        val newList = drugAlarmDataList.toMutableList()
        newList.removeAt(position)

        drugAlarmDataList.clear()
        drugAlarmDataList.addAll(newList)
        submitList(newList)
    }

    fun addDrugAlarm() {
        val newList = drugAlarmDataList.toMutableList()
        newList.add(
            drugAlarmDataList.size,
            DrugAlarmTime(
                id = drugAlarmDataList.size,
                time = "08:00",
            ),
        )
        drugAlarmDataList.clear()
        drugAlarmDataList.addAll(newList)
        submitList(newList)
    }

    inner class SetAlarmTimeViewHolder(
        binding: ItemAlarmBinding,
    ) : BaseViewHolder<DrugAlarmTime>(
            itemView = binding.root,
        ) {
        private val time: TextView = binding.txtTime
        private val btnDelete: ImageButton = binding.btnDeleteAlarm
        private val txtWhen: TextView = binding.txtWhen
        val view = binding.customAlarmView

        override fun bind(item: DrugAlarmTime) {
            time.text = item.time
            Timber.d("hour 추출 ${item.time}")
            val hour = item.time.substring(0, 2).toInt()
            val minutes = item.time.substring(3, 5)
            if (hour > 12) {
                time.text = "${hour - 12}:$minutes"
                txtWhen.text = "오후"
            }
            view.setOnClickListener {
                it.isSelected = !it.isSelected
                listener?.onItemClick(bindingAdapterPosition)
            }

            btnDelete.setOnClickListener {
                if (drugAlarmDataList.size == 0) return@setOnClickListener
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    try {
                        deleteDrugAlarmItem(bindingAdapterPosition)
                    } catch (e: IndexOutOfBoundsException) {
                        e.printStackTrace()
                        Timber.d("index out $bindingAdapterPosition :${e.message}")
                    }
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
