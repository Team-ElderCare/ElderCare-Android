package com.example.eldercare.presentation.ui.setting

import androidx.lifecycle.ViewModelProvider
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>(
    FragmentSettingsBinding::inflate,
) {
    override val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this)[SettingsViewModel::class.java]
    }
}
