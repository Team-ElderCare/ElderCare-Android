package com.example.eldercare.presentation.ui.devicemanage.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentDeviceManageBinding

class DeviceManageFragment : BaseFragment<FragmentDeviceManageBinding, DeviceManageViewModel>(
    FragmentDeviceManageBinding::inflate,
) {
    override val viewModel: DeviceManageViewModel by lazy {
        ViewModelProvider(this)[DeviceManageViewModel::class.java]
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            includeDeviceManageTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            btnDeviceMangerAddDevice.setOnClickListener {
                navigateToAddDevice()
            }
        }
    }

    private fun navigateToAddDevice() {
        val action = DeviceManageFragmentDirections.actionToolFragmentToAddDeviceFragment()
        findNavController().navigate(action)
    }
}
