package com.example.eldercare.presentation.ui.devicemanage.add.hub

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddHubConnectPowerBinding

class AddHubConnectPowerFragment : BaseFragment<FragmentAddHubConnectPowerBinding, Nothing>(
    FragmentAddHubConnectPowerBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListener()
    }

    private fun addListener() {
        with(binding) {
            btnAddHubConnectPowerPrevious.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAddHubConnectPowerNext.setOnClickListener {
                navigateToAddHubSelectPlaceFragment()
            }
        }
    }

    private fun navigateToAddHubSelectPlaceFragment() {
        val action = AddHubConnectPowerFragmentDirections.actionAddHubConnectPowerFragmentToAddHubSelectPlaceFragment()
        findNavController().navigate(action)
    }

}
