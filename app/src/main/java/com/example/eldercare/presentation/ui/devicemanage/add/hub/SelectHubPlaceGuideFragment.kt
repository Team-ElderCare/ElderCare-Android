package com.example.eldercare.presentation.ui.devicemanage.add.hub

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.eldercare.R
import com.example.eldercare.base.fragment.BaseFragment
import com.example.eldercare.databinding.FragmentSelectHubPlaceGuideBinding
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.createBalloon

class SelectHubPlaceGuideFragment : BaseFragment<FragmentSelectHubPlaceGuideBinding, Nothing>(
    FragmentSelectHubPlaceGuideBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListener()
    }

    private fun addListener() {
        with(binding) {
            btnSelectHubPlaceGuideTooltip.setOnClickListener {
                tooltipBalloon()?.showAlignBottom(btnSelectHubPlaceGuideTooltip)
            }
            btnSelectHubPlaceGuideNext.setOnClickListener {
                navigateToAddHubConnectPowerFragment()
            }
        }
    }

    private fun tooltipBalloon(): Balloon? =
        context?.let {
            createBalloon(context = it) {
                setArrowSize(24)
                setWidthRatio(0.75f)
                setCornerRadius(24f)
                setBackgroundColorResource(R.color.primary)
                setText(requireContext().getString(R.string.recommend_place_tooltip_content))
                setTextColorResource(R.color.white)
                setTextSize(14f)
                setPadding(12)
                setArrowPosition(0.3f)
                setLifecycleOwner(lifecycleOwner)
                setDismissWhenClicked(true)
                setDismissWhenTouchOutside(true)
                setBalloonAnimation(com.skydoves.balloon.BalloonAnimation.FADE)
            }
        }

    private fun navigateToAddHubConnectPowerFragment() {
        val action = SelectHubPlaceGuideFragmentDirections.actionSelectHubPlaceGuideFragmentToAddHubConnectPowerFragment()
        findNavController().navigate(action)
    }

}
