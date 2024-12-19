package com.example.eldercare.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.eldercare.base.viewmodel.BaseViewModel

abstract class BaseFragment<B : ViewBinding, VM: BaseViewModel>(
    private val inflater : (LayoutInflater, ViewGroup?, Boolean) -> B,
) : Fragment() {

    protected inline val TAG: String
        get() = this::class.java.simpleName

    private var _binding : B? = null
    protected val binding
        get() = requireNotNull(_binding)

    protected abstract val viewModel : VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflater(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
