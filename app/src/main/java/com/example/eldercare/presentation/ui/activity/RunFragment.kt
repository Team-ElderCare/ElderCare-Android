package com.example.eldercare.presentation.ui.activity

import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.viewModels
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentRunBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : BaseFragment<FragmentRunBinding, RunViewModel>(FragmentRunBinding::inflate) {

    override val viewModel: RunViewModel by viewModels()

    // 선택된 관계 (예: "부모", "자식" 등)
    private var selectedRelationship: String? = null

    // 추가 연락처는 최대 4개 (즉, 총 연락처 개수는 primary + 추가 최대 4개 = 5개)
    private val maxAdditionalContacts = 4

    // 추가 연락처는 XML의 LinearLayout 내 4개의 CustomEditText로 관리함.
    // (각 EditText는 binding.etAdditionalContact1 ~ binding.etAdditionalContact4로 접근)
    // 현재 몇 개가 visible한지를 관리하는 변수 (추가된 개수)
    private var additionalVisibleCount: Int = 0

    private val photoPickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { binding.ivProfileImage.setImageURI(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // primary 연락처 CustomEditText 유효성 검사 설정
        binding.etPrimaryContact.setValidator { input ->
            val currentField = viewModel.getCurrentField()
            if (currentField.id == "ward_emergency_contacts") {
                // 11자리 숫자 검사
                if (input.length != 11 || !input.all { char -> char.isDigit() }) {
                    "전화번호는 11자리의 숫자여야 합니다."
                } else {
                    // 기본 연락처와 추가 연락처(모두 합친 값)에서 중복 여부 검사
                    val allContacts = getAllContacts()
                    if (allContacts.count { contact: String -> contact == input } > 1) {
                        "중복된 연락처입니다."
                    } else {
                        null
                    }
                }
            } else {
                if (viewModel.validateInput(currentField.id, input)) {
                    null
                } else {
                    currentField.errorMessage ?: "유효하지 않은 입력입니다. 다시 확인해주세요."
                }
            }
        }

        setupRelationshipButtonListeners()

        // "다음으로" 버튼 클릭 (단계 진행)
        binding.btnNext.setOnClickListener {
            val currentField = viewModel.getCurrentField()
            val inputValue = when (currentField.inputType) {
                CustomInputType.TEXT, CustomInputType.MULTILINE_TEXT -> binding.etPrimaryContact.getText()
                CustomInputType.IMAGE_PICKER -> "Image Selected"
                CustomInputType.BUTTON_GROUP -> selectedRelationship ?: ""
                else -> ""
            }
            if (viewModel.validateInput(currentField.id, inputValue)) {
                viewModel.saveInput(currentField.id, inputValue)
                if (viewModel.nextStep()) {
                    val nextField = viewModel.getCurrentField()
                    val nextInputValue = viewModel.getInput(nextField.id) ?: ""
                    if (nextField.inputType == CustomInputType.TEXT ||
                        nextField.inputType == CustomInputType.MULTILINE_TEXT) {
                        binding.etPrimaryContact.setText(nextInputValue)
                    } else {
                        binding.etPrimaryContact.clearText()
                    }
                }
            }
        }

        // "이전으로" 버튼 클릭 (단계 복원)
        binding.btnPrevious.setOnClickListener {
            viewModel.previousStep()
            val previousField = viewModel.getCurrentField()
            val previousInputValue = viewModel.getInput(previousField.id) ?: ""
            if (previousField.inputType == CustomInputType.TEXT ||
                previousField.inputType == CustomInputType.MULTILINE_TEXT) {
                binding.etPrimaryContact.setText(previousInputValue)
            }
        }



        binding.btnRegisterPhoto.setOnClickListener { registerPhoto() }

        // "연락처 추가" 버튼 클릭 시, 추가 연락처 필드를 하나씩 보이도록 함.
        binding.btnAddContact.setOnClickListener {
            if (additionalVisibleCount >= maxAdditionalContacts) {
                Toast.makeText(requireContext(), "최대 4개 까지 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            additionalVisibleCount++
            updateAdditionalContactFields()
        }


        viewModel.currentStep.observe(viewLifecycleOwner) { step ->
            renderStep(step)
        }

        // NestedScrollView 내의 ll_additional_contacts는 기본적으로 invisible
        binding.llAdditionalContacts.visibility = View.INVISIBLE

        viewModel.currentStep.observe(viewLifecycleOwner) { step ->
            renderStep(step)
        }
    }

    // getAllContacts() returns primary + additional contacts as a list of strings.
    private fun getAllContacts(): List<String> {
        val primary = binding.etPrimaryContact.getText()
        val additional = mutableListOf<String>()
        if (binding.etAdditionalContact1.visibility == View.VISIBLE) additional.add(binding.etAdditionalContact1.getText())
        if (binding.etAdditionalContact2.visibility == View.VISIBLE) additional.add(binding.etAdditionalContact2.getText())
        if (binding.etAdditionalContact3.visibility == View.VISIBLE) additional.add(binding.etAdditionalContact3.getText())
        if (binding.etAdditionalContact4.visibility == View.VISIBLE) additional.add(binding.etAdditionalContact4.getText())
        return listOf(primary) + additional
    }

    // getCurrentAdditionalContacts() returns the list of texts from additional fields (without primary)
    private fun getCurrentAdditionalContacts(): List<String> {
        val additional = mutableListOf<String>()
        if (binding.etAdditionalContact1.visibility == View.VISIBLE) additional.add(binding.etAdditionalContact1.getText())
        if (binding.etAdditionalContact2.visibility == View.VISIBLE) additional.add(binding.etAdditionalContact2.getText())
        if (binding.etAdditionalContact3.visibility == View.VISIBLE) additional.add(binding.etAdditionalContact3.getText())
        if (binding.etAdditionalContact4.visibility == View.VISIBLE) additional.add(binding.etAdditionalContact4.getText())
        return additional
    }

    // updateAdditionalContactFields() 업데이트: 입력값을 복원하거나, 추가하기 버튼 클릭 시, visible 상태와 텍스트 설정
    private fun updateAdditionalContactFields(saved: List<String> = getCurrentAdditionalContacts()) {
        val fields = listOf(
            binding.etAdditionalContact1,
            binding.etAdditionalContact2,
            binding.etAdditionalContact3,
            binding.etAdditionalContact4
        )
        // 추가된 항목이 하나라도 있으면 additionalVisibleCount > 0이므로, 무조건 VISIBLE로 설정
        binding.llAdditionalContacts.visibility = if (additionalVisibleCount > 0) View.VISIBLE else View.INVISIBLE
        for (i in fields.indices) {
            if (i < additionalVisibleCount) {
                fields[i].visibility = View.VISIBLE
                // saved 리스트에 값이 있으면 해당 값을, 없으면 빈 문자열로 설정
                fields[i].setText(if (i < saved.size) saved[i] else "")
                fields[i].setValidator { input ->
                    if (input.length != 11 || !input.all { char -> char.isDigit() }) {
                        "전화번호는 11자리의 숫자여야 합니다."
                    } else {
                        val all = getAllContacts()
                        if (all.count { contact: String -> contact == input } > 1) "중복된 연락처입니다." else null
                    }
                }
            } else {
                fields[i].visibility = View.GONE
            }
        }
    }

    private fun renderStep(step: Int) {
        val currentField = viewModel.getCurrentField()
        binding.tvInputTitle.text = currentField.info

        hideAllViews()

        when (currentField.inputType) {
            CustomInputType.TEXT -> {
                binding.etPrimaryContact.visibility = View.VISIBLE
                binding.etPrimaryContact.setHint(currentField.hint)
                setKeyboardType(currentField.keyboardType)
                updateNavigationButtonsBelow(binding.etPrimaryContact)
            }
            CustomInputType.BUTTON_GROUP -> {
                binding.flowRelationshipButtons.visibility = View.VISIBLE
                updateNavigationButtonsBelow(binding.flowRelationshipButtons)
            }
            CustomInputType.IMAGE_PICKER -> {
                binding.ivProfileImage.visibility = View.VISIBLE
                binding.btnRegisterPhoto.visibility = View.VISIBLE
                updateNavigationButtonsBelow(binding.btnRegisterPhoto)
            }
            CustomInputType.MULTILINE_TEXT -> {
                if (currentField.id == "ward_emergency_contacts") {
                    binding.etPrimaryContact.visibility = View.VISIBLE
                    binding.tvSideInfo.visibility = View.VISIBLE
                    binding.tvSideInfo.text = Html.fromHtml(
                        getString(R.string.ward_emergency_side_info),
                        Html.FROM_HTML_MODE_LEGACY
                    )
                    binding.llAdditionalContacts.visibility = View.VISIBLE
                    binding.btnAddContact.visibility = View.VISIBLE
                    updateNavigationButtonsBelow(binding.btnAddContact)
                } else if (currentField.id == "ward_address") {
                    binding.tvSideInfo.visibility = View.VISIBLE
                    binding.tvSideInfo.text = getString(R.string.ward_address_side_info)
                    updateNavigationButtonsBelow(binding.tvSideInfo)
                }
            }
        }
        binding.btnPrevious.isEnabled = step > 1
        binding.btnNext.isEnabled = step != viewModel.fields.size
    }

    private fun updateNavigationButtonsBelow(targetView: View) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        constraintSet.connect(
            binding.llAdditionalContacts.id,
            ConstraintSet.TOP,
            targetView.id,
            ConstraintSet.BOTTOM,
            16
        )
        constraintSet.applyTo(binding.root)
    }

    private fun hideAllViews() {
        binding.etPrimaryContact.visibility = View.GONE
        binding.flowRelationshipButtons.visibility = View.GONE
        binding.ivProfileImage.visibility = View.GONE
        binding.btnRegisterPhoto.visibility = View.GONE
        binding.llAdditionalContacts.visibility = View.GONE
        binding.tvSideInfo.visibility = View.GONE
        binding.btnAddContact.visibility = View.GONE
        binding.etPrimaryContact.visibility = View.GONE
    }

    private fun setKeyboardType(keyboardType: CustomKeyboardType) {
        binding.etPrimaryContact.setInputType(
            when (keyboardType) {
                CustomKeyboardType.DEFAULT -> InputType.TYPE_CLASS_TEXT
                CustomKeyboardType.NUMERIC -> InputType.TYPE_CLASS_NUMBER
                CustomKeyboardType.PHONE -> InputType.TYPE_CLASS_PHONE
            }
        )
    }

    private fun setupRelationshipButtonListeners() {
        val relationshipButtons = listOf(
            binding.btnParent to "부모",
            binding.btnChild to "자식",
            binding.btnSibling to "형제",
            binding.btnCousin to "사촌",
            binding.btnFriend to "친구",
            binding.btnCaregiver to "간병인",
            binding.btnOther to "기타"
        )
        relationshipButtons.forEach { (button, relationship) ->
            button.setOnClickListener {
                selectRelationship(button, relationship)
            }
        }
    }

    private fun selectRelationship(selectedButton: Button, relationship: String) {
        val relationshipButtons = listOf(
            binding.btnParent,
            binding.btnChild,
            binding.btnSibling,
            binding.btnCousin,
            binding.btnFriend,
            binding.btnCaregiver,
            binding.btnOther
        )
        relationshipButtons.forEach { it.isSelected = false }
        selectedButton.isSelected = true
        selectedRelationship = relationship
        binding.btnNext.isEnabled = true
    }

    private fun registerPhoto() {
        photoPickerLauncher.launch("image/*")
    }
}
