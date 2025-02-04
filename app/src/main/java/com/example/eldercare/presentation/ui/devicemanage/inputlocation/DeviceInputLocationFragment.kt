package com.example.eldercare.presentation.ui.devicemanage.inputlocation

import androidx.fragment.app.viewModels
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInputDeviceLocationBinding

class DeviceInputLocationFragment : BaseFragment<FragmentInputDeviceLocationBinding, DeviceInputLocationViewModel>(
    FragmentInputDeviceLocationBinding :: inflate
) {
    override val viewModel: DeviceInputLocationViewModel by viewModels()
}
