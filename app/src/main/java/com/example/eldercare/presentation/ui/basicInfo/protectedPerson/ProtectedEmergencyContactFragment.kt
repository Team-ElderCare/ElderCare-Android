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
                binding.etAdditionalContact5,
            )
    }

    private fun loadSavedContacts() {
        val savedContacts =
            viewModel.getInput(BasicInfoStep.PROTECTED_EMERGENCY_CONTACTS)
                ?.split(",") ?: emptyList()

        if (savedContacts.isNotEmpty()) {
            binding.llAdditionalContacts.visibility = View.VISIBLE
        }

        savedContacts.forEachIndexed { index, contact ->
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
            val targetEditText = additionalContacts.getOrNull(contactCount - 1) ?: additionalContacts[0]
            targetEditText.requestFocus()
            targetEditText.requestFocusAndShowKeyboard()
        }
    }

    private fun setupValidation() {
        additionalContacts.forEach { editText ->
            configureEditText(editText)
        }
    }

    private fun configureEditText(editText: CustomEditText) {
        editText.apply {
            setValidator { input ->
                val errorMessage = validateInput(input)
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

    private fun validateInput(input: String): String? {
        val cleanedInput = input.replace("-", "") // ✅ "-" 제거 후 숫자만 추출

        if (cleanedInput.isEmpty()) return null

        if ((cleanedInput.length != 10 && cleanedInput.length != 11) || !cleanedInput.all { it.isDigit() }) {
            return "잘못된 형식의 전화번호입니다."
        }

        // (010-XXXX-XXXX 또는 011-XXX-XXXX) 체크
        val phonePattern = Regex("^01[016789]-\\d{3,4}-\\d{4}$")
        if (input.contains("-") && !phonePattern.matches(input)) {
            return "잘못된 형식의 전화번호입니다."
        }

        val nonEmptyContacts =
            getVisibleContacts()
                .map { it.replace("-", "") }

        if (nonEmptyContacts.count { it == cleanedInput } > 1) {
            return "중복된 전화번호가 존재합니다."
        }

        return null
    }

    private fun getVisibleContacts(): List<String> {
        return additionalContacts.filter { it.visibility == View.VISIBLE }
            .map { it.getText() }
    }

    private fun updateSavedContacts() {
        val allContacts = getVisibleContacts()
        val validContacts =
            allContacts.filter {
                it.isNotEmpty() && validateInput(it) == null
            }

        if (validContacts.isEmpty()) {
            additionalContacts[0].setError("잘못된 형식의 전화번호입니다.")
            return
        }

        viewModel.saveInput(BasicInfoStep.PROTECTED_EMERGENCY_CONTACTS, validContacts.joinToString(","))
    }
}
