package com.example.eldercare.presentation.ui.devicemanage.add.hub

import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddHubConnectWifiBinding

class AddHubConnectWifiFragment : BaseFragment<FragmentAddHubConnectWifiBinding, Nothing>(
    FragmentAddHubConnectWifiBinding::inflate
) {
    override fun onDestroyView() {
        super.onDestroyView()

        addListener()
    }

    private fun addListener() {
        with(binding) {
            btnAddHubConnectWifiList.setOnClickListener {

            }

            btnAddHubConnectWifiPrevious.setOnClickListener {

            }
            btnAddHubConnectWifiNext.setOnClickListener {

            }
        }
    }

}
