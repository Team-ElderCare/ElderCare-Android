package com.example.eldercare.presentation.ui.basicInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eldercare.base.viewmodel.BaseViewModel

class BasicInfoViewModel
    @javax.inject.Inject
    constructor() : BaseViewModel() {
        // 연락처를 관리하는 리스트
        val contacts: MutableList<String> = mutableListOf()

        // 현재 단계
        private val _currentStep = MutableLiveData(BasicInfoStep.GUARDIAN_NAME)
        val currentStep: LiveData<BasicInfoStep> get() = _currentStep

        // 입력 데이터 저장
        private val inputData = mutableMapOf<String, String>()

        // 입력값 저장
        fun saveInput(
            step: BasicInfoStep,
            value: String,
        ) {
            inputData[step.name] = value
        }

        // 입력값 가져오기 (Step을 받아서 처리)
        fun getInput(step: BasicInfoStep): String? = inputData[step.name]
    }
