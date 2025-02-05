package com.example.eldercare.presentation.ui.devicemanage.add.inputlocation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInputDeviceLocationBinding
import com.example.eldercare.presentation.ui.devicemanage.add.AddDeviceFragmentDirections

class DeviceInputLocationFragment : BaseFragment<FragmentInputDeviceLocationBinding, DeviceInputViewModel>(
    FragmentInputDeviceLocationBinding::inflate,
) {
    override val viewModel: DeviceInputViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            includeInputDeviceLocationTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            chipInputDeviceLocationEnterManually.setOnClickListener {
                if (chipInputDeviceLocationEnterManually.isChecked) {
                    enableTextField(true)
                    cgInputDeviceLocation.clearCheck()
                    chipInputDeviceLocationEnterManually.isChecked = true
                } else {
                    enableTextField(false)
                }
            }

            cgInputDeviceLocation.setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.contains(chipInputDeviceLocationEnterManually.id)) {
                    enableTextField(true)
                } else {
                    enableTextField(false)
                }
            }

            btnInputDeviceLocationNext.setOnClickListener {
                navigateToNameInput()
            }
        }
    }

    private fun enableTextField(isVisible: Boolean) {
        binding.etInputDeviceLocation.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun navigateToNameInput() {
        val action = DeviceInputLocationFragmentDirections.actionDeviceInputLocationFragmentToDeviceInputNameFragment()
        findNavController().navigate(action)
    }

}
