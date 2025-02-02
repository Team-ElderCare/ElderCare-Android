package com.example.eldercare.presentation.ui.activity

sealed class AdditionalContactItem {
    data class Contact(val id: Long, val phone: String) : AdditionalContactItem()
}

