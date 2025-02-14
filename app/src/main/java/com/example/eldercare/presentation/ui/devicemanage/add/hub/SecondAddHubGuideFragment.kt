package com.example.eldercare.presentation.ui.devicemanage.add.hub

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddHubGuideSeconedBinding

class SecondAddHubGuideFragment : BaseFragment<FragmentAddHubGuideSeconedBinding, Nothing>(
    FragmentAddHubGuideSeconedBinding::inflate,
) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        addListener()
    }

    private fun addListener() {
        with(binding) {
            btnAddHubGuideSecondGuideNext.setOnClickListener {
                navigateToSelectAddHubGuideFragment()
            }
        }
    }

    private fun navigateToSelectAddHubGuideFragment() {
        val action = SecondAddHubGuideFragmentDirections.actionSecondAddHubGuideFragmentToSelectHubPlaceGuideFragment()
        findNavController().navigate(action)
    }
}
