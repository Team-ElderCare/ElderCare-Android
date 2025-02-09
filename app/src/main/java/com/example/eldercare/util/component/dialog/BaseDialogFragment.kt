package com.example.eldercare.util.component.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) : DialogFragment() {
    private var _binding: T? = null
    val binding get() = _binding ?: throw IllegalStateException("${this::class.java.simpleName}에서 에러가 발생했습니다.")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            val screenHeight = resources.displayMetrics.heightPixels
            val dialogY = (screenHeight * 0.35).toInt()

            setLayout(
                (resources.displayMetrics.widthPixels * 0.7).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.TOP)

            val params = attributes
            params.y = dialogY
            attributes = params
        }
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
