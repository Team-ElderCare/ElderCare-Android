package com.example.eldercare.presentation.ui.main

import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eldercare.R
import com.example.eldercare.base.activity.BaseActivity
import com.example.eldercare.databinding.ActivityMainBinding
import com.example.eldercare.util.dimension.dpToPx
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate,
) {
    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = binding.mainNavigation
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.background = createBackgroundDrawable()
        bottomNavigationView.itemIconTintList =
            createColorStateList(
                ContextCompat.getColor(this, R.color.Green800),
                ContextCompat.getColor(this, R.color.Gray400),
            )
        bottomNavigationView.itemTextColor = bottomNavigationView.itemIconTintList
        bottomNavigationView.itemBackground = createItemBackgroundDrawable()
    }

    private fun createItemBackgroundDrawable(): StateListDrawable {
        val activeIndicatorDrawable =
            MaterialShapeDrawable().apply {
                setTint(ContextCompat.getColor(this@MainActivity, R.color.Green50))
                shapeAppearanceModel =
                    ShapeAppearanceModel.builder()
                        .setAllCornerSizes(120f)
                        .build()
            }

        return StateListDrawable().apply {
            addState(
                intArrayOf(android.R.attr.state_checked),
                InsetDrawable(activeIndicatorDrawable, 8.dpToPx(), 7.dpToPx(), 8.dpToPx(), 7.dpToPx()),
            )
        }
    }

    private fun createBackgroundDrawable(): MaterialShapeDrawable {
        return MaterialShapeDrawable().apply {
            shapeAppearanceModel =
                ShapeAppearanceModel.builder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, 8.dpToPx().toFloat())
                    .setTopRightCorner(CornerFamily.ROUNDED, 8.dpToPx().toFloat())
                    .build()
            setTint(ContextCompat.getColor(this@MainActivity, R.color.white))
        }
    }

    private fun createColorStateList(
        checkedColor: Int,
        uncheckedColor: Int,
    ): android.content.res.ColorStateList {
        return android.content.res.ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked),
            ),
            intArrayOf(checkedColor, uncheckedColor),
        )
    }
}
