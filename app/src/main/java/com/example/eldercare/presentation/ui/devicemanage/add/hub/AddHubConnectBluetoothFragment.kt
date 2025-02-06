package com.example.eldercare.presentation.ui.devicemanage.add.hub

import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddHubBluetoothConnectBinding

class AddHubConnectBluetoothFragment : BaseFragment<FragmentAddHubBluetoothConnectBinding, Nothing>(
    FragmentAddHubBluetoothConnectBinding::inflate
) {
    override fun onDestroyView() {
        super.onDestroyView()

        addListener()
    }

    private fun addListener() {
        with(binding) {

        }
    }

}
