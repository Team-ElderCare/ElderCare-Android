package com.example.eldercare.presentation.ui.basicInfo.protectedPerson

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInfoProtectedPersonPhoneBinding
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoStep
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoViewModel

class ProtectedPhoneFragment : BaseFragment<FragmentInfoProtectedPersonPhoneBinding, BasicInfoViewModel>(
    FragmentInfoProtectedPersonPhoneBinding::inflate,
) {
    override val viewModel: BasicInfoViewModel by activityViewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        getEditTextInput()
        configureInputField()
        setupOnClickListener()
    }

    private fun getEditTextInput() {
        updateButtonState(false)

        val savedPhoneNumber = viewModel.getInput(BasicInfoStep.PROTECTED_PHONE)

        if (!savedPhoneNumber.isNullOrEmpty()) {
            binding.etPrimary.setText(savedPhoneNumber)
            updateButtonState(true)
        }
    }

    private fun configureInputField() {
        binding.etPrimary.apply {
            setValidator { input ->
                val errorMessage = validateInput(input)
                val isValid = errorMessage == null

                updateButtonState(isValid)

                errorMessage
            }

            setInputType(InputType.TYPE_CLASS_PHONE)
            requestFocusAndShowKeyboard()
        }
    }

    private fun setupOnClickListener() {
        binding.btnAddSuccessNext.setOnClickListener {
            val input = binding.etPrimary.getText()

            viewModel.saveInput(BasicInfoStep.PROTECTED_PHONE, input)

            val action = ProtectedPhoneFragmentDirections.actionProtectedPhoneToProtectedAddress()
            findNavController().navigate(directions = action)
        }

        binding.btnAddSuccessPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateInput(input: String): String? {
        return if (input.length == 11 && input.all { it.isDigit() }) {
            null
        } else {
            "잘못된 형식의 전화번호입니다."
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
