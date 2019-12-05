package com.github.nukc.stateview

import android.view.View

/**
 * @author Nukc.
 */
fun View.state() = StateView.inject(this)
