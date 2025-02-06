package com.example.eldercare.util.component.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.eldercare.R
import com.example.eldercare.databinding.DialogTwoButtonBinding

class TwoButtonDialogFragment(
    private val icon: Int?,
    private val title: String,
    private val detail: String,
    private val leftButtonText: String,
    private val rightButtonText: String,
    private val leftButtonClick: () -> Unit,
    private val rightButtonClick: () -> Unit,
    private val onDialogClosed: () -> Unit = {}
) : BaseDialogFragment<DialogTwoButtonBinding>(R.layout.dialog_two_button) {

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
                ivTwoBtnDialogIcon.visibility = View.GONE
            } else {
                ivTwoBtnDialogIcon.setImageResource(icon)
            }
            tvTwoBtnDialogTitle.text = title
            tvTwoBtnDialogDetail.text = detail
            btnTwoBtnDialogLeft.text = leftButtonText
            btnTwoBtnDialogRight.text = rightButtonText
        }
    }

    private fun addListeners() {
        with(binding) {
            btnTwoBtnDialogLeft.setOnClickListener {
                leftButtonClick()
                dismiss()
            }

            btnTwoBtnDialogRight.setOnClickListener {
                rightButtonClick()
                dismiss()
            }
        }
    }

}
