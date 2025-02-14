package com.example.eldercare.presentation.ui.activity

import com.example.eldercare.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserActivityViewModel
    @Inject
    constructor(
        // private val activityRepository: ActivityRepository,
    ) : BaseViewModel() {
        private val _activityList = MutableStateFlow<List<UserActivityItem>>(emptyList())
        val activityList = _activityList.asStateFlow()

        fun getActivities(date: String) {
            Timber.d("클릭된 날짜 :$date")
//            viewModelScope.launch {
//                activityRepository.getActivities(date)
//            }
        }
    }
