package com.example.eldercare.presentation.ui.activity

import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentRunBinding
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Date

// 활동 탭
class RunFragment : BaseFragment<FragmentRunBinding, RunViewModel>(FragmentRunBinding::inflate) {
    override val viewModel: RunViewModel by viewModels()

    private fun selectToday() {
        val num = doDayOfWeek()
        when (num) {
            1 -> {
                binding.viewWeekCalendar.sunday.isSelected = true
            }
            2 -> {
                binding.viewWeekCalendar.monday.isSelected = true
            }
            3 -> {
                binding.viewWeekCalendar.tuesday.isSelected = true
            }
            4 -> {
                binding.viewWeekCalendar.wednesday.isSelected = true
            }
            5 -> {
                binding.viewWeekCalendar.thursday.isSelected = true
            }
            6 -> {
                binding.viewWeekCalendar.friday.isSelected = true
            }
            7 -> {
                binding.viewWeekCalendar.saturday.isSelected = true
            }
        }
    }

    private fun onSelectDate(item: View) {
        binding.viewWeekCalendar.sunday.isSelected = false
        binding.viewWeekCalendar.monday.isSelected = false
        binding.viewWeekCalendar.tuesday.isSelected = false
        binding.viewWeekCalendar.wednesday.isSelected = false
        binding.viewWeekCalendar.thursday.isSelected = false
        binding.viewWeekCalendar.friday.isSelected = false
        binding.viewWeekCalendar.saturday.isSelected = false

        item.isSelected = !item.isSelected
    }

    private fun doDayOfWeek(): Int {
        val cal: Calendar = Calendar.getInstance()
        val nWeek: Int = cal.get(Calendar.DAY_OF_WEEK)
        return nWeek
    }

    private fun getTodayDate(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val today = sdf.format(date)
        return today
    }

    private fun dateMonday(dayOfWeek: DayOfWeek): String {
        val today = LocalDate.now()
        val thisWeekMonday =
            if (today.dayOfWeek.value >= dayOfWeek.value) {
                today.with(TemporalAdjusters.previousOrSame(dayOfWeek))
            } else {
                today.with(TemporalAdjusters.next(dayOfWeek))
            }
        val formatter = DateTimeFormatter.ofPattern("dd")
        val formattedDate = thisWeekMonday.format(formatter)
        return formattedDate
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        selectToday()

        with(binding.viewWeekCalendar) {
            tvDate.text = getTodayDate()
            monday.setDate(dateMonday(DayOfWeek.MONDAY))
            monday.setOnClickListener { onSelectDate(it) }
            tuesday.setDate(dateMonday(DayOfWeek.TUESDAY))
            tuesday.setOnClickListener { onSelectDate(it) }

            wednesday.setDate(dateMonday(DayOfWeek.WEDNESDAY))
            wednesday.setOnClickListener { onSelectDate(it) }

            thursday.setDate(dateMonday(DayOfWeek.THURSDAY))
            thursday.setOnClickListener { onSelectDate(it) }

            friday.setDate(dateMonday(DayOfWeek.FRIDAY))
            friday.setOnClickListener { onSelectDate(it) }

            saturday.setDate(dateMonday(DayOfWeek.SATURDAY))
            saturday.setOnClickListener { onSelectDate(it) }

            sunday.setDate(dateMonday(DayOfWeek.SUNDAY))
            sunday.setOnClickListener { onSelectDate(it) }
        }
    }
}
