package com.example.eldercare.presentation.ui.devicemanage.inputlocation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInputDeviceLocationBinding

class DeviceInputLocationFragment : BaseFragment<FragmentInputDeviceLocationBinding, DeviceInputViewModel>(
    FragmentInputDeviceLocationBinding :: inflate
) {
    override val viewModel: DeviceInputViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
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
        }
    }

    private fun enableTextField(isVisible: Boolean) {
        binding.etInputDeviceLocation.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

}
