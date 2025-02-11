package com.example.eldercare.presentation.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentUserActivityBinding
import java.time.LocalDate

// 활동 탭
class UserActivityFragment : BaseFragment<FragmentUserActivityBinding, UserActivityViewModel>(FragmentUserActivityBinding::inflate) {
    override val viewModel: UserActivityViewModel by viewModels()
    private val adapter by lazy { UserActivityAdapter() }
    val now: LocalDate = LocalDate.now()

    private val navController by lazy { findNavController() }

    // 0 : 오늘 ,1 : 1일전 , 2 : 2일전
    private val dateList =
        listOf(
            DateUtil.formatFullDate(now),
            DateUtil.formatFullDate(now.minusDays(1)),
            DateUtil.formatFullDate(now.minusDays(2)),
            DateUtil.formatFullDate(now.minusDays(3)),
            DateUtil.formatFullDate(now.minusDays(4)),
            DateUtil.formatFullDate(now.minusDays(5)),
            DateUtil.formatFullDate(now.minusDays(6)),
        )

    private fun selectToday() {
        val num = DateUtil.doDayOfWeek()
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

    private fun onSelectDate(
        item: View,
        position: Int,
    ) {
        binding.viewWeekCalendar.seventhDay.isSelected = false
        binding.viewWeekCalendar.firstDay.isSelected = false
        binding.viewWeekCalendar.secondDay.isSelected = false
        binding.viewWeekCalendar.thirdDay.isSelected = false
        binding.viewWeekCalendar.fourthDay.isSelected = false
        binding.viewWeekCalendar.fifthDay.isSelected = false
        binding.viewWeekCalendar.sixthDay.isSelected = false
        item.isSelected = !item.isSelected
        binding.viewWeekCalendar.tvDate.text = dateList[position]
    }

    private fun processCalendar() {
        with(binding.viewWeekCalendar) {
            // 오늘로부터 이전 6일을 표시해주기 !
            tvDate.text = DateUtil.getTodayDate()
            val now = LocalDate.now()

            seventhDay.setDate(DateUtil.getFormattedDate(now))
            sixthDay.setDate(DateUtil.getFormattedDate(now.minusDays(1)))
            fifthDay.setDate(DateUtil.getFormattedDate(now.minusDays(2)))
            fourthDay.setDate(DateUtil.getFormattedDate(now.minusDays(3)))
            thirdDay.setDate(DateUtil.getFormattedDate(now.minusDays(4)))
            secondDay.setDate(DateUtil.getFormattedDate(now.minusDays(5)))
            firstDay.setDate(DateUtil.getFormattedDate(now.minusDays(6)))

            sixthDay.setDayOfWeek(DateUtil.getDayOfWeek(now.minusDays(1).dayOfWeek.value))
            fifthDay.setDayOfWeek(DateUtil.getDayOfWeek(now.minusDays(2).dayOfWeek.value))
            fourthDay.setDayOfWeek(DateUtil.getDayOfWeek(now.minusDays(3).dayOfWeek.value))
            thirdDay.setDayOfWeek(DateUtil.getDayOfWeek(now.minusDays(4).dayOfWeek.value))
            secondDay.setDayOfWeek(DateUtil.getDayOfWeek(now.minusDays(5).dayOfWeek.value))
            firstDay.setDayOfWeek(DateUtil.getDayOfWeek(now.minusDays(6).dayOfWeek.value))

            firstDay.setOnClickListener {
                onSelectDate(it, 6)
                // todo : 해당하는 데이터를 어댑터에 보내주기
                viewModel.getActivities(dateList[6])
                adapter.setData()
            }
            secondDay.setOnClickListener {
                onSelectDate(it, 5)
                viewModel.getActivities(dateList[5])
                adapter.setData()
            }
            thirdDay.setOnClickListener {
                onSelectDate(it, 4)
                viewModel.getActivities(dateList[4])
                adapter.setData()
            }
            fourthDay.setOnClickListener {
                onSelectDate(it, 3)
                viewModel.getActivities(dateList[3])
                adapter.setData()
            }
            fifthDay.setOnClickListener {
                onSelectDate(it, 2)
                viewModel.getActivities(dateList[2])
                adapter.setData()
            }
            sixthDay.setOnClickListener {
                onSelectDate(it, 1)
                viewModel.getActivities(dateList[1])
                adapter.setData()
            }
            seventhDay.setOnClickListener {
                onSelectDate(it, 0)
                viewModel.getActivities(dateList[0])
                adapter.setData()
            }
        }
    }

    private fun initCalendar() {
        selectToday()
        processCalendar()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initCalendar()
        with(binding) {
            sensedListRecyclerView.adapter = adapter
            btnBack.setOnClickListener {
                navController.popBackStack()
            }
        }
        adapter.setData()
    }
}
