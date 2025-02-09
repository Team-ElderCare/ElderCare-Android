package com.example.eldercare.presentation.ui.basicInfo.protectedPerson

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInfoProtectedPersonAddressBinding
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoStep
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoViewModel

class ProtectedAddressFragment : BaseFragment<FragmentInfoProtectedPersonAddressBinding, BasicInfoViewModel>(
    FragmentInfoProtectedPersonAddressBinding::inflate,
) {
    override val viewModel: BasicInfoViewModel by activityViewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        getEditTextInput()
        setupOnClickListener()
    }

    private fun getEditTextInput() {
        updateButtonState(true)

        val savedPhoneNumber = viewModel.getInput(BasicInfoStep.PROTECTED_ADDRESS)

        if (!savedPhoneNumber.isNullOrEmpty()) {
            binding.etPrimary.setText(savedPhoneNumber)
            updateButtonState(true)
        }
    }

    private fun setupOnClickListener() {
        binding.btnAddSuccessNext.setOnClickListener {
            val input = binding.etPrimary.getText()

            viewModel.saveInput(BasicInfoStep.PROTECTED_ADDRESS, input)

            val action = ProtectedAddressFragmentDirections.actionProtectedAddressToProtectedEmergencyContact()
            findNavController().navigate(directions = action)
        }

        binding.btnAddSuccessPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateButtonState(isValid: Boolean) {
        binding.btnAddSuccessNext.isEnabled = isValid
        val color =
            if (isValid) {
                ContextCompat.getColor(requireContext(), R.color.primary)
            } else {
                ContextCompat.getColor(requireContext(), R.color.Gray200)
            }
        binding.btnAddSuccessNext.backgroundTintList = ColorStateList.valueOf(color)
    }
}
