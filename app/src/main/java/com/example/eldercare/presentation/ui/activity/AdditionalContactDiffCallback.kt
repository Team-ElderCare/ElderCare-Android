package com.example.eldercare.presentation.ui.activity

import com.example.eldercare.base.diffutil.BaseDiffCallback


class AdditionalContactDiffCallback : BaseDiffCallback<AdditionalContactItem>(
    itemTheSame = { oldItem, newItem ->
        when {
            oldItem is AdditionalContactItem.Contact && newItem is AdditionalContactItem.Contact ->
                oldItem.id == newItem.id
            else -> false
        }
    },
    contentsTheSame = { oldItem, newItem -> oldItem == newItem }
)

