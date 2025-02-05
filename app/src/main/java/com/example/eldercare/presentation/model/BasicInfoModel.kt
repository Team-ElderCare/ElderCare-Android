package com.example.eldercare.presentation.model

data class InputField(
    // 필드 식별자 (예: "guardian_name", "guardian_phone")
    val step: Step,
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
    // 일반 텍스트 입력
    TEXT,

    // 버튼 그룹 선택
    BUTTON_GROUP,

    // 사진 업로드
    IMAGE_PICKER,

    // 멀티라인 텍스트
    MULTILINE_TEXT,
}

enum class CustomKeyboardType {
    // 일반 키보드
    DEFAULT,

    // 숫자 키보드
    NUMERIC,

    // 전화번호 키보드
    PHONE,
}

enum class Step {
    GUARDIAN_NAME,
    GUARDIAN_PHONE,
    GUARDIAN_RELATIONSHIP,
    GUARDIAN_PHOTO,
    WARD_NAME,
    WARD_BIRTH_DATE,
    WARD_NICKNAME,
    WARD_PHONE,
    WARD_ADDRESS,
    WARD_EMERGENCY_CONTACTS,
}
