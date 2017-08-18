package com.github.nukc.stateview.animations;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import com.github.nukc.stateview.AnimatorProvider;

/**
 * @author Nukc.
 */

public class FlipAnimatorProvider implements AnimatorProvider {

    private String mPropertyName;

    public FlipAnimatorProvider() {
        mPropertyName = "rotationX";
    }

    public FlipAnimatorProvider(boolean isX) {
        mPropertyName = isX ? "rotationX" : "rotationY";
    }

    @Override
    public Animator showAnimation() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(null, mPropertyName, 90f, -15f, 15f, 0f),
                ObjectAnimator.ofFloat(null, "alpha", 0.25f, 0.5f, 0.75f, 1f)
        );
        set.setDuration(400);
        return set;
    }

    @Override
    public Animator hideAnimation() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(null, mPropertyName, -90f, 15f, -15f, 0f),
                ObjectAnimator.ofFloat(null, "alpha", 1f, 0.75f, 0.25f, 0f)
        );
        set.setDuration(400);
        return set;
    }
}
