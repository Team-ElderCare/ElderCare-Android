package com.example.eldercare.presentation.ui.home.diff

import com.example.eldercare.base.diffutil.BaseDiffCallback
import com.example.eldercare.domain.model.alarm.DrugAlarmData

// 먼저 DiffCallback 클래스를 만듭니다
class DrugAlarmDiffCallback :
    BaseDiffCallback<DrugAlarmData>(
        itemTheSame = { old, new -> old.alarmId == new.alarmId },
        contentsTheSame = { old, new ->
            old.alarmId == new.alarmId
        },
    )
