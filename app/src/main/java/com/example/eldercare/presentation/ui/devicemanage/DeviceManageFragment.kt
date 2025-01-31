package com.example.eldercare.presentation.ui.devicemanage

import androidx.lifecycle.ViewModelProvider
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentDeviceManageBinding

class DeviceManageFragment : BaseFragment<FragmentDeviceManageBinding, DeviceManageViewModel>(
    FragmentDeviceManageBinding::inflate,
) {
    override val viewModel: DeviceManageViewModel by lazy {
        ViewModelProvider(this)[DeviceManageViewModel::class.java]
    }
}
