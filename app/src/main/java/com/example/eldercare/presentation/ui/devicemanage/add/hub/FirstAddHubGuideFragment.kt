package com.example.eldercare.presentation.ui.devicemanage.add.hub

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddHubGuideFirstBinding

class FirstAddHubGuideFragment : BaseFragment<FragmentAddHubGuideFirstBinding, Nothing>(
    FragmentAddHubGuideFirstBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener()
    }

    private fun addListener() {
        with(binding) {
            btnAddHubGuideFirstGuideNext.setOnClickListener {
                navigateToSecondAddHubFragment()
            }
        }
    }

    private fun navigateToSecondAddHubFragment() {
        val action = FirstAddHubGuideFragmentDirections.actionFirstAddHubGuideFragmentToSecondAddHubGuideFragment()
        findNavController().navigate(action)
    }

}
