package com.example.eldercare.presentation.ui.devicemanage.add.hub

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentAddHubSelectPlaceBinding

class AddHubSelectPlaceFragment : BaseFragment<FragmentAddHubSelectPlaceBinding, Nothing>(
    FragmentAddHubSelectPlaceBinding::inflate,
) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        with(binding) {
            val cg = includeAddHubSelectPlaceViewTitleWithCg
            cg.cgViewTitleWithChipGroup.setOnClickListener {
                if (cg.chipViewTitleWithChipGroupEnterManually.isChecked) {
                    enableTextField(true)
                    cg.cgViewTitleWithChipGroup.clearCheck()
                    cg.chipViewTitleWithChipGroupEnterManually.isChecked = true
                } else {
                    enableTextField(false)
                }
            }

            cg.cgViewTitleWithChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.contains(cg.chipViewTitleWithChipGroupEnterManually.id)) {
                    enableTextField(true)
                } else {
                    enableTextField(false)
                }
            }

            btnAddHubSelectPrevious.setOnClickListener {
                findNavController().popBackStack()
            }
            btnAddHubSelectNext.setOnClickListener {
                navigateToAddHubSelectNameFragment()
            }
        }
    }

    private fun enableTextField(isVisible: Boolean) {
        binding.etAddHubSelectPlace.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun navigateToAddHubSelectNameFragment() {
        val action = AddHubSelectPlaceFragmentDirections.actionAddHubSelectPlaceFragmentToAddHubSelectNameFragment()
        findNavController().navigate(action)
    }
}
