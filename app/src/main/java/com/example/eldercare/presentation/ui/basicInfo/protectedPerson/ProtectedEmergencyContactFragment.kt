package com.example.eldercare.presentation.ui.basicInfo.protectedPerson

import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInfoProtectedPersonEmergencyContactBinding
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoStep
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoViewModel
import com.example.eldercare.presentation.ui.custom.CustomEditText

class ProtectedEmergencyContactFragment : BaseFragment<FragmentInfoProtectedPersonEmergencyContactBinding, BasicInfoViewModel>(
    FragmentInfoProtectedPersonEmergencyContactBinding::inflate,
) {
    override val viewModel: BasicInfoViewModel by activityViewModels()
    private lateinit var additionalContacts: List<CustomEditText>

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initAdditionalContacts()
        binding.tvSideInfo.text = Html.fromHtml(getString(R.string.ward_emergency_side_info), Html.FROM_HTML_MODE_LEGACY)

        loadSavedContacts()
        setupValidation()
        setupOnClickListener()
    }

    private fun initAdditionalContacts() {
        additionalContacts =
            listOf(
                binding.etAdditionalContact1,
                binding.etAdditionalContact2,
                binding.etAdditionalContact3,
                binding.etAdditionalContact4,
            )
    }

    private fun loadSavedContacts() {
        val savedContacts =
            viewModel.getInput(BasicInfoStep.PROTECTED_EMERGENCY_CONTACTS)
                ?.split(",") ?: emptyList()

        if (savedContacts.isNotEmpty()) {
            binding.llAdditionalContacts.visibility = View.VISIBLE
            binding.etPrimary.setText(savedContacts.first())
        }

        savedContacts.drop(1).forEachIndexed { index, contact ->
            if (index in additionalContacts.indices) {
                additionalContacts[index].apply {
                    setText(contact)
                    visibility = View.VISIBLE
                }
            }
        }

        focusLastEnteredContact(savedContacts.size)
    }

    private fun focusLastEnteredContact(contactCount: Int) {
        binding.root.post {
            val targetEditText =
                if (contactCount > 1) {
                    additionalContacts.getOrNull(contactCount - 2) ?: binding.etPrimary
                } else {
                    binding.etPrimary
                }

            targetEditText.requestFocus()
            targetEditText.requestFocusAndShowKeyboard()
        }
    }

    private fun setupValidation() {
        configureEditText(binding.etPrimary, isPrimary = true)
        additionalContacts.forEach { editText ->
            configureEditText(editText, isPrimary = false)
        }
    }

    private fun configureEditText(
        editText: CustomEditText,
        isPrimary: Boolean,
    ) {
        editText.apply {
            setValidator { input ->
                val errorMessage = validateInput(input, isPrimary)
                if (errorMessage == null) updateSavedContacts()
                errorMessage
            }
            setInputType(InputType.TYPE_CLASS_PHONE)
        }
    }

    private fun setupOnClickListener() {
        binding.btnAddContact.setOnClickListener {
            val nextContact = additionalContacts.firstOrNull { it.visibility == View.GONE }
            if (nextContact != null) {
                nextContact.visibility = View.VISIBLE
                binding.llAdditionalContacts.visibility = View.VISIBLE

                binding.nestedScroll.post {
                    binding.nestedScroll.fullScroll(View.FOCUS_DOWN)
                    nextContact.requestFocus()
                    nextContact.requestFocusAndShowKeyboard()
                }
            }
        }

        binding.btnAddSuccessNext.setOnClickListener { updateSavedContacts() }
        binding.btnAddSuccessPrevious.setOnClickListener { findNavController().popBackStack() }
    }

    private fun validateInput(
        input: String,
        isPrimary: Boolean,
    ): String? {
        if (input.isEmpty()) {
            if (isPrimary && getVisibleContacts().all { it.isEmpty() }) {
                return "잘못된 형식의 전화번호입니다."
            }
            return null
        }

        if (input.length != 11 || !input.all { it.isDigit() }) {
            return "잘못된 형식의 전화번호입니다."
        }

        val nonEmptyContacts = getVisibleContacts().filter { it.isNotEmpty() }
        if (nonEmptyContacts.count { it == input } > 1) {
            return "중복된 전화번호가 존재합니다."
        }

        return null
    }

    private fun getVisibleContacts(): List<String> {
        return listOf(binding.etPrimary.getText()) +
            additionalContacts.filter { it.visibility == View.VISIBLE }
                .map { it.getText() }
    }

    private fun updateSavedContacts() {
        val allContacts = getVisibleContacts()
        val validContacts =
            allContacts.filter {
                it.isNotEmpty() && validateInput(it, isPrimary = false) == null
            }
        if (validContacts.isEmpty()) {
            binding.etPrimary.setError("잘못된 형식의 전화번호입니다.")
            return
        }
        viewModel.saveInput(BasicInfoStep.PROTECTED_EMERGENCY_CONTACTS, validContacts.joinToString(","))
    }
}
