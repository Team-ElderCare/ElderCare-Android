package com.example.eldercare.presentation.ui.devicemanage.add.hub

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddHubBluetoothConnectBinding

class AddHubConnectBluetoothFragment : BaseFragment<FragmentAddHubBluetoothConnectBinding, Nothing>(
    FragmentAddHubBluetoothConnectBinding::inflate,
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
            btnAddHubBluetoothConnectPrevious.setOnClickListener {
                findNavController().popBackStack()
            }
            btnAddHubBluetoothConnectNext.setOnClickListener {
                navigateToAddHubConnectWifiFragment()
            }
        }
    }

    private fun navigateToAddHubConnectWifiFragment() {
        val action = AddHubConnectBluetoothFragmentDirections.actionAddHubConnectBluetoothFragmentToAddHubConnectWifiFragment()
        findNavController().navigate(action)
    }
}
