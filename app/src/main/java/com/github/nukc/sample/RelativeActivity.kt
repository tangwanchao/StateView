package com.github.nukc.sample

import android.os.Bundle
import android.view.View
import com.github.nukc.stateview.animations.SlideAnimatorProvider
import kotlinx.android.synthetic.main.activity_relative.*

class RelativeActivity : BaseActivity() {
    override fun injectTarget(): View {
        return btn
    }

    override fun setContentView(): Int {
        return R.layout.activity_relative
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimator(SlideAnimatorProvider())
    }
}
