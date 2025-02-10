package com.example.eldercare.presentation.ui.activity

import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentUserActivityBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Date

// 활동 탭
class UserActivityFragment : BaseFragment<FragmentUserActivityBinding, UserActivityViewModel>(FragmentUserActivityBinding::inflate) {
    override val viewModel: UserActivityViewModel by viewModels()
    private val adapter by lazy { UserActivityAdapter() }

    // todo : 오늘 요일 구하기
    private fun getDayOfWeek(dayOfWeek: Int): String =
        when (dayOfWeek) {
            1 -> "월"
            2 -> "화"
            3 -> "수"
            4 -> "목"
            5 -> "금"
            6 -> "토"
            7 -> "일"
            else -> {
                throw IllegalArgumentException()
            }
        }

    private fun selectToday() {
        val num = doDayOfWeek()
        when (num) {
            1 -> {
                binding.viewWeekCalendar.seventhDay.setDayOfWeek("일")
            }
            2 -> {
                binding.viewWeekCalendar.seventhDay.setDayOfWeek("월")
            }
            3 -> {
                binding.viewWeekCalendar.seventhDay.setDayOfWeek("화")
            }
            4 -> {
                binding.viewWeekCalendar.seventhDay.setDayOfWeek("수")
            }
            5 -> {
                binding.viewWeekCalendar.seventhDay.setDayOfWeek("목")
            }
            6 -> {
                binding.viewWeekCalendar.seventhDay.setDayOfWeek("금")
            }
            7 -> {
                binding.viewWeekCalendar.seventhDay.setDayOfWeek("토")
            }
        }
        binding.viewWeekCalendar.seventhDay.isSelected = true
    }

    private fun onSelectDate(item: View) {
        binding.viewWeekCalendar.seventhDay.isSelected = false
        binding.viewWeekCalendar.firstDay.isSelected = false
        binding.viewWeekCalendar.secondDay.isSelected = false
        binding.viewWeekCalendar.thirdDay.isSelected = false
        binding.viewWeekCalendar.fourthDay.isSelected = false
        binding.viewWeekCalendar.fifthDay.isSelected = false
        binding.viewWeekCalendar.sixthDay.isSelected = false
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

    private fun getFormattedDate(date: LocalDate): String {
        // 여기서 해당 날짜의 요일을 넘겨줘야함
        val dayOfWeek = date.dayOfWeek
        val thisWeek =
            date.with(TemporalAdjusters.previousOrSame(dayOfWeek))
        val formatter = DateTimeFormatter.ofPattern("dd")
        val formattedDate = thisWeek.format(formatter)
        return formattedDate
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        selectToday()

        binding.sensedListRecyclerView.adapter = adapter
        adapter.setData()
        with(binding.viewWeekCalendar) {
            // 오늘로부터 이전 6일을 표시해주기 !
            tvDate.text = getTodayDate()
            val now = LocalDate.now()

            seventhDay.setDate(getFormattedDate(now))
            sixthDay.setDate(getFormattedDate(now.minusDays(1)))
            sixthDay.setDayOfWeek(getDayOfWeek(now.minusDays(1).dayOfWeek.value))

            fifthDay.setDate(getFormattedDate(now.minusDays(2)))
            fifthDay.setDayOfWeek(getDayOfWeek(now.minusDays(2).dayOfWeek.value))

            fourthDay.setDate(getFormattedDate(now.minusDays(3)))
            fourthDay.setDayOfWeek(getDayOfWeek(now.minusDays(3).dayOfWeek.value))

            thirdDay.setDate(getFormattedDate(now.minusDays(4)))
            thirdDay.setDayOfWeek(getDayOfWeek(now.minusDays(4).dayOfWeek.value))

            secondDay.setDate(getFormattedDate(now.minusDays(5)))
            secondDay.setDayOfWeek(getDayOfWeek(now.minusDays(5).dayOfWeek.value))

            firstDay.setDate(getFormattedDate(now.minusDays(6)))
            firstDay.setDayOfWeek(getDayOfWeek(now.minusDays(6).dayOfWeek.value))

            firstDay.setOnClickListener { onSelectDate(it) }
            secondDay.setOnClickListener { onSelectDate(it) }

            thirdDay.setOnClickListener { onSelectDate(it) }

            fourthDay.setOnClickListener { onSelectDate(it) }

            fifthDay.setOnClickListener { onSelectDate(it) }

            sixthDay.setOnClickListener { onSelectDate(it) }

            seventhDay.setOnClickListener { onSelectDate(it) }
        }
    }
}
