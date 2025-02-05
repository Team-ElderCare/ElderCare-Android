package com.example.eldercare.presentation.ui.devicemanage.add.inputlocation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInputDeviceNameBinding

class DeviceInputNameFragment : BaseFragment<FragmentInputDeviceNameBinding, DeviceInputViewModel> (
    FragmentInputDeviceNameBinding::inflate,
) {
    override val viewModel: DeviceInputViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            includeInputDeviceNameTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            chipInputDeviceNameEnterManually.setOnClickListener {
                if (chipInputDeviceNameEnterManually.isChecked) {
                    enableTextField(true)
                    cgInputDeviceName.clearCheck()
                    chipInputDeviceNameEnterManually.isChecked = true
                } else {
                    enableTextField(false)
                }
            }
            cgInputDeviceName.setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.contains(chipInputDeviceNameEnterManually.id)) {
                    enableTextField(true)
                } else {
                    enableTextField(false)
                }
            }

            btnInputDeviceNamePrevious.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun enableTextField(isVisible: Boolean) {
        binding.etInputDeviceName.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
