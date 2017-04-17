package com.github.nukc.sample;

import android.view.View;

public class InjectViewActivity extends BaseActivity {

    @Override
    protected int setContentView() {
        return R.layout.activity_inject_view;
    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.text_view);
    }

}
