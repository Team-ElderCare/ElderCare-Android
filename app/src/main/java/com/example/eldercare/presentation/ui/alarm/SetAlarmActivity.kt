package com.example.eldercare.presentation.ui.alarm

import android.os.Bundle
import androidx.activity.viewModels
import com.example.eldercare.R
import com.example.eldercare.base.activity.BaseActivity
import com.example.eldercare.databinding.ActivitySetAlarmBinding
import com.example.eldercare.presentation.ui.alarm.name.SetAlarmNameFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetAlarmActivity :
    BaseActivity<ActivitySetAlarmBinding, SetAlarmViewModel>(
        ActivitySetAlarmBinding::inflate,
    ) {
    override val viewModel: SetAlarmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, SetAlarmNameFragment())
            .commit()
    }
}
