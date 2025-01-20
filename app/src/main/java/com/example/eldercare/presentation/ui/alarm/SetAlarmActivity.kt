package com.example.eldercare.presentation.ui.alarm

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.example.eldercare.base.activity.BaseActivity
import com.example.eldercare.databinding.ActivitySetAlarmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetAlarmActivity :
    BaseActivity<ActivitySetAlarmBinding, SetAlarmViewModel>(
        ActivitySetAlarmBinding::inflate,
    ) {
    override val viewModel: SetAlarmViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
