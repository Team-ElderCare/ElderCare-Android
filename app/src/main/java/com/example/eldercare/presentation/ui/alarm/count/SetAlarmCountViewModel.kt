package com.example.eldercare.presentation.ui.alarm.count

import com.example.eldercare.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SetAlarmCountViewModel
    @Inject
    constructor() : BaseViewModel() {
        private val _count = MutableStateFlow<Int>(0)
        val count = _count.asStateFlow()

        fun addCount() {
            _count.value += 1
        }

        fun reduceCount() {
            if (_count.value == 0) return
            _count.value -= 1
        }
    }
