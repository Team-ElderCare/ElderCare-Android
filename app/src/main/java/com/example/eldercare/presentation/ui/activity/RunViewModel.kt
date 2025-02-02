package com.example.eldercare.presentation.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eldercare.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunViewModel
    @Inject
    constructor() : BaseViewModel() {
        // 연락처를 관리하는 리스트
        val contacts: MutableList<String> = mutableListOf()

        // 현재 단계
        private val _currentStep = MutableLiveData(1)
        val currentStep: LiveData<Int> get() = _currentStep

        // 입력 데이터 저장
        private val inputData = mutableMapOf<String, String>()

        // 단계별 입력 필드 정의 (순서대로 입력)
        val fields =
            listOf(
                InputField("guardian_name", "ex)홍길동", "이름을 입력해주세요.", "특수문자나, 초성, 숫자는 사용할 수 없습니다.",CustomInputType.TEXT),
                InputField("guardian_phone", "01012345678", "보호자의 전화번호", "잘못된 형식의 전화번호입니다.", CustomInputType.TEXT, CustomKeyboardType.PHONE),
                InputField(" ", "", "보호 대상자와의 관계", inputType = CustomInputType.BUTTON_GROUP),
                InputField("guardian_photo", "", "보호자의 사진 (선택 사항)", inputType = CustomInputType.IMAGE_PICKER),
                InputField("ward_name", "ex)홍길동", "보호 대상자의 이름", "특수문자나, 초성, 숫자는 사용할 수 없습니다.", CustomInputType.TEXT),
                InputField("ward_birth_date", "19971220", "보호 대상자의 생년월일", "잘못된 형식의 생년월일 정보입니다.", CustomInputType.TEXT, CustomKeyboardType.NUMERIC),
                InputField("ward_nickname", "닉네임을 입력해주세요.", "보호 대 상자의 닉네임", "특수문자나, 초성, 숫자는 사용할 수 없습니다.", CustomInputType.TEXT),
                InputField("ward_phone", "ex)01012345678", "보호 대상자의 전화번호", "잘못된 형식의 전화번호입니다.",  CustomInputType.TEXT, CustomKeyboardType.PHONE),
                InputField("ward_address", "주소를 입력해주세요.", "보호 대상자의 주소", inputType = CustomInputType.TEXT),
                InputField(
                    "ward_emergency_contacts",
                    "비상연락처를 입력해주세요.",
                    "보호 대상자의 부재 시 연결할 비상연락처를 입력해주세요.",
                    "잘못된 형식의 전화번호입니다.",
                    CustomInputType.MULTILINE_TEXT,
                    CustomKeyboardType.NUMERIC,
                ),
            )

        // 현재 단계의 필드 가져오기
        fun getCurrentField(): InputField = fields[_currentStep.value!! - 1]

        // 입력값 저장
        fun saveInput(
            id: String,
            value: String,
        ) {
            inputData[id] = value
        }

        // 입력값 가져오기
        fun getInput(id: String): String? = inputData[id]

        // 유효성 에러 메세지 가져오기
        fun getErrorMessage(id: String): String? = fields.find { it.id == id }?.errorMessage

        // 다음 단계로 이동
        fun nextStep(): Boolean {
            val currentField = getCurrentField()
            val inputValue = inputData[currentField.id] ?: ""

            // 유효성 검사
            if (!validateInput(currentField.id, inputValue)) {
                return false
            }

            // 유효하면 단계 이동
            if (_currentStep.value!! < fields.size) {
                _currentStep.value = _currentStep.value!! + 1
            }
            return true
        }

        // 이전 단계로 이동
        fun previousStep() {
            if (_currentStep.value!! > 1) {
                _currentStep.value = _currentStep.value!! - 1
            }
        }

        // 유효성 검사 로직
        fun validateInput(
            id: String,
            value: String,
        ): Boolean {
            return when (id) {
                "guardian_name", "ward_name" -> {
                    // 한글만 허용, 자음/모음/공백 불가
                    value.isNotBlank() && Regex("^[가-힣]+\$").matches(value)
                }
                "guardian_phone", "ward_phone", "ward_emergency_contacts" -> {
                    // 숫자만 허용, 정확히 11자리
                    value.length == 11 &&
                        value.all {
                            it.isDigit()
                        }
                }
                "ward_birth_date" -> {
                    // 숫자만 허용, 정확히 8자리
                    value.length == 8 &&
                        value.all {
                            it.isDigit()
                        }
                }
                "ward_nickname" -> {
                    // 공백만이거나 비어있으면 안 됨
                    value.isNotBlank()
                }
                else -> true // 기타 필드는 유효성 검사 없음
            }
        }
    }

data class InputField(
    // 필드 식별자 (예: "guardian_name", "guardian_phone")
    val id: String,
    // 에딧텍스트 안에 표시될 힌트
    val hint: String,
    // 입력 필드에 대한 추가 설명
    val info: String,
    // 유효성 실패시 표시될 에러 메시지
    val errorMessage: String? = null,
    // 입력 방식 (텍스트, 버튼 그룹, 사진 등)
    val inputType: CustomInputType,
    // 키보드 타입 (기본값: 일반 키보드)
    val keyboardType: CustomKeyboardType = CustomKeyboardType.DEFAULT,
    // 필수 입력 여부
    val isRequired: Boolean = true,
)

enum class CustomInputType {
    TEXT, // 일반 텍스트 입력
    BUTTON_GROUP, // 버튼 그룹 선택
    IMAGE_PICKER, // 사진 업로드
    MULTILINE_TEXT, // 멀티라인 텍스트
}

enum class CustomKeyboardType {
    DEFAULT, // 일반 키보드
    NUMERIC, // 숫자 키보드
    PHONE, // 전화번호 키보드
}
