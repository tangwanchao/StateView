package com.github.nukc.stateview.animations;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import com.github.nukc.stateview.AnimatorProvider;

/**
 * @author Nukc.
 */

public class FadeScaleAnimatorProvider implements AnimatorProvider {

    @Override
    public Animator showAnimation() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(null, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(null, "scaleX", 0.1f, 1f),
                ObjectAnimator.ofFloat(null, "scaleY", 0.1f, 1f)
        );
        return set;
    }

    @Override
    public Animator hideAnimation() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(null, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(null, "scaleX", 1f, 0.1f),
                ObjectAnimator.ofFloat(null, "scaleY", 1f, 0.1f)
        );
        return set;
    }
}
