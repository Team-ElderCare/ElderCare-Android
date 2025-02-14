package com.example.eldercare.presentation.ui.devicemanage.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddDeviceSuccessBinding
import com.example.eldercare.presentation.ui.devicemanage.add.inputlocation.DeviceInputViewModel

class AddDeviceSuccessFragment : BaseFragment<FragmentAddDeviceSuccessBinding, DeviceInputViewModel>(
    FragmentAddDeviceSuccessBinding::inflate,
) {
    override val viewModel: DeviceInputViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            includeAddSuccessTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            btnAddSuccessPrevious.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            btnAddSuccessNext.setOnClickListener {
                navigateToDeviceManageHome()
            }
        }
    }

    private fun navigateToDeviceManageHome() {
        val action = AddDeviceSuccessFragmentDirections.actionAddDeviceSuccessFragmentToDeviceManageFragment()
        findNavController().navigate(action)
    }
}
