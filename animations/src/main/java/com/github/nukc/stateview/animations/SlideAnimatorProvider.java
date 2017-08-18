package com.github.nukc.stateview.animations;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.github.nukc.stateview.AnimatorProvider;
import com.github.nukc.stateview.StateView;

/**
 * @author Nukc.
 */

public class SlideAnimatorProvider implements AnimatorProvider {

    private StateView mStateView;

    public SlideAnimatorProvider(StateView view) {
        mStateView = view;
    }

    @Override
    public Animator showAnimation() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(null, "alpha", 0, 1),
                ObjectAnimator.ofFloat(null, "translationX", getDistance(), 0)
        );
        set.setInterpolator(new AccelerateInterpolator());
        return set;
    }

    @Override
    public Animator hideAnimation() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(null, "alpha", 1, 0),
                ObjectAnimator.ofFloat(null, "translationX", 0, getDistance())
        );
        set.setInterpolator(new DecelerateInterpolator());
        return set;
    }

    private float getDistance() {
        ViewParent viewParent = mStateView.getParent();
        if (viewParent == null) {
            return 0f;
        } else {
            return ((View) viewParent).getWidth();
        }
    }
}
