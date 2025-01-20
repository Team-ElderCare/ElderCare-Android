package com.example.eldercare.presentation.ui.alarm.name

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentSetAlarmNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetAlarmNameFragment :
    BaseFragment<FragmentSetAlarmNameBinding, SetAlarmNameViewModel>(
        FragmentSetAlarmNameBinding::inflate,
    ) {
    override val viewModel: SetAlarmNameViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

//        binding.textFieldLayout.error = "이미 등록된 이름입니다. 다른 이름으로 등록해주세요"
//        binding.textInputEditText.setBackgroundResource(R.drawable.rectangle_red_with_stroke_white)

        binding.btnNext.isSelected = false

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.setAlarmCycleFragment)
        }
    }
}
