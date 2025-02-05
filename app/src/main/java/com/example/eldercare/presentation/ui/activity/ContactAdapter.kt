package com.example.eldercare.presentation.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eldercare.databinding.ItemContactBinding

class ContactAdapter(private val contacts: MutableList<String>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.contactEditText.setText(contacts[position])

            binding.contactEditText.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                        // 필요 시 구현
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                        // 필요 시 구현
                    }

                    override fun afterTextChanged(s: Editable?) {
                        // 연락처 입력값을 업데이트
                        contacts[position] = s.toString()
                    }
                },
            )

            binding.deleteButton.setOnClickListener {
                contacts.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, contacts.size)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int,
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = contacts.size
}
