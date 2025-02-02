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

    override fun bind(item: AdditionalContactItem) {
        // 기존에 등록된 TextWatcher 제거
        textWatcher?.let { binding.customEditText.getEditText().removeTextChangedListener(it) }

        if (item is AdditionalContactItem.Contact) {
            // CustomEditText 보이기
            binding.customEditText.visibility = View.VISIBLE
            // 현재 연락처 값과 CustomEditText에 설정된 텍스트가 다를 때만 업데이트
            if (binding.customEditText.getText() != item.phone) {
                binding.customEditText.setText(item.phone)
            }
            // 유효성 검사 설정 (이 부분은 필요에 따라 한 번만 설정하도록 할 수도 있음)
            binding.customEditText.setValidator { input ->
                // 전화번호는 11자리 숫자여야 함
                if (input.length != 11 || !input.all { char -> char.isDigit() }) {
                    "전화번호는 11자리의 숫자여야 합니다."
                } else {
                    // 중복 검사: 기본 연락처와 비교
                    if (input == getPrimaryContact()) {
                        "기본 연락처와 중복됩니다."
                    } else {
                        // 추가 연락처 목록에서 현재 번호 중복 검사 (자기 자신 포함 시 1개 이상이면 중복)
                        val duplicates = getAllAdditionalContacts().filter { contact: String -> contact == input }
                        if (duplicates.size > 1) "중복된 연락처입니다." else null
                    }
                }
            }
            // TextWatcher 등록하여 텍스트 변경 시 onContactChanged 콜백 호출
            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        onContactChanged(s?.toString() ?: "", pos)
                    }
                }
            }
            binding.customEditText.getEditText().addTextChangedListener(textWatcher)
        }
    }
}
