package com.example.eldercare.presentation.ui.devicemanage.add.inputlocation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
            val cg = includeInputDeviceNameViewTitleWithCg
            cg.chipViewTitleWithChipGroupEnterManually.setOnClickListener {
                if (cg.chipViewTitleWithChipGroupEnterManually.isChecked) {
                    enableTextField(true)
                    cg.cgViewTitleWithChipGroup.clearCheck()
                    cg.chipViewTitleWithChipGroupEnterManually.isChecked = true
                } else {
                    enableTextField(false)
                }
            }
            cg.cgViewTitleWithChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.contains(cg.chipViewTitleWithChipGroupEnterManually.id)) {
                    enableTextField(true)
                } else {
                    enableTextField(false)
                }
            }

            btnInputDeviceNameNext.setOnClickListener {
                navigateToSuccess()
            }

            btnInputDeviceNamePrevious.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun enableTextField(isVisible: Boolean) {
        binding.etInputDeviceName.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun navigateToSuccess() {
        val action = DeviceInputNameFragmentDirections.actionDeviceInputNameFragmentToAddDeviceSuccessFragment()
        findNavController().navigate(action)
    }
}
