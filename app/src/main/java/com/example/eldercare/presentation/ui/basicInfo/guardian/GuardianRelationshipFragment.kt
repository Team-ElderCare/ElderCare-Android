package com.example.eldercare.presentation.ui.basicInfo.guardian

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInfoGuardianRelationshipBinding
import com.example.eldercare.domain.model.basicInfo.Relationship
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuardianRelationshipFragment : BaseFragment<FragmentInfoGuardianRelationshipBinding, BasicInfoViewModel>(
    FragmentInfoGuardianRelationshipBinding::inflate,
) {
    override val viewModel: BasicInfoViewModel by activityViewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        setupChipGroup()
        setupOnClickListener()
    }

    private fun setupChipGroup() {
        updateButtonState(false)

        val savedRelationship = viewModel.getSavedRelationship()

        binding.chipGroupRelationship.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val selectedChip = group.findViewById<Chip>(checkedIds.first())
                val selectedText = selectedChip.text.toString()

                val relationship = Relationship.fromDisplayName(selectedText)
                if (relationship != null) {
                    viewModel.saveRelationship(relationship)
                    updateButtonState(true)
                }
            } else {
                updateButtonState(false)
            }
        }

        savedRelationship?.let {
            val chipToSelect = getChipByRelationship(it)
            chipToSelect?.isChecked = true
            updateButtonState(true)
        }
    }

    private fun getChipByRelationship(relationship: Relationship): Chip? {
        return binding.chipGroupRelationship.children
            .filterIsInstance<Chip>()
            .firstOrNull { it.text.toString() == relationship.displayName }
    }

    private fun setupOnClickListener() {
        binding.btnAddSuccessNext.setOnClickListener {
            val action = GuardianRelationshipFragmentDirections.actionGuardianRelationshipToGuardianPhoto()
            findNavController().navigate(directions = action)
        }

        binding.btnAddSuccessPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateButtonState(isValid: Boolean) {
        binding.btnAddSuccessNext.isEnabled = isValid
        val color =
            if (isValid) {
                ContextCompat.getColor(requireContext(), R.color.primary)
            } else {
                ContextCompat.getColor(requireContext(), R.color.Gray200)
            }
        binding.btnAddSuccessNext.backgroundTintList = ColorStateList.valueOf(color)
    }
}
