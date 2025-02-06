package com.example.eldercare.presentation.ui.devicemanage.add.hub

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddHubConnectWifiBinding

class AddHubConnectWifiFragment : BaseFragment<FragmentAddHubConnectWifiBinding, Nothing>(
    FragmentAddHubConnectWifiBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListener()
    }

    private fun addListener() {
        with(binding) {
            btnAddHubConnectWifiList.setOnClickListener {

            }

            btnAddHubConnectWifiPrevious.setOnClickListener {
                findNavController().popBackStack()
            }
            btnAddHubConnectWifiNext.setOnClickListener {
                navigateToDeviceManageFragment()
            }
        }
    }

    private fun navigateToDeviceManageFragment() {
        val action = AddHubConnectWifiFragmentDirections.actionAddHubConnectWifiFragmentToDeviceManageFragment()
        findNavController().navigate(action)
    }

}
