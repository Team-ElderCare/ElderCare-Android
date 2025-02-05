package com.example.eldercare.presentation.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentRunBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : BaseFragment<FragmentRunBinding, RunViewModel>(FragmentRunBinding::inflate) {
    override val viewModel: RunViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            // 런 액티비티로 이동
            binding.btnStart.setOnClickListener {
            }
        }
    }
}
