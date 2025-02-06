package com.example.eldercare.presentation.ui.devicemanage.add.hub

import android.os.Bundle
import android.view.View
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddHubConnectPowerBinding

class AddHubConnectPowerFragment : BaseFragment<FragmentAddHubConnectPowerBinding, Nothing>(
    FragmentAddHubConnectPowerBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addListener() {
        with(binding) {
            btnAddHubConnectPowerPrevious.setOnClickListener {

            }

            btnAddHubConnectPowerNext.setOnClickListener {

            }
        }
    }

}
