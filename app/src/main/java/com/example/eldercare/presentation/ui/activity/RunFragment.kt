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
import timber.log.Timber

@AndroidEntryPoint
class RunFragment : BaseFragment<FragmentRunBinding, RunViewModel>(FragmentRunBinding::inflate) {

    // Hilt로 주입되는 RunViewModel
    override val viewModel: RunViewModel by viewModels()

    // 선택된 관계 (예: "부모", "자식" 등)
    private var selectedRelationship: String? = null

    // 추가 연락처는 primary 외 최대 4개 (총 5개)
    private val maxAdditionalContacts = 4

    // AdditionalContactAdapter – 연락처 입력 항목만 관리 (AddButton은 XML에 별도로 있음)
    internal lateinit var additionalContactAdapter: AdditionalContactAdapter

    private val photoPickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.ivProfileImage.setImageURI(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etPrimaryContact.setValidator { input ->
            val currentField = viewModel.getCurrentField()
            if (currentField.id == "ward_emergency_contacts") {
                // 전화번호는 11자리 숫자여야 함
                if (input.length != 11 || !input.all { char -> char.isDigit() }) {
                    "전화번호는 11자리의 숫자여야 합니다."
                } else {
                    // 기본 연락처와 추가 연락처를 모두 합친 리스트에서 중복 체크
                    val allContacts = getAllContacts() // 예: 기본 연락처 + 추가 연락처 목록 반환
                    if (allContacts.count { contact : String -> contact == input } > 1) {
                        "중복된 연락처입니다."
                    } else {
                        null
                    }
                }
            } else {
                // ward_emergency_contacts 단계가 아닌 경우
                if (viewModel.validateInput(currentField.id, input)) {
                    null
                } else {
                    currentField.errorMessage ?: "유효하지 않은 입력입니다. 다시 확인해주세요."
                }
            }
        }

        // 관계 선택 버튼 리스너 설정
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

        // "사진 등록" 버튼 클릭
        binding.btnRegisterPhoto.setOnClickListener {
            registerPhoto()
        }

        // AdditionalContactAdapter 초기화
        additionalContactAdapter = AdditionalContactAdapter(
            onContactChanged = { phone, position ->
                val currentList = additionalContactAdapter.currentList.toMutableList()
                if (currentList.isNotEmpty() && currentList.size > position && currentList[position] is AdditionalContactItem.Contact) {
                    val oldContact = currentList[position] as AdditionalContactItem.Contact
                    currentList[position] = AdditionalContactItem.Contact(id = oldContact.id, phone = phone)
                    //additionalContactAdapter.submitList(currentList)
                }
            },
            getPrimaryContact = { binding.etPrimaryContact.getText() },
            getAllAdditionalContacts = { additionalContactAdapter.currentList.filterIsInstance<AdditionalContactItem.Contact>().map { it.phone } }
        )

        binding.rvAdditionalContacts.adapter = additionalContactAdapter
        // 초기에는 빈 리스트 제출
        additionalContactAdapter.submitList(emptyList())

        // "연락처 추가" 버튼 (XML에 별도로 배치됨) 클릭 시, 새 연락처 항목 추가 (최대 4개)
        binding.btnAddContact.setOnClickListener {
            val currentList = additionalContactAdapter.currentList.toMutableList()
            if (currentList.size >= maxAdditionalContacts) {
                Toast.makeText(requireContext(), "최대 4개 까지 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 새 연락처 항목 추가: 고유 ID를 생성하여 빈 문자열과 함께 추가
            currentList.add(AdditionalContactItem.Contact(id = System.currentTimeMillis(), phone = ""))
            additionalContactAdapter.submitList(currentList)
        }


        viewModel.currentStep.observe(viewLifecycleOwner) { step ->
            renderStep(step)
        }

        // 스크롤뷰와 연동하여 RecyclerView 스크롤을 비활성화
        binding.rvAdditionalContacts.isNestedScrollingEnabled = false
    }

    private fun getAllContacts(): List<String> {
        // 기본 연락처 + 추가 연락처들을 모두 합친 리스트 반환
        val primary = binding.etPrimaryContact.getText()
        val additional = additionalContactAdapter.currentList.filterIsInstance<AdditionalContactItem.Contact>().map { it.phone }
        return listOf(primary) + additional
    }

    /**
     * renderStep() 메소드
     * - 현재 단계에 따라 기본 연락처 입력, 관계 선택, 이미지 등록 등의 UI를 보이게 합니다.
     * - CustomInputType.MULTILINE_TEXT 단계에서는:
     *    • "ward_emergency_contacts" 단계: tv_side_info에 HTML 서식 문자열을 적용하고,
     *       rv_additional_contacts와 btn_add_contact를 보이게 합니다.
     *    • "ward_address" 단계: tv_side_info에 주소 관련 안내 문구를 보입니다.
     */
    private fun renderStep(step: Int) {
        val currentField = viewModel.getCurrentField()
        binding.tvInputTitle.text = currentField.info

        // 모든 주요 UI 요소를 숨김
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
                binding.etPrimaryContact.visibility = View.VISIBLE
                setKeyboardType(currentField.keyboardType)
                when (currentField.id) {
                    "ward_emergency_contacts" -> {
                        Timber.d("ward_emergency_contacts")
                        binding.tvSideInfo.visibility = View.VISIBLE
                        binding.tvSideInfo.text = Html.fromHtml(
                            getString(R.string.ward_emergency_side_info),
                            Html.FROM_HTML_MODE_LEGACY
                        )
                        binding.rvAdditionalContacts.visibility = View.VISIBLE
                        binding.btnAddContact.visibility = View.VISIBLE
                        updateNavigationButtonsBelow(binding.btnAddContact)
                    }
                    "ward_address" -> {
                        Timber.d("ward_address")
                        binding.tvSideInfo.visibility = View.VISIBLE
                        binding.tvSideInfo.text = getString(R.string.ward_address_side_info)
                        updateNavigationButtonsBelow(binding.tvSideInfo)
                    }
                    else -> {
                        binding.tvSideInfo.visibility = View.GONE
                        binding.rvAdditionalContacts.visibility = View.GONE
                        binding.btnAddContact.visibility = View.GONE
                        updateNavigationButtonsBelow(binding.etPrimaryContact)
                    }
                }
            }
        }
        binding.btnPrevious.isEnabled = step > 1
        binding.btnNext.isEnabled = step != viewModel.fields.size
    }

    /**
     * updateNavigationButtonsBelow()
     * ConstraintSet을 사용하여 네비게이션 버튼 컨테이너(ll_navigation_buttons)의 상단을
     * targetView의 하단에 16픽셀 마진을 두고 연결합니다.
     */
    private fun updateNavigationButtonsBelow(targetView: View) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        constraintSet.connect(
            binding.llNavigationButtons.id,
            ConstraintSet.TOP,
            targetView.id,
            ConstraintSet.BOTTOM,
            16
        )
        constraintSet.applyTo(binding.root)
    }

    /**
     * hideAllViews()
     * 모든 주요 UI 요소를 숨깁니다.
     */
    private fun hideAllViews() {
        binding.etPrimaryContact.visibility = View.GONE
        binding.flowRelationshipButtons.visibility = View.GONE
        binding.ivProfileImage.visibility = View.GONE
        binding.btnRegisterPhoto.visibility = View.GONE
        binding.rvAdditionalContacts.visibility = View.GONE
        binding.tvSideInfo.visibility = View.GONE
        binding.btnAddContact.visibility = View.GONE
    }

    /**
     * setKeyboardType()
     * et_primary_contact의 입력 타입을 설정합니다.
     */
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
