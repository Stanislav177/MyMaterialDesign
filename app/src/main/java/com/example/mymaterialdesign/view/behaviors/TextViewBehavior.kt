package com.example.mymaterialdesign.view.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import kotlin.math.abs

class TextViewBehavior(context: Context, attributeSet: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attributeSet) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is ConstraintLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val nestedScroll = dependency as ConstraintLayout

        if ((abs(nestedScroll.y) > nestedScroll.height / 2)) {
            child.visibility = View.VISIBLE
        } else {
            child.visibility = View.GONE

//            val alpha = (nestedScroll.height / 2 - abs(nestedScroll.y)) / (nestedScroll.height/2)
//            child.alpha = alpha
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}