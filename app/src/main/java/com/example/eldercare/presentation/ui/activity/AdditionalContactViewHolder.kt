package com.example.eldercare.presentation.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.eldercare.base.adapter.BaseViewHolder
import com.example.eldercare.databinding.ItemAdditionalContactBinding

class AdditionalContactViewHolder(
    private val binding: ItemAdditionalContactBinding,
    private val onContactChanged: (phone: String, position: Int) -> Unit,
    private val getPrimaryContact: () -> String,
    private val getAllAdditionalContacts: () -> List<String>
) : BaseViewHolder<AdditionalContactItem>(binding.root) {

    // 중복 등록 방지를 위한 TextWatcher 참조
    private var textWatcher: TextWatcher? = null

    // AdditionalContactViewHolder 내부 (예제)
    override fun bind(item: AdditionalContactItem) {
        // 기존 TextWatcher 제거
        textWatcher?.let { binding.customEditText.getEditText().removeTextChangedListener(it) }

        if (item is AdditionalContactItem.Contact) {
            binding.customEditText.visibility = View.VISIBLE
            // 만약 현재 텍스트와 아이템 값이 다르면 업데이트
            if (binding.customEditText.getText() != item.phone) {
                binding.customEditText.setText(item.phone)
            }
            // TextWatcher 등록 (실시간 업데이트는 하지 않고 포커스 해제 시 최종 값을 반영)
            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                override fun afterTextChanged(s: Editable?) {
                    // 여기서는 submitList()를 바로 호출하지 않고,
                    // 포커스 해제 시 onFocusChangeListener에서 호출하도록 할 수 있음.
                }
            }
            binding.customEditText.getEditText().addTextChangedListener(textWatcher)

            // 포커스 변경 리스너를 등록하여 포커스가 해제되었을 때 최종 값을 업데이트
            binding.customEditText.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        onContactChanged(binding.customEditText.getText(), pos)
                    }
                }
            }
        }
    }

}
