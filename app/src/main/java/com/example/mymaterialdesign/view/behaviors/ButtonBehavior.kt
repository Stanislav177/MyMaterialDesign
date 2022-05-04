package com.example.mymaterialdesign.view.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ButtonBehavior(context: Context, attributeSet: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attributeSet) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val bar = dependency as AppBarLayout

        if ((abs(bar.y) > bar.height / 2)) {
            child.visibility = View.GONE
        } else {
            child.visibility = View.VISIBLE
            val alpha = (bar.height / 2 - abs(bar.y)) / (bar.height/2)
            child.alpha = alpha
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}