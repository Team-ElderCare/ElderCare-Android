package com.example.eldercare.domain.model.basicInfo

enum class Relationship(val displayName: String) {
    PARENT("부모"),
    CHILDREN("자식"),
    SIBLING("형제"),
    COUSIN("사촌"),
    FRIEND("친구"),
    CAREGIVER("간병인"),
    ETC("기타");

    companion object {
        fun fromDisplayName(displayName: String): Relationship? {
            return entries.find { it.displayName == displayName }
        }
    }
}


