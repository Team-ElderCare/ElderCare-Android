package com.example.eldercare.util.component.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.eldercare.R
import com.example.eldercare.databinding.DialogNoButtonBinding

class NoButtonDialogFragment(
    private val icon: Int?,
    private val title: String,
    private val onDialogClosed: () -> Unit = {}
) : BaseDialogFragment<DialogNoButtonBinding>(R.layout.dialog_no_button) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDialogClosed()
    }

    private fun initLayout() {
        with(binding) {
            if(icon == null) {
                    ivNoBtnDialogIcon.visibility = View.GONE
            } else {
                ivNoBtnDialogIcon.setImageResource(icon)
            }
            tvNoBtnDialogTitle.text = title
        }
    }

}

