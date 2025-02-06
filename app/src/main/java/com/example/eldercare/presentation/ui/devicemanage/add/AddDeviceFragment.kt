package com.example.eldercare.presentation.ui.devicemanage.add

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddDeviceBinding

class AddDeviceFragment : BaseFragment<FragmentAddDeviceBinding, AddDeviceViewModel>(
    FragmentAddDeviceBinding::inflate,
) {
    override val viewModel: AddDeviceViewModel by lazy {
        ViewModelProvider(this)[AddDeviceViewModel::class.java]
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            includeAddDeviceTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            containerAddDeviceHub.setOnClickListener {
                navigateToAddHubFirstFragment()
            }
            containerAddDeviceTag.setOnClickListener {
                navigateToDeviceInput()
            }
            containerDeviceMangerSensor.setOnClickListener {
                navigateToDeviceInput()
            }
        }
    }

    private fun navigateToDeviceInput() {
        val action = AddDeviceFragmentDirections.actionAddDeviceFragmentToDeviceInputLocationFragment()
        findNavController().navigate(action)
    }

    private fun navigateToAddHubFirstFragment() {
        val action = AddDeviceFragmentDirections.actionAddDeviceFragmentToFirstAddHubGuideFragment()
        findNavController().navigate(action)
    }

}
