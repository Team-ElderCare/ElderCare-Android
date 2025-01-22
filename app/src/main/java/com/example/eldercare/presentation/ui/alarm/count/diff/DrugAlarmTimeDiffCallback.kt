package com.example.eldercare.presentation.ui.alarm.count.diff

import com.example.eldercare.base.diffutil.BaseDiffCallback
import com.example.eldercare.presentation.ui.alarm.count.adapter.DrugAlarmTime
import timber.log.Timber

// todo : 추후 데이터에 따라 수정
class DrugAlarmTimeDiffCallback :
    BaseDiffCallback<DrugAlarmTime>(
        itemTheSame = { old, new -> old.id == new.id },
        contentsTheSame = { old, new ->
            Timber.d("시간비교 :${old.time}, ${new.time}")
            old.time == new.time
        },
    )
