package com.example.eldercare.presentation.ui.basicInfo.guardian

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentInfoGuardianPhotoBinding
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoStep
import com.example.eldercare.presentation.ui.basicInfo.BasicInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuardianPhotoFragment : BaseFragment<FragmentInfoGuardianPhotoBinding, BasicInfoViewModel>(
    FragmentInfoGuardianPhotoBinding::inflate,
) {
    override val viewModel: BasicInfoViewModel by activityViewModels()

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.saveInput(BasicInfoStep.GUARDIAN_PHOTO, it.toString())
                loadImage(it)
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        loadSavedImage()
        setupOnClickListener()
    }

    private fun loadSavedImage() {
        val savedImageUri = viewModel.getInput(BasicInfoStep.GUARDIAN_PHOTO)
        if (!savedImageUri.isNullOrEmpty()) {
            loadImage(Uri.parse(savedImageUri))
        }
    }

    private fun loadImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivProfileImage)
    }

    private fun setupOnClickListener() {
        binding.btnRegisterPhoto.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.btnAddSuccessNext.setOnClickListener {
            val action = GuardianPhotoFragmentDirections.actionGuardianPhotoToProtectedName()

            findNavController().navigate(
                directions = action,
            )
        }

        binding.btnAddSuccessPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
