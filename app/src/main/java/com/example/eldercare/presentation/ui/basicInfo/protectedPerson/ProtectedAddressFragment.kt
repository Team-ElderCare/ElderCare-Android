package com.example.eldercare.presentation.ui.basicInfo.protectedPerson

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInfoProtectedPersonAddressBinding
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoStep
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoViewModel
import com.example.eldercare.presentation.ui.basicInfo.KakaoAddressWebViewDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProtectedAddressFragment : BaseFragment<FragmentInfoProtectedPersonAddressBinding, BasicInfoViewModel>(
    FragmentInfoProtectedPersonAddressBinding::inflate,
) {
    override val viewModel: BasicInfoViewModel by activityViewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        getEditTextInput()
        setupOnClickListener()
        observeAddressResult()
    }

    private fun getEditTextInput() {
        val savedAddress = viewModel.getInput(BasicInfoStep.PROTECTED_ADDRESS)
        if (!savedAddress.isNullOrEmpty()) {
            binding.tvPrimary.text = savedAddress
            binding.ivSearch.visibility = View.GONE
            updateButtonState(true)
        } else {
            binding.tvPrimary.hint = "지번, 도로명, 건물명으로 검색"
            binding.ivSearch.visibility = View.VISIBLE
            updateButtonState(false)
        }
    }

    private fun setupOnClickListener() {
        binding.btnAddSuccessNext.setOnClickListener {
            val input = binding.tvPrimary.text.toString()
            viewModel.saveInput(BasicInfoStep.PROTECTED_ADDRESS, input)
            val action = ProtectedAddressFragmentDirections.actionProtectedAddressToProtectedEmergencyContact()
            findNavController().navigate(directions = action)
        }
        binding.btnAddSuccessPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.clPrimaryInfo.setOnClickListener {
            openKakaoAddressSearch()
        }
    }

    private fun updateButtonState(isValid: Boolean) {
        binding.btnAddSuccessNext.isEnabled = isValid
        val color = if (isValid) {
            ContextCompat.getColor(requireContext(), R.color.primary)
        } else {
            ContextCompat.getColor(requireContext(), R.color.Gray200)
        }
        binding.btnAddSuccessNext.backgroundTintList = ColorStateList.valueOf(color)
    }

    private fun openKakaoAddressSearch() {
        lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    KakaoAddressWebViewDialogFragment().show(childFragmentManager, "KakaoAddressDialog")
                }
        }
    }

    private fun observeAddressResult() {
        childFragmentManager.setFragmentResultListener("addressResult", viewLifecycleOwner) { _, bundle ->
            val selectedAddress = bundle.getString("selectedAddress")
            selectedAddress?.let {
                binding.tvPrimary.text = it
                updateButtonState(true)
                viewModel.saveInput(BasicInfoStep.PROTECTED_ADDRESS, it)
            }
        }
    }
}
