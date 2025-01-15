package com.example.eldercare.presentation.ui.custoom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.eldercare.R

class CustomButton
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int = 0,
    ) : ConstraintLayout(context, attrs, defStyleAttr) {
        private lateinit var titleTextView: TextView
        private lateinit var iconImageView: ImageView

        init {
            initializeView()
            getAttrs(attrs, defStyleAttr)
        }

        private fun initializeView() {
            val view =
                LayoutInflater
                    .from(context)
                    .inflate(R.layout.view_custom_button, this, true)

            titleTextView = view.findViewById(R.id.custom_btn_text)
            iconImageView = view.findViewById(R.id.custom_btn_image)
        }

        private fun getAttrs(
            attrs: AttributeSet?,
            defStyleAttr: Int,
        ) {
            val typedArray =
                context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomButton,
                    defStyleAttr,
                    0,
                )
            try {
                typedArray.getString(R.styleable.CustomButton_customButtonTextView)?.let {
                    titleTextView.text = it
                }

                val iconResId =
                    typedArray.getResourceId(
                        R.styleable.CustomButton_customButtonIcon,
                        -1,
                    )

                if (iconResId != -1) {
                    iconImageView.setImageResource(iconResId)
                }
            } finally {
                typedArray.recycle()
            }
        }
    }
