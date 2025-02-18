package com.example.eldercare.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentHomeBinding
import com.example.eldercare.presentation.ui.alarm.SetAlarmActivity
import com.example.eldercare.presentation.ui.home.adapter.DrugAlarmRVAdapter
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(
        FragmentHomeBinding::inflate,
    ) {
    override val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy { DrugAlarmRVAdapter() }

    private fun showToolTip() {
        val balloon =
            context?.let {
                createBalloon(it) {
                    setLayout(R.layout.layout_custom_tool_tip)
                    setHeight(BalloonSizeSpec.WRAP)
                    setTextColorResource(R.color.black)
                    setCornerRadius(12f)
                    setBackgroundColorResource(R.color.white)
                    build()
                }
            }
        balloon?.showAlignBottom(binding.viewRecentActivity.btnTip)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            binding.viewRecentActivity.btnTip.setOnClickListener {
                showToolTip()
            }
            binding.viewRecentActivity.drugSensed.layout
                .setBackgroundResource(R.drawable.rectangle_yellow_with_stroke)
            binding.viewRecentActivity.sensed.layout
                .setBackgroundResource(R.drawable.rectangle_white_radius_16_without_stroke)
            binding.viewDrugAlarm.drugAlarmRecyclerview.adapter = adapter

            viewDrugAlarm.btnAddAlarm.setOnClickListener {
                val intent = Intent(context, SetAlarmActivity::class.java)
                startActivity(intent)
            }

            btnRegisterProbationer.setOnClickListener {
                it.isPressed = !it.isPressed
            }

            btnRegisterHealthInfo.setOnClickListener {
                it.isPressed = !it.isPressed
            }

            viewDrugAlarm.tab.btnCalendar.apply {
                setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }

            viewDrugAlarm.tab.apply {
                btnAlarmList.setOnClickListener {
                    updateButtonStyle(isCalendarSelected = false)
                }
                btnCalendar.setOnClickListener {
                    updateButtonStyle(isCalendarSelected = true)
                }
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
