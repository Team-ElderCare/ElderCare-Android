package com.example.eldercare.presentation.ui.alarm.count

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentSetAlarmCountBinding
import com.example.eldercare.presentation.ui.alarm.count.adapter.SetAlarmTimeAdapter
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetAlarmCountFragment :
    BaseFragment<FragmentSetAlarmCountBinding, SetAlarmCountViewModel>(
        FragmentSetAlarmCountBinding::inflate,
    ) {
    override val viewModel: SetAlarmCountViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val adapter by lazy { SetAlarmTimeAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = this@SetAlarmCountFragment.adapter
        adapter.listener =
            object : SetAlarmTimeAdapter.OnClickListener {
                override fun onItemClick(position: Int) {
                    // 타임 피커 뜨기
                    val picker =
                        MaterialTimePicker
                            .Builder()
                            .setTimeFormat(TimeFormat.CLOCK_12H)
                            .setHour(8)
                            .setMinute(30)
                            .setTitleText("시간을 선택해주세요")
                            .setNegativeButtonText("취소")
                            .setPositiveButtonText("확인")
                            .build()

                    picker.show(parentFragmentManager, "tag")
                    picker.addOnPositiveButtonClickListener {
                        val selectedHour = picker.hour
                        val selectedMinute = picker.minute
                        val time = "$selectedHour:$selectedMinute"
                        adapter.updateAlarmTime(time, position)
                    }
                }

                override fun onDeleteClick(position: Int) {
                    viewModel.reduceCount()
                }
            }

        binding.btnDone.isSelected = adapter.currentList.size >= 1

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }

        // 카운팅
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.count.collectLatest {
                    binding.txtCount.text = "${it}회"
                    if (it >= 1) {
                        binding.btnDone.isSelected = true
                    } else {
                        binding.btnDone.isSelected = false
                    }
                }
            }
        }

        binding.btnReduce.setOnClickListener {
            Log.d("제거", "버튼 클릭")
            viewModel.reduceCount()
            adapter.deleteDrugAlarm()
        }
        binding.btnAdd.setOnClickListener {
            Log.d("추가", "버튼 클릭")
            viewModel.addCount()
            // 복용 알림 데이터 추가하기
            adapter.addDrugAlarm()
        }
    }
}
