package com.example.eldercare.presentation.ui.activity

import androidx.lifecycle.ViewModelProvider
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentRunBinding

class RunFragment : BaseFragment<FragmentRunBinding, RunViewModel>(FragmentRunBinding::inflate) {
    override val viewModel: RunViewModel by lazy {
        ViewModelProvider(this)[RunViewModel::class.java]
    }
}
