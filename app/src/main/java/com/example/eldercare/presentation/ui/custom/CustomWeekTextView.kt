package com.example.eldercare.presentation.ui.custoom

import android.content.Context
import android.util.AttributeSet
import android.view.InflateException
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.eldercare.R
import timber.log.Timber

class CustomWeekTextView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int = 0,
    ) : ConstraintLayout(context, attrs, defStyleAttr) {
        private lateinit var dateTextView: TextView
        private lateinit var dayOfWeekTextView: TextView

        init {
            initializeView()
            getAttrs(attrs, defStyleAttr)
        }

        private fun initializeView() {
            val view =
                LayoutInflater
                    .from(context)
                    .inflate(R.layout.custom_calendar_textview, this, true)

            dateTextView = view.findViewById(R.id.tv_date)
            dayOfWeekTextView = view.findViewById(R.id.tv_day_of_week)
        }

        fun setDate(date: String) {
            dateTextView.text = date
        }

        fun setDayOfWeek(dayOfWeek: String) {
            dayOfWeekTextView.text = dayOfWeek
        }

        private fun getAttrs(
            attrs: AttributeSet?,
            defStyleAttr: Int,
        ) {
            val typedArray =
                context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomWeekTextView,
                    defStyleAttr,
                    0,
                )
            try {
                typedArray.getString(R.styleable.CustomWeekTextView_customWeekDate)?.let {
                    dateTextView.text = it
                }
                typedArray.getString(R.styleable.CustomWeekTextView_customWeekDayOfWeek)?.let {
                    dayOfWeekTextView.text = it
                }
            } catch (e: InflateException) {
                e.printStackTrace()
                Timber.d("failed to inflate :${e.message}")
            } finally {
                typedArray.recycle()
            }
        }
    }
