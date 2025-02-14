package com.example.eldercare.presentation.ui.basicInfo.guardian

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInfoGuardianPhoneBinding
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoStep
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuardianPhoneFragment : BaseFragment<FragmentInfoGuardianPhoneBinding, BasicInfoViewModel>(
    FragmentInfoGuardianPhoneBinding::inflate,
) {
    override val viewModel: BasicInfoViewModel by activityViewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        getEditTextInput()
        configureInputField()
        setupOnClickListener()
    }

    private fun getEditTextInput() {
        updateButtonState(false)

        val savedPhoneNumber = viewModel.getInput(BasicInfoStep.GUARDIAN_PHONE)

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

            viewModel.saveInput(BasicInfoStep.GUARDIAN_PHONE, input)

            val action = GuardianPhoneFragmentDirections.actionGuardianPhoneToGuardianRelationship()
            findNavController().navigate(directions = action)
        }

        binding.btnAddSuccessPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateInput(input: String): String? {
        val cleanedInput = input.replace("-", "")

        if (cleanedInput.isEmpty()) return null

        if (cleanedInput.length != 11 || !cleanedInput.all { it.isDigit() }) {
            return "잘못된 형식의 전화번호입니다."
        }

        val phonePattern = Regex("^010-\\d{4}-\\d{4}$")
        if (input.contains("-") && !phonePattern.matches(input)) {
            return "잘못된 형식의 전화번호입니다."
        }

        return null
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
