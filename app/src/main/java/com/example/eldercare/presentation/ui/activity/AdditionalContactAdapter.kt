package com.example.eldercare.presentation.ui.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.eldercare.base.adapter.BaseAdapter
import com.example.eldercare.databinding.ItemAdditionalContactBinding

class AdditionalContactAdapter(
    private val onContactChanged: (phone: String, position: Int) -> Unit,
    private val getPrimaryContact: () -> String,
    private val getAllAdditionalContacts: () -> List<String>
) : BaseAdapter<AdditionalContactItem, ItemAdditionalContactBinding, AdditionalContactViewHolder>(
    AdditionalContactDiffCallback()
) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        return if (item is AdditionalContactItem.Contact) item.id else 0L
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): ItemAdditionalContactBinding {
        return ItemAdditionalContactBinding.inflate(inflater, parent, attachToParent)
    }

    override fun createViewHolder(binding: ItemAdditionalContactBinding): AdditionalContactViewHolder {
        return AdditionalContactViewHolder(binding, onContactChanged, getPrimaryContact, getAllAdditionalContacts)
    }
}
