package com.github.nukc.sample;

import android.view.View;

public class RefreshActivity extends BaseActivity {

    @Override
    protected int setContentView() {
        return R.layout.activity_refresh;
    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.refresh_layout);
    }
}
