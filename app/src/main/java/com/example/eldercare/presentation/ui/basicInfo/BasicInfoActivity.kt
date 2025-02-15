package com.example.eldercare.presentation.ui.basicInfo

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.example.eldercare.base.activity.BaseActivity
import com.example.eldercare.databinding.ActivityBasicInfoBinding
import com.example.eldercare.util.context.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasicInfoActivity : BaseActivity<ActivityBasicInfoBinding, BasicInfoViewModel> (
    ActivityBasicInfoBinding::inflate,
) {
    override val viewModel: BasicInfoViewModel by viewModels()
    private lateinit var navController: NavController

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val touchedView = getTouchedView(ev.rawX.toInt(), ev.rawY.toInt())
            if (touchedView !is Button) {
                currentFocus?.let { focusedView ->
                    if (focusedView is EditText) {
                        val outRect = Rect()
                        focusedView.getGlobalVisibleRect(outRect)
                        if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                            focusedView.clearFocus()
                            hideKeyboard(focusedView)
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun getTouchedView(
        x: Int,
        y: Int,
    ): View? {
        val rootView = window.decorView.findViewById<ViewGroup>(android.R.id.content)
        return findViewAtPosition(rootView, x, y)
    }

    private fun findViewAtPosition(
        view: View,
        x: Int,
        y: Int,
    ): View? {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val left = location[0]
        val top = location[1]
        val right = left + view.width
        val bottom = top + view.height

        if (x in left until right && y in top until bottom) {
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val child = view.getChildAt(i)
                    val touched = findViewAtPosition(child, x, y)
                    if (touched != null) return touched
                }
            }
            return view
        }
        return null
    }
}
