package com.example.eldercare.presentation.ui.activity

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Date

// 캘린더 관련 작업 함수
object DateUtil {
    private val FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    private val DAY_FORMATTER = DateTimeFormatter.ofPattern("dd")

    fun doDayOfWeek(): Int {
        val cal: Calendar = Calendar.getInstance()
        val nWeek: Int = cal.get(Calendar.DAY_OF_WEEK)
        return nWeek
    }

    fun getTodayDate(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val today = sdf.format(date)
        return today
    }

    fun formatFullDate(date: LocalDate): String = date.format(FULL_DATE_FORMATTER)

    // 달력 관련 처리
    fun getFormattedDate(date: LocalDate): String {
        // 여기서 해당 날짜의 요일을 넘겨줘야함
        val dayOfWeek = date.dayOfWeek
        val thisWeek =
            date.with(TemporalAdjusters.previousOrSame(dayOfWeek))
        val formattedDate = thisWeek.format(DAY_FORMATTER)
        return formattedDate
    }

    fun getDayOfWeek(dayOfWeek: Int): String =
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
}
