package com.github.nukc.stateview;

import android.animation.Animator;

/**
 * @author Nukc.
 */

public interface AnimatorProvider {

    Animator showAnimation();

    Animator hideAnimation();
}
