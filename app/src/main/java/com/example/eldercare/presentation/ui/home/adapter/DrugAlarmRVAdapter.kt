package com.example.eldercare.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.eldercare.R
import com.example.eldercare.base.adapter.BaseAdapter
import com.example.eldercare.base.adapter.BaseViewHolder
import com.example.eldercare.databinding.ItemDrugAlarmBinding
import com.example.eldercare.domain.model.alarm.DrugAlarmData
import com.example.eldercare.presentation.ui.home.diff.DrugAlarmDiffCallback

class DrugAlarmRVAdapter :
    BaseAdapter<DrugAlarmData, ItemDrugAlarmBinding, DrugAlarmRVAdapter.DrugAlarmViewHolder>(
        DrugAlarmDiffCallback(),
    ) {
    private val drugAlarmDataList: MutableList<DrugAlarmData> = mutableListOf()

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean,
    ): ItemDrugAlarmBinding {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_drug_alarm, parent, false)

        val binding = ItemDrugAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding
    }

    override fun createViewHolder(binding: ItemDrugAlarmBinding): DrugAlarmViewHolder = DrugAlarmViewHolder(binding)

    inner class DrugAlarmViewHolder(
        binding: ItemDrugAlarmBinding,
    ) : BaseViewHolder<DrugAlarmData>(binding.root) {
        val title: TextView = binding.tvTime

        override fun bind(item: DrugAlarmData) {
        }
    }
}
