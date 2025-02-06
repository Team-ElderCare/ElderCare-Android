package com.example.eldercare.util.component.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.eldercare.R
import com.example.eldercare.databinding.DialogOneButtonBinding

class OneButtonDialogFragment(
    private val icon: Int?,
    private val title: String,
    private val detail: String?,
    private val buttonText: String,
    private val clickBtn: () -> Unit,
    private val onDialogClosed: () -> Unit = {}
) : BaseDialogFragment<DialogOneButtonBinding>(R.layout.dialog_one_button) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDialogClosed()
    }

    private fun initLayout() {
        with(binding) {
            if(icon == null) {
                ivOneBtnDialogIcon.visibility = View.GONE
            } else {
                ivOneBtnDialogIcon.setImageResource(icon)
            }
            if(detail.isNullOrEmpty()) {
                tvOneBtnDialogDetail.visibility = View.GONE
            } else {
                tvOneBtnDialogDetail.text = detail
            }
            tvOneBtnDialogTitle.text = title
            btnOneBtnDialog.text = buttonText
        }
    }

    private fun addListeners() {
        binding.btnOneBtnDialog.setOnClickListener {
            clickBtn()
            dismiss()
        }
    }

}
