package com.example.eldercare.presentation.ui.devicemanage.edit

import android.os.Bundle
import android.view.View
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentEditAndDeleteDeviceBinding

class DeviceEditAndDeleteFragment : BaseFragment<FragmentEditAndDeleteDeviceBinding, Nothing>(
    FragmentEditAndDeleteDeviceBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListener()
    }

    private fun addListener() {
        with(binding) {
            val nameCg = includeDeviceEditAndDeleteDeviceNameCg
            nameCg.cgViewPlaceChipGroup.setOnClickListener {
                if (nameCg.chipViewPlaceChipGroupEnterManually.isChecked) {
                    enableNameTextField(true)
                    nameCg.cgViewPlaceChipGroup.clearCheck()
                    nameCg.chipViewPlaceChipGroupEnterManually.isChecked = true
                } else {
                    enableNameTextField(false)
                }
            }

            nameCg.cgViewPlaceChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.contains(nameCg.chipViewPlaceChipGroupEnterManually.id)) {
                    enableNameTextField(true)
                } else {
                    enableNameTextField(false)
                }
            }

            val placeCg = includeDeviceEditAndDeletePlaceCg
            placeCg.cgViewPlaceChipGroup.setOnClickListener {
                if (placeCg.chipViewPlaceChipGroupEnterManually.isChecked) {
                    enableLocationTextField(true)
                    placeCg.cgViewPlaceChipGroup.clearCheck()
                    placeCg.chipViewPlaceChipGroupEnterManually.isChecked = true
                } else {
                    enableLocationTextField(false)
                }
            }

            placeCg.cgViewPlaceChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.contains(placeCg.chipViewPlaceChipGroupEnterManually.id)) {
                    enableLocationTextField(true)
                } else {
                    enableLocationTextField(false)
                }
            }

            btnDeviceEditAndDeletePrevious.setOnClickListener {

            }
            btnDeviceEditAndDeleteNext.setOnClickListener {

            }
        }
    }

    private fun enableNameTextField(isVisible: Boolean) {
        binding.etDeviceEditAndDeleteDeviceName.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun enableLocationTextField(isVisible: Boolean) {
        binding.etDeviceEditAndDeleteLocation.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

}
