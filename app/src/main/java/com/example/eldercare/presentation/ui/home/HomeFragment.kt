package com.example.eldercare.presentation.ui.home

import androidx.lifecycle.ViewModelProvider
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
}
