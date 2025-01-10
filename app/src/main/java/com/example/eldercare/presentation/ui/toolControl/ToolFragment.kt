package com.example.eldercare.presentation.ui.toolControl

import androidx.lifecycle.ViewModelProvider
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentToolBinding

class ToolFragment : BaseFragment<FragmentToolBinding, ToolViewModel>(
    FragmentToolBinding::inflate,
) {
    override val viewModel: ToolViewModel by lazy {
        ViewModelProvider(this)[ToolViewModel::class.java]
    }
}
