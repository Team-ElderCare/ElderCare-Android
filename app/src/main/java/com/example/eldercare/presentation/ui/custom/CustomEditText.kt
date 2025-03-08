package com.example.eldercare.presentation.ui.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.eldercare.R
import com.example.eldercare.databinding.ViewCustomEditTextBinding

class CustomEditText
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : ConstraintLayout(context, attrs, defStyleAttr) {
        private val binding: ViewCustomEditTextBinding
        private var isClearIcon = false
        private var validator: ((String) -> String?)? = null // 유효성 검사 함수
        private var searchAction: ((String) -> Unit)? = null // 검색 액션 함수
        private var unit: String? = null // 단위 표시 텍스트
        private var dropdownOptions: List<String>? = null // 드롭 다운 메뉴
        private var onOptionSelected: ((String) -> Unit)? = null
        private var isPhoneNumber = false
        private var isFormatted = false

        init {
            binding = ViewCustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)
            binding.clTextField.setBackgroundResource(R.drawable.shape_for_edit_text)
            setupCustomAttributes(context, attrs)
            setupListeners()
            setupTextWatcher()
        }

        private fun setupCustomAttributes(
            context: Context,
            attrs: AttributeSet?,
        ) {
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTextField, 0, 0).apply {
                try {
                    binding.etInputField.hint = getString(R.styleable.CustomTextField_customHint)
                    unit = getString(R.styleable.CustomTextField_customUnit)
                    isPhoneNumber = getBoolean(R.styleable.CustomTextField_isPhoneNumber, false)
                    val customIcon = getResourceId(R.styleable.CustomTextField_customIcon, -1)

                    binding.tvUnit.visibility = if (unit.isNullOrEmpty()) GONE else VISIBLE
                    binding.tvUnit.text = unit

                    setCustomIconVisibility(customIcon)
                } finally {
                    recycle()
                }
            }
        }

        private fun setCustomIconVisibility(customIcon: Int) {
            binding.ivClearButton.visibility = GONE
            binding.ivSearchButton.visibility = GONE
            binding.ivDropdownButton.visibility = GONE

            when (customIcon) {
                R.drawable.ic_clear_21x20dp -> {
                    isClearIcon = true
                }

                R.drawable.ic_search_24x25dp -> {
                    binding.ivSearchButton.visibility = VISIBLE
                    isClearIcon = false
                }

                R.drawable.ic_dropdown_17x15dp -> {
                    binding.ivDropdownButton.visibility = VISIBLE
                    binding.ivDropdownButton.setOnClickListener { showDropdownMenu() }
                    isClearIcon = false
                }
            }
        }

        private fun setupListeners() {
            binding.ivSearchButton.setOnClickListener {
                searchAction?.invoke(binding.etInputField.text.toString())
            }

            binding.ivClearButton.setOnClickListener {
                binding.etInputField.text.clear()
                clearError()
            }
            // EditText 포커스 변화에 따라 상태 업데이트
            binding.etInputField.setOnFocusChangeListener { _, hasFocus ->
                binding.clTextField.isActivated = hasFocus
                updateUnitAndClearButtonVisibility(hasFocus)

                if (isPhoneNumber) {
                    checkPhoneFormat(hasFocus)
                }
            }
            // 부모 레이아웃 클릭 시 EditText에 포커스 부여 및 활성화 상태 변경
            binding.clTextField.setOnClickListener {
                binding.etInputField.requestFocus()
                binding.etInputField.showKeyboard()
                binding.clTextField.isActivated = true
            }
        }

    private fun checkPhoneFormat(hasFocus: Boolean) {
        val input = getText()

        if (!hasFocus && input.all { it.isDigit() } && input.length == 11) {
            val formatted = formatPhoneNumber(input)
            if (formatted != input) {
                binding.etInputField.setText(formatted)
                binding.etInputField.setSelection(formatted.length)
                isFormatted = true
            }
        } else if (hasFocus && isFormatted) {
            val unformatted = input.replace("-", "")
            binding.etInputField.setText(unformatted)
            binding.etInputField.setSelection(unformatted.length)
            isFormatted = false
        }
    }


    private fun formatPhoneNumber(number: String): String =
            if (number.length == 11) {
                "${number.substring(0, 3)}-${number.substring(3, 7)}-${number.substring(7)}"
            } else {
                number
            }

        private fun setupTextWatcher() {
            binding.etInputField.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        updateUnitAndClearButtonVisibility(binding.etInputField.hasFocus())
                        validateInput()
                    }
                },
            )
        }

        private fun updateUnitAndClearButtonVisibility(hasFocus: Boolean) {
            val isEmpty = binding.etInputField.text.isNullOrEmpty()
            binding.tvUnit.visibility = if (isEmpty && unit.isNullOrEmpty()) GONE else VISIBLE
            binding.ivClearButton.visibility = if (isClearIcon && !isEmpty && hasFocus) VISIBLE else GONE
        }

        private fun validateInput() {
            val input = getText()
            val errorMessage = validator?.invoke(input)
            if (errorMessage != null) setError(errorMessage) else clearError()
        }

        private fun showDropdownMenu() {
            dropdownOptions?.let { options ->
                val popupMenu = PopupMenu(context, binding.clTextField)
                options.forEachIndexed { index, option -> popupMenu.menu.add(0, index, index, option) }

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    val selectedOption = options[menuItem.itemId]
                    binding.etInputField.setText(selectedOption)
                    onOptionSelected?.invoke(selectedOption)
                    true
                }

                popupMenu.show()
            }
        }

        fun getText(): String = binding.etInputField.text.toString()

        fun setError(message: String) {
            binding.clTextField.setBackgroundResource(R.drawable.shape_for_edit_text_error)
            binding.tvErrorMessage.apply {
                text = message
                visibility = VISIBLE
            }
        }

        fun clearError() {
            binding.clTextField.setBackgroundResource(R.drawable.shape_for_edit_text)
            binding.tvErrorMessage.visibility = GONE
        }

        fun setOnSearchClickListener(action: (String) -> Unit) {
            this.searchAction = action
        }

        fun setValidator(validator: (String) -> String?) {
            this.validator = validator
        }

        fun setText(text: String) {
            binding.etInputField.setText(text)
            binding.etInputField.setSelection(text.length)
            clearError()
        }

        fun clearText() {
            binding.etInputField.text.clear()
            clearError()
        }

        fun setHint(hint: String) {
            binding.etInputField.hint = hint
        }

        fun setInputType(inputType: Int) {
            binding.etInputField.inputType = inputType
        }

        fun setUnit(unit: String?) {
            this.unit = unit
            binding.tvUnit.text = unit
            binding.tvUnit.visibility = if (unit.isNullOrEmpty()) GONE else VISIBLE
        }

        fun setDropdownOptions(
            options: List<String>,
            onOptionSelected: (String) -> Unit,
        ) {
            dropdownOptions = options
            this.onOptionSelected = onOptionSelected
        }

        fun getEditText(): EditText = binding.etInputField

        private fun View.showKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }

        fun requestFocusAndShowKeyboard() {
            binding.etInputField.requestFocus()
            binding.etInputField.showKeyboard()
        }
    }
