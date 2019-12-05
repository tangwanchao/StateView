package com.github.nukc.stateview

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingParent
import androidx.core.view.ScrollingView

/**
 * @author Nukc.
 */
internal object Injector {

    /**
     * When viewParent is ConstraintLayout
     * Change other view's Constraint to root, if the origin Constraint is parent
     *
     *
     * Solve the ClassCastException: constraint-layout version 1.1.3
     * java.lang.ClassCastException: android.widget.FrameLayout$LayoutParams cannot be cast to
     * android.support.constraint.ConstraintLayout$LayoutParams
     * at [ConstraintLayout.getTargetWidget]
     * at [ConstraintLayout.setChildrenConstraints]
     * at [ConstraintLayout.updateHierarchy]
     * at [ConstraintLayout.onMeasure]
     *
     * @param viewParent   injectView's parent
     * @param root         wrapper view, replace injectView
     * @param injectViewId injectView's Id
     */
    fun changeChildrenConstraints(viewParent: ViewGroup?, root: FrameLayout, injectViewId: Int) {
        if (viewParent is ConstraintLayout) {
            val rootId = R.id.root_id
            root.id = rootId
            var i = 0
            val count = viewParent.childCount
            while (i < count) {
                val child = viewParent.getChildAt(i)
                val layoutParams = child.layoutParams as ConstraintLayout.LayoutParams
                if (layoutParams.circleConstraint == injectViewId) {
                    layoutParams.circleConstraint = rootId
                } else {
                    if (layoutParams.leftToLeft == injectViewId) {
                        layoutParams.leftToLeft = rootId
                    } else if (layoutParams.leftToRight == injectViewId) {
                        layoutParams.leftToRight = rootId
                    }
                    if (layoutParams.rightToLeft == injectViewId) {
                        layoutParams.rightToLeft = rootId
                    } else if (layoutParams.rightToRight == injectViewId) {
                        layoutParams.rightToRight = rootId
                    }
                    if (layoutParams.topToTop == injectViewId) {
                        layoutParams.topToTop = rootId
                    } else if (layoutParams.topToBottom == injectViewId) {
                        layoutParams.topToBottom = rootId
                    }
                    if (layoutParams.bottomToTop == injectViewId) {
                        layoutParams.bottomToTop = rootId
                    } else if (layoutParams.bottomToBottom == injectViewId) {
                        layoutParams.bottomToBottom = rootId
                    }
                    if (layoutParams.baselineToBaseline == injectViewId) {
                        layoutParams.baselineToBaseline = rootId
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        layoutParams.resolveLayoutDirection(child.layoutDirection)
                    }
                }
                i++
            }
        }
    }

    /**
     * Create a new FrameLayout (wrapper), let parent's remove children views, and add to the wrapper,
     * stateVew add to wrapper, wrapper add to parent
     *
     * @param parent target
     * @return [StateView]
     */
    fun wrapChild(parent: ViewGroup): StateView {
        // If there are other complex needs, maybe you can use stateView in layout(.xml)
        var screenHeight = 0
        // create a new FrameLayout to wrap StateView and parent's childView
        val wrapper = FrameLayout(parent.context)
        val layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        wrapper.layoutParams = layoutParams

        if (parent is LinearLayout) {
            // create a new LinearLayout to wrap parent's childView
            val wrapLayout = LinearLayout(parent.context)
            wrapLayout.layoutParams = parent.layoutParams
            wrapLayout.orientation = parent.orientation
            var i = 0
            val childCount: Int = parent.getChildCount()
            while (i < childCount) {
                val childView: View = parent.getChildAt(0)
                parent.removeView(childView)
                wrapLayout.addView(childView)
                i++
            }
            wrapper.addView(wrapLayout)
        } else if (parent is ScrollView || parent is ScrollingView) {
            // not recommended to inject Scrollview/NestedScrollView
            if (parent.childCount != 1) {
                throw IllegalStateException("the ScrollView does not have one direct child")
            }
            val directView: View = parent.getChildAt(0)
            parent.removeView(directView)
            wrapper.addView(directView)
            val wm = parent.context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(metrics)
            screenHeight = metrics.heightPixels
        } else if (parent is NestedScrollingParent &&
                parent is NestedScrollingChild) {
            if (parent.childCount == 2) {
                val targetView: View = parent.getChildAt(1)
                parent.removeView(targetView)
                wrapper.addView(targetView)
            } else if (parent.childCount > 2) {
                throw IllegalStateException("the view is not refresh layout? view = $parent")
            }
        } else {
            throw IllegalStateException("the view does not have parent, view = $parent")
        }
        // parent add wrapper
        parent.addView(wrapper)
        // StateView will be added to wrapper
        val stateView = StateView(parent.context)
        if (screenHeight > 0) {
            // let StateView be shown in the center
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight)
            wrapper.addView(stateView, params)
        } else {
            wrapper.addView(stateView)
        }
        return stateView
    }

    /**
     * Set StateView to be the same size and position as the target view，
     * layoutParams should be use [ConstraintLayout.LayoutParams] (android.view.ViewGroup.LayoutParams)
     *
     * @param parent view's parent, [ConstraintLayout]
     * @param view target view
     * @return [StateView]
     */
    fun matchViewIfParentIsConstraintLayout(parent: ConstraintLayout, view: View): StateView {
        val stateView = StateView(parent.context)
        val lp = ConstraintLayout.LayoutParams(view.layoutParams as ViewGroup.LayoutParams)
        lp.leftToLeft = view.id
        lp.rightToRight = view.id
        lp.topToTop = view.id
        lp.bottomToBottom = view.id
        parent.addView(stateView, lp)
        return stateView
    }

    /**
     * Set StateView to be the same size and position as the target view， If parent is RelativeLayout
     *
     * @param parent view's parent, [RelativeLayout]
     * @param view target view
     * @return [StateView]
     */
    fun matchViewIfParentIsRelativeLayout(parent: RelativeLayout, view: View): StateView {
        val stateView = StateView(parent.context)
        val lp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams(view.layoutParams as RelativeLayout.LayoutParams)
        } else {
            RelativeLayout.LayoutParams(view.layoutParams)
        }
        parent.addView(stateView, lp)
        setStateListAnimator(stateView, view)
        return stateView
    }

    /**
     * In order to display on the button top
     */
    fun setStateListAnimator(stateView: StateView, target: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && target is Button) {
            Log.i(StateView.TAG, "for normal display, stateView.stateListAnimator = view.stateListAnimator")
            stateView.stateListAnimator = target.stateListAnimator
        }
    }
}