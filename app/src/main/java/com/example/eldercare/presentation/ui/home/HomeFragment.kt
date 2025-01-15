package com.example.eldercare.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentHomeBinding
import com.example.eldercare.presentation.ui.home.adapter.DrugAlarmRVAdapter
import com.example.eldercare.presentation.ui.home.adapter.UserRecentActivityRVAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(
        FragmentHomeBinding::inflate,
    ) {
    override val viewModel: HomeViewModel by viewModels()
    private val alarmAdapter by lazy { DrugAlarmRVAdapter() }
    private val recentActivityAdapter by lazy { UserRecentActivityRVAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterProbationer.setOnClickListener {
            it.isPressed = !it.isPressed
        }
        binding.btnRegisterHealthInfo.setOnClickListener {
            it.isPressed = !it.isPressed
        }

        binding.viewDrugAlarm.tab.btnCalendar.apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }

        with(binding.viewDrugAlarm.tab) {
            btnAlarmList.setOnClickListener {
                updateButtonStyle(isCalendarSelected = false)
            }
            btnCalendar.setOnClickListener {
                updateButtonStyle(isCalendarSelected = true)
            }
        }
    }

    private fun updateButtonStyle(isCalendarSelected: Boolean) {
        if (isCalendarSelected) {
            with(binding.viewDrugAlarm.tab) {
                btnCalendar.apply {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                btnAlarmList.apply {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.Gray50))
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }
        } else {
            with(binding.viewDrugAlarm.tab) {
                btnCalendar.apply {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.Gray50))
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                }
                btnAlarmList.apply {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }
        }
    }
}
