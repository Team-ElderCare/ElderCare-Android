package com.example.eldercare.presentation.ui.alarm.count

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentSetAlarmCountBinding
import com.example.eldercare.presentation.ui.alarm.count.adapter.SetAlarmTimeAdapter
import com.example.eldercare.presentation.ui.main.MainActivity
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
        with(binding) {
            recyclerView.adapter = this@SetAlarmCountFragment.adapter

            btnDone.isSelected = adapter.currentList.size >= 1
            btnDone.setOnClickListener {
                // todo : 홈 화면으로 이동하기
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }

            btnBack.setOnClickListener {
                navController.popBackStack()
            }

            btnReduce.setOnClickListener {
                adapter.deleteDrugAlarm()
                viewModel.reduceCount()
            }
            btnAdd.setOnClickListener {
                adapter.addDrugAlarm()
                viewModel.addCount()
                // 복용 알림 데이터 추가하기
            }
        }

        adapter.listener =
            object : SetAlarmTimeAdapter.OnClickListener {
                override fun onItemClick(position: Int) {
                    // 타임 피커 뜨기
                    val picker =
                        MaterialTimePicker
                            .Builder()
                            .setTheme(R.style.Theme_ElderCare_TimePickerDialog)
                            .setTimeFormat(TimeFormat.CLOCK_12H)
                            .setHour(8)
                            .setMinute(30)
                            .setTitleText("시간을 선택해주세요")
                            .setNegativeButtonText("취소")
                            .setPositiveButtonText("확인")
                            .build()

                    picker.show(parentFragmentManager, "tag")
                    picker.addOnPositiveButtonClickListener {
                        var selectedMinute = picker.minute.toString()
                        var selectedHour = picker.hour.toString()
                        // 만약 시간이 한자리 수라면 ?
                        if (picker.hour < 10) {
                            selectedHour = "0${picker.hour}"
                        }
                        if (picker.minute < 10) {
                            selectedMinute = "0${picker.minute}"
                        }
                        adapter.updateAlarmTime("$selectedHour:$selectedMinute", position)
                    }
                }

                override fun onDeleteClick(position: Int) {
                    viewModel.reduceCount()
                }
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
    }
}
