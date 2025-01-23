package com.example.eldercare.presentation.ui.alarm.cycle

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentSetAlarmCycleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetAlarmCycleFragment :
    BaseFragment<FragmentSetAlarmCycleBinding, SetAlarmCycleViewModel>(
        FragmentSetAlarmCycleBinding::inflate,
    ) {
    override val viewModel: SetAlarmCycleViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // 요일 선택
            tvMon.setOnClickListener {
                it.isSelected = !it.isSelected
            }
            tvTues.setOnClickListener {
                it.isSelected = !it.isSelected
            }
            tvWednes.setOnClickListener {
                it.isSelected = !it.isSelected
            }
            tvThurs.setOnClickListener {
                it.isSelected = !it.isSelected
            }
            tvFri.setOnClickListener {
                it.isSelected = !it.isSelected
            }
            tvSatur.setOnClickListener {
                it.isSelected = !it.isSelected
            }
            tvSun.setOnClickListener {
                it.isSelected = !it.isSelected
            }

            // 버튼 선택 테스트
            binding.btnNext.apply {
                this.isSelected = true
            }

            binding.btnNext.setOnClickListener {
                navController.navigate(R.id.setAlarmCountFragment)
            }

            binding.btnBack.setOnClickListener {
                navController.popBackStack()
            }
        }
    }
}
