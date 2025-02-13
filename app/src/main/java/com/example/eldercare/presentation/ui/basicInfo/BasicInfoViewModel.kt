package com.example.eldercare.presentation.ui.basicInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eldercare.base.viewmodel.BaseViewModel
import com.example.eldercare.domain.model.basicInfo.Address
import com.example.eldercare.domain.model.basicInfo.Relationship
import com.example.eldercare.domain.repository.BasicInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class BasicInfoViewModel @Inject
constructor(
    private val basicInfoRepository: BasicInfoRepository
) : BaseViewModel() {
    // 연락처를 관리하는 리스트
    val contacts: MutableList<String> = mutableListOf()

    // 현재 단계
    private val _currentStep = MutableLiveData(BasicInfoStep.GUARDIAN_NAME)
    val currentStep: LiveData<BasicInfoStep> get() = _currentStep

    // 입력 데이터 저장
    private val inputData = mutableMapOf<String, String>()

    // 입력 데이터 저장 (Relationship을 저장하도록 변경)
    private val relationshipData = MutableLiveData<Relationship?>()

    // 입력값 저장
    fun saveInput(
        step: BasicInfoStep,
        value: String,
    ) {
        inputData[step.name] = value
    }

    // 입력값 가져오기 (Step을 받아서 처리)
    fun getInput(step: BasicInfoStep): String? = inputData[step.name]

    fun saveRelationship(relationship: Relationship) {
        relationshipData.value = relationship
    }

    // 저장된 Relationship 가져오기
    fun getSavedRelationship(): Relationship? = relationshipData.value

    fun postUsersRegistration(
        username: String,
        phoneNumber: String,
        relationship: Relationship,
        userImageUrl: MultipartBody.Part
    ) {
        //TODO: 처리 로직

        viewModelScope.launch {
            basicInfoRepository.postUserRegistration(
                username,
                phoneNumber,
                relationship,
                userImageUrl
            ).onSuccess {

            }
        }
    }

    fun postProtectedRegistration(
        protectedName: String,
        protectedBirthDate: String,
        protectedPhoneNumber: String,
        address: Address
    ) {
        //TODO: 처리 로직

        viewModelScope.launch {
            basicInfoRepository.postProtectedRegistration(
                protectedName,
                protectedBirthDate,
                protectedPhoneNumber,
                address
            ).onSuccess {

            }
        }
    }
}
