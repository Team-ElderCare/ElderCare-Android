package com.example.eldercare.presentation.ui.custom

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.eldercare.R
import com.example.eldercare.databinding.ViewCustomEditTextBinding

class CustomEditText
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : ConstraintLayout(context, attrs, defStyleAttr) {
        private val binding = ViewCustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)
        private var isClearIcon = false
        private var validator: ((String) -> String?)? = null
        private var searchAction: ((String) -> Unit)? = null
        private var unit: String? = null
        private var dropdownOptions: List<String>? = null
        private var onOptionSelected: ((String) -> Unit)? = null
        private var isPhoneNumber = false
        private var isFormatted = false

        companion object {
            private const val PHONE_NUMBER_LENGTH = 11
        }

        init {
            initializeView()
            setupCustomAttributes(context, attrs)
            setupListeners()
            setupTextWatcher()
        }

        private fun initializeView() {
            binding.clTextField.setBackgroundResource(R.drawable.shape_for_edit_text)
        }

        private fun setupCustomAttributes(
            context: Context,
            attrs: AttributeSet?,
        ) {
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTextField, 0, 0).apply {
                try {
                    applyCustomAttributes(this)
                } finally {
                    recycle()
                }
            }
        }

        private fun applyCustomAttributes(typedArray: TypedArray) {
            binding.etInputField.hint = typedArray.getString(R.styleable.CustomTextField_customHint)
            unit = typedArray.getString(R.styleable.CustomTextField_customUnit)
            isPhoneNumber = typedArray.getBoolean(R.styleable.CustomTextField_isPhoneNumber, false)
            val customIcon = typedArray.getResourceId(R.styleable.CustomTextField_customIcon, -1)

            updateUnitVisibility()
            setCustomIconVisibility(customIcon)
        }

        private fun updateUnitVisibility() {
            binding.tvUnit.apply {
                visibility = if (unit.isNullOrEmpty()) GONE else VISIBLE
                text = unit
            }
        }

        private fun setCustomIconVisibility(customIcon: Int) {
            hideAllIcons()
            when (customIcon) {
                R.drawable.ic_clear_21x20dp -> isClearIcon = true
                R.drawable.ic_search_24x25dp -> binding.ivSearchButton.visibility = VISIBLE
                R.drawable.ic_dropdown_17x15dp -> {
                    binding.ivDropdownButton.apply {
                        visibility = VISIBLE
                        setOnClickListener { showDropdownMenu() }
                    }
                }
                else -> {
                    isClearIcon = true
                }
            }
            updateUnitAndClearButtonVisibility(binding.etInputField.hasFocus())
        }

        private fun hideAllIcons() {
            binding.ivClearButton.visibility = GONE
            binding.ivSearchButton.visibility = GONE
            binding.ivDropdownButton.visibility = GONE
            isClearIcon = false
        }

        private fun setupListeners() {
            with(binding) {
                ivSearchButton.setOnClickListener { searchAction?.invoke(etInputField.text.toString()) }
                ivClearButton.setOnClickListener { clearText() }
                etInputField.setOnFocusChangeListener { _, hasFocus -> handleFocusChange(hasFocus) }
                clTextField.setOnClickListener { requestFocusAndShowKeyboard() }
            }
        }

        private fun handleFocusChange(hasFocus: Boolean) {
            binding.clTextField.isActivated = hasFocus
            updateUnitAndClearButtonVisibility(hasFocus)
            if (isPhoneNumber) checkPhoneFormat(hasFocus)
        }

        private fun checkPhoneFormat(hasFocus: Boolean) {
            val input = getText()
            if (!hasFocus && input.all { it.isDigit() } && input.length == PHONE_NUMBER_LENGTH) {
                formatPhoneNumber(input)
            } else if (hasFocus && isFormatted) {
                unformatPhoneNumber(input)
            }
        }

        private fun formatPhoneNumber(input: String) {
            val formatted = "${input.substring(0, 3)}-${input.substring(3, 7)}-${input.substring(7)}"
            if (formatted != input) {
                binding.etInputField.apply {
                    setText(formatted)
                    setSelection(formatted.length)
                }
                isFormatted = true
            }
        }

        private fun unformatPhoneNumber(input: String) {
            val unformatted = input.replace("-", "")
            binding.etInputField.apply {
                setText(unformatted)
                setSelection(unformatted.length)
            }
            isFormatted = false
        }

        private fun setupTextWatcher() {
            binding.etInputField.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {}

                    override fun afterTextChanged(s: Editable?) {
                        updateUnitAndClearButtonVisibility(binding.etInputField.hasFocus())
                        validateInput()
                    }
                },
            )
        }

        private fun updateUnitAndClearButtonVisibility(hasFocus: Boolean) {
            val isEmpty = binding.etInputField.text.isNullOrEmpty()
            val unitVisible = !unit.isNullOrEmpty()
            val clearButtonVisible = isClearIcon && !isEmpty && hasFocus
            val searchButtonVisible = binding.ivSearchButton.visibility == VISIBLE
            val dropdownButtonVisible = binding.ivDropdownButton.visibility == VISIBLE

            updateVisibility(unitVisible, clearButtonVisible)
            updateConstraints(clearButtonVisible, searchButtonVisible, dropdownButtonVisible, unitVisible)
        }

        private fun updateVisibility(
            unitVisible: Boolean,
            clearButtonVisible: Boolean,
        ) {
            binding.tvUnit.visibility = if (unitVisible) VISIBLE else GONE
            binding.ivClearButton.visibility = if (clearButtonVisible) VISIBLE else GONE
        }

        private fun updateConstraints(
            clearButtonVisible: Boolean,
            searchButtonVisible: Boolean,
            dropdownButtonVisible: Boolean,
            unitVisible: Boolean,
        ) {
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.clTextField)

            when {
                clearButtonVisible -> setClearButtonConstraints(constraintSet)
                searchButtonVisible || dropdownButtonVisible -> setIconButtonConstraints(constraintSet)
                unitVisible -> setUnitConstraints(constraintSet)
                else -> setDefaultConstraints(constraintSet)
            }

            constraintSet.applyTo(binding.clTextField)
        }

        private fun setClearButtonConstraints(constraintSet: ConstraintSet) {
            constraintSet.connect(R.id.et_input_field, ConstraintSet.END, R.id.space_unit, ConstraintSet.START)
            constraintSet.connect(R.id.space_unit, ConstraintSet.END, R.id.tv_unit, ConstraintSet.START)
            constraintSet.connect(R.id.tv_unit, ConstraintSet.END, R.id.iv_clear_button, ConstraintSet.START)
            constraintSet.connect(R.id.iv_clear_button, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        }

        private fun setIconButtonConstraints(constraintSet: ConstraintSet) {
            constraintSet.connect(R.id.et_input_field, ConstraintSet.END, R.id.right_icon_guideline, ConstraintSet.START)
        }

        private fun setUnitConstraints(constraintSet: ConstraintSet) {
            constraintSet.connect(R.id.et_input_field, ConstraintSet.END, R.id.space_unit, ConstraintSet.START)
            constraintSet.connect(R.id.space_unit, ConstraintSet.END, R.id.tv_unit, ConstraintSet.START)
            constraintSet.connect(R.id.tv_unit, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        }

        private fun setDefaultConstraints(constraintSet: ConstraintSet) {
            constraintSet.connect(R.id.et_input_field, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
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
