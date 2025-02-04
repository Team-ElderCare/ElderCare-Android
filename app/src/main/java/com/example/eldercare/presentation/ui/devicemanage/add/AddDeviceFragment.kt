package com.example.eldercare.presentation.ui.devicemanage.add

import androidx.lifecycle.ViewModelProvider
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddDeviceBinding

class AddDeviceFragment : BaseFragment<FragmentAddDeviceBinding, AddDeviceViewModel>(
    FragmentAddDeviceBinding::inflate,
) {
    override val viewModel: AddDeviceViewModel by lazy {
        ViewModelProvider(this)[AddDeviceViewModel::class.java]
    }
}
