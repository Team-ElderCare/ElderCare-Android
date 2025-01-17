package com.example.eldercare.util.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null,
) {
    // 현재 목적지와 이동할 목적지 비교 (목적지 중복 이동 방지)
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if (action != null && currentDestination?.id != action.destinationId) {
        val newNavOptions = navOptions?.let { NavOptions.Builder() } ?: NavOptions.Builder()
        newNavOptions.setPopUpTo(action.destinationId, true)
        navigate(resId, args, newNavOptions.build(), navExtras)
    }
}

fun NavController.navigateToTopLevelDestination(
    @IdRes destinationId: Int,
    navController: NavController,
): Boolean {
    val builder =
        NavOptions.Builder()
            .setLaunchSingleTop(true) // 동일 목적지 중복 생성 방지
            .setRestoreState(true) // 프래그먼트 상태 유지

    // 현재 목적지와 이동할 목적지 비교
    if (currentDestination?.id != destinationId) {
        // 최상위 목적지로 이동 시 백스택 관리 , 백스택 저장
        builder.setPopUpTo(
            navController.graph.findStartDestination().id,
            inclusive = false,
            saveState = true,
        )
    }

    return try {
        navigate(destinationId, null, builder.build())
        true
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        false
    }
}
