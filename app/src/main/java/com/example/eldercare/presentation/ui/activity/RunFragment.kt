package com.example.eldercare.presentation.ui.activity

import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.viewModels
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentRunBinding
import com.example.eldercare.presentation.ui.custom.CustomEditText
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RunFragment : BaseFragment<FragmentRunBinding, RunViewModel>(FragmentRunBinding::inflate) {
    // Hilt를 통해 RunViewModel 주입
    override val viewModel: RunViewModel by viewModels()

    // 버튼 그룹 입력 타입에서 선택된 관계(예: "부모", "자식" 등)를 저장하는 변수
    private var selectedRelationship: String? = null

    // 추가 연락처 입력용 CustomEditText들을 저장할 리스트 (최대 5개)
    private val additionalContactViews = mutableListOf<CustomEditText>()

    private val photoPickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // 사용자가 선택한 이미지를 ImageView에 설정합니다.
            binding.ivProfileImage.setImageURI(uri)
            // 필요한 경우, 선택한 사진의 URI를 ViewModel이나 다른 곳에 저장하거나 추가 처리를 할 수 있습니다.
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // CustomEditText(etPrimaryContact)에 유효성 검사 함수를 설정합니다.
        // 이 함수는 사용자가 입력한 값(input)을 RunViewModel의 validateInput 함수를 통해 검증합니다.
        binding.etPrimaryContact.setValidator { input ->
            // 현재 단계의 필드 정보를 ViewModel에서 가져옵니다.
            val currentField = viewModel.getCurrentField()
            // 입력값이 유효하면 null을 반환하여 에러 메시지를 제거합니다.
            if (viewModel.validateInput(currentField.id, input)) {
                null
            } else {
                // 유효하지 않으면 필드에 정의된 에러 메시지를 반환합니다.
                // 만약 에러 메시지가 없다면 기본 메시지를 사용합니다.
                currentField.errorMessage ?: "유효하지 않은 입력입니다. 다시 확인해주세요."
            }
        }

        // 관계 선택 버튼들의 클릭 리스너를 설정합니다.
        setupRelationshipButtonListeners()

        // "다음으로" 버튼 클릭 시, 현재 단계의 입력값을 검증한 후 다음 단계로 진행합니다.
        binding.btnNext.setOnClickListener {
            // 현재 입력 필드 정보를 가져옵니다.
            val currentField = viewModel.getCurrentField()
            // 입력 타입에 따라 실제 입력값을 결정합니다.
            val inputValue = when (currentField.inputType) {
                CustomInputType.TEXT, CustomInputType.MULTILINE_TEXT -> binding.etPrimaryContact.getText()
                CustomInputType.IMAGE_PICKER -> "Image Selected"
                CustomInputType.BUTTON_GROUP -> selectedRelationship ?: ""
                else -> ""
            }

            // ViewModel의 유효성 검사 함수를 통해 입력값을 확인합니다.
            if (viewModel.validateInput(currentField.id, inputValue)) {
                // 입력값이 유효하면 ViewModel에 저장합니다.
                viewModel.saveInput(currentField.id, inputValue)

                // 다음 단계로 이동 시도
                if (viewModel.nextStep()) {
                    // 다음 단계의 필드 정보를 가져옵니다.
                    val nextField = viewModel.getCurrentField()
                    // 이전에 입력된 값이 있다면 가져오고, 없다면 빈 문자열로 설정합니다.
                    val nextInputValue = viewModel.getInput(nextField.id) ?: ""

                    // 다음 단계가 텍스트 입력 타입이면 etPrimaryContact에 값을 설정합니다.
                    if (nextField.inputType == CustomInputType.TEXT || nextField.inputType == CustomInputType.MULTILINE_TEXT) {
                        binding.etPrimaryContact.setText(nextInputValue)
                    } else {
                        // 텍스트 입력 타입이 아니라면 입력 필드를 초기화합니다.
                        binding.etPrimaryContact.clearText()
                    }
                }
            } else {
                // 입력값이 유효하지 않은 경우, 에러 메시지는 CustomEditText의 validator에 의해 표시됩니다.
                // 필요한 경우 추가적인 UI 피드백을 이곳에서 구현할 수 있습니다.
            }
        }

        // "이전으로" 버튼 클릭 시, 이전 단계로 돌아가고 저장된 입력값을 복원합니다.
        binding.btnPrevious.setOnClickListener {
            viewModel.previousStep()
            val previousField = viewModel.getCurrentField()
            val previousInputValue = viewModel.getInput(previousField.id) ?: ""

            if (previousField.inputType == CustomInputType.TEXT || previousField.inputType == CustomInputType.MULTILINE_TEXT) {
                binding.etPrimaryContact.setText(previousInputValue)
            }
        }

        // "사진 등록" 버튼 클릭 시, 사진 등록 로직을 수행합니다.
        binding.btnRegisterPhoto.setOnClickListener {
            registerPhoto()
        }

        // [연락처 추가] 버튼 클릭 리스너
        binding.btnAddContact.setOnClickListener {
            if (additionalContactViews.size >= 4) {
                Toast.makeText(requireContext(), "최대 5개 까지 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 새 CustomEditText 인스턴스 생성 (동적 생성)
            val newContactView = CustomEditText(requireContext()).apply {
                // primary와 같은 스타일 및 힌트 설정
                setHint("전화번호 입력")
                setInputType(InputType.TYPE_CLASS_NUMBER)
                setValidator { input ->
                    if (input.length == 11 && input.all { it.isDigit() }) {
                        null // 유효한 입력: 에러 메시지 없음
                    } else {
                        "잘못된 형식의 전화번호입니다."
                    }
                }
                // 동적으로 추가하는 뷰에는 고유 ID를 부여 (ConstraintSet이나 인덱스 관리에 필요)
                id = View.generateViewId()
            }

            // 10dp를 픽셀로 변환
            val marginPx = (10 * resources.displayMetrics.density).toInt()
            // LinearLayout.LayoutParams 생성 후 topMargin 설정
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = marginPx
            }
            newContactView.layoutParams = layoutParams

            // 현재 btn_add_contact는 ll_additional_contacts의 마지막 자식입니다.
            // 새 뷰를 버튼 위에 추가하려면, 버튼의 인덱스를 구해서 그 위치에 삽입합니다.
            val container = binding.llAdditionalContacts
            val buttonIndex = container.indexOfChild(binding.btnAddContact)
            container.addView(newContactView, buttonIndex)
            additionalContactViews.add(newContactView)
        }


        // ViewModel의 currentStep LiveData를 관찰하여 단계가 변경될 때마다 UI를 업데이트합니다.
        viewModel.currentStep.observe(viewLifecycleOwner) { step ->
            renderStep(step)
        }

    }

    /**
     * 현재 단계(step)에 맞춰 UI를 업데이트합니다.
     *
     * @param step 현재 단계(1부터 시작)
     */
    private fun renderStep(step: Int) {
        val currentField = viewModel.getCurrentField()
        binding.tvInputTitle.text = currentField.info

        // 모든 관련 UI 요소를 우선 숨김
        hideAllViews()

        when (currentField.inputType) {
            CustomInputType.TEXT -> {
                binding.etPrimaryContact.visibility = View.VISIBLE
                binding.etPrimaryContact.setHint(currentField.hint)
                setKeyboardType(currentField.keyboardType)
            }
            CustomInputType.BUTTON_GROUP -> {
                binding.flowRelationshipButtons.visibility = View.VISIBLE
            }
            CustomInputType.IMAGE_PICKER -> {
                binding.ivProfileImage.visibility = View.VISIBLE
                binding.btnRegisterPhoto.visibility = View.VISIBLE
            }
            CustomInputType.MULTILINE_TEXT -> {
                binding.etPrimaryContact.visibility = View.VISIBLE
                setKeyboardType(currentField.keyboardType)
                when (currentField.id) {
                    "ward_emergency_contacts" -> {
                        Timber.d("ward_emergency_contacts")
                        // 비상 연락망 단계: side info 보이기 (HTML 태그 적용)
                        binding.tvSideInfo.visibility = View.VISIBLE
                        binding.tvSideInfo.text = Html.fromHtml(
                            getString(R.string.ward_emergency_side_info),
                            Html.FROM_HTML_MODE_LEGACY
                        )
                        // 추가 연락처 컨테이너 보이기
                        binding.llAdditionalContacts.visibility = View.VISIBLE
                        // 컨테이너 내의 기존 모든 자식(혹은 이전에 추가했던 뷰들 및 add 버튼)을 초기화
                        binding.llAdditionalContacts.removeAllViews()
                        // 이미 추가된 연락처 컴포넌트들을 먼저 추가
                        additionalContactViews.forEach { additionalView ->
                            binding.llAdditionalContacts.addView(additionalView)
                        }
                        // 마지막 자식으로 항상 "연락처 추가" 버튼을 추가
                        binding.llAdditionalContacts.addView(binding.btnAddContact)
                    }
                    "ward_address" -> {
                        Timber.d("ward_address")
                        binding.tvSideInfo.visibility = View.VISIBLE
                        binding.tvSideInfo.text = getString(R.string.ward_address_side_info)
                    }
                    else -> {
                        binding.tvSideInfo.visibility = View.GONE
                        binding.btnAddContact.visibility = View.GONE
                        binding.llAdditionalContacts.visibility = View.GONE
                        updateAddContactButtonPosition()  // 기본 제약 재설정
                    }
                }
            }
        }

        binding.btnPrevious.isEnabled = step > 1
        binding.btnNext.isEnabled = step != viewModel.fields.size
    }

    private fun updateAddContactButtonConstraint(belowEmergencySideInfo: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        val marginPx = if (belowEmergencySideInfo) {
            (24 * resources.displayMetrics.density).toInt()
        } else {
            (10 * resources.displayMetrics.density).toInt()
        }
        if (belowEmergencySideInfo) {
            constraintSet.connect(
                binding.btnAddContact.id,
                ConstraintSet.TOP,
                binding.tvSideInfo.id,
                ConstraintSet.BOTTOM,
                marginPx
            )
        } else {
            constraintSet.connect(
                binding.btnAddContact.id,
                ConstraintSet.TOP,
                binding.etPrimaryContact.id,
                ConstraintSet.BOTTOM,
                marginPx
            )
        }
        constraintSet.applyTo(binding.root)
    }

    /**
     * updateAddContactButtonPosition()
     *
     * 추가 연락처 컨테이너에 자식 뷰가 있다면, btn_add_contact의 상단 제약을 마지막 자식 뷰의 하단으로 연결합니다.
     * 자식 뷰가 없으면 기본적으로 et_primary_contact의 하단에 연결합니다.
     */
    private fun updateAddContactButtonPosition() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        val marginPx = (10 * resources.displayMetrics.density).toInt()
        if (binding.llAdditionalContacts.childCount > 0) {
            // 마지막 추가된 뷰를 가져옴
            val lastChild = binding.llAdditionalContacts.getChildAt(binding.llAdditionalContacts.childCount - 1)
            constraintSet.connect(
                binding.btnAddContact.id,
                ConstraintSet.TOP,
                lastChild.id,
                ConstraintSet.BOTTOM,
                marginPx
            )
        } else {
            constraintSet.connect(
                binding.btnAddContact.id,
                ConstraintSet.TOP,
                binding.etPrimaryContact.id,
                ConstraintSet.BOTTOM,
                marginPx
            )
        }
        constraintSet.applyTo(binding.root)
    }


    /**
     * 현재 화면에 있는 모든 입력 관련 UI 요소를 숨깁니다.
     * (각 단계별로 필요한 UI 요소만 표시하기 위함)
     */
    private fun hideAllViews() {
        binding.etPrimaryContact.visibility = View.GONE
        binding.flowRelationshipButtons.visibility = View.GONE
        binding.ivProfileImage.visibility = View.GONE
        binding.btnRegisterPhoto.visibility = View.GONE
        binding.btnAddContact.visibility = View.GONE
        binding.llAdditionalContacts.visibility = View.GONE
        binding.tvSideInfo.visibility = View.GONE
    }


    /**
     * CustomEditText의 키보드 입력 타입을 설정합니다.
     *
     * @param keyboardType CustomKeyboardType에 따른 키보드 타입
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

    /**
     * 관계 선택 버튼(버튼 그룹)들의 클릭 리스너를 설정합니다.
     */
    private fun setupRelationshipButtonListeners() {
        // 각 버튼과 해당 관계 문자열("부모", "자식", 등)을 페어로 구성합니다.
        val relationshipButtons = listOf(
            binding.btnParent to "부모",
            binding.btnChild to "자식",
            binding.btnSibling to "형제",
            binding.btnCousin to "사촌",
            binding.btnFriend to "친구",
            binding.btnCaregiver to "간병인",
            binding.btnOther to "기타"
        )

        // 각 버튼에 클릭 리스너를 설정하여 선택 시 selectRelationship()을 호출합니다.
        for ((button, relationship) in relationshipButtons) {
            button.setOnClickListener {
                selectRelationship(button, relationship)
            }
        }
    }

    /**
     * 버튼 그룹에서 선택된 관계 버튼을 처리합니다.
     *
     * @param selectedButton 사용자가 선택한 버튼
     * @param relationship   버튼에 해당하는 관계 문자열
     */
    private fun selectRelationship(selectedButton: Button, relationship: String) {
        // 모든 관계 버튼의 선택 상태를 초기화합니다.
        val relationshipButtons = listOf(
            binding.btnParent,
            binding.btnChild,
            binding.btnSibling,
            binding.btnCousin,
            binding.btnFriend,
            binding.btnCaregiver,
            binding.btnOther
        )

        relationshipButtons.forEach { button ->
            button.isSelected = false
        }

        // 선택된 버튼에 선택 효과를 부여하고, 선택된 관계 문자열을 저장합니다.
        selectedButton.isSelected = true
        selectedRelationship = relationship

        // "다음으로" 버튼을 활성화합니다.
        binding.btnNext.isEnabled = true
    }

    /**
     * 사진 등록 버튼 클릭 시 호출되는 메서드입니다.
     * 실제 사진 등록 로직을 구현할 곳입니다.
     */
    private fun registerPhoto() {
        photoPickerLauncher.launch("image/*")
    }
}
