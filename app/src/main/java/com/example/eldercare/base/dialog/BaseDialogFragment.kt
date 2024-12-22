package com.example.eldercare.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

// 추후 파일 위치 변경
abstract class BaseDialogFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) :
    DialogFragment() {
    private var _binding: T? = null
    val binding
        get() = _binding ?: throw IllegalStateException("${this::class.java.simpleName}에서 에러가 발생했습니다.")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
