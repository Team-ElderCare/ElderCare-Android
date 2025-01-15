package com.example.eldercare.presentation.ui.home.diff

import com.example.eldercare.base.diffutil.BaseDiffCallback
import com.example.eldercare.domain.model.recent.UserRecentActivityData

class UserRecentActivityDiffCallback :
    BaseDiffCallback<UserRecentActivityData>(
        itemTheSame = { oldItem, newItem ->
            oldItem.id == newItem.id
        },
        contentsTheSame = { oldItem, newItem ->
            oldItem.time == newItem.time &&
                oldItem.title == newItem.title
        },
    )
