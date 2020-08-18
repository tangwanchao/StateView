package com.github.nukc.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.nukc.stateview.StateView;
import com.github.nukc.stateview.animations.SlideAnimatorProvider;

public class SetViewActivity extends AppCompatActivity {

    private StateView mStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inject);

        mStateView = StateView.inject(this);
        mStateView.setAnimatorProvider(new SlideAnimatorProvider());
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                //do something
                mStateView.showRetry();
            }
        });

        View emptyView = View.inflate(this, R.layout.view_empty, null);
        TextView tvMessage = emptyView.findViewById(R.id.tv_message);
        if (tvMessage != null) {
            tvMessage.setText("Run setEmptyView");
        }
        mStateView.setEmptyView(emptyView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_inject, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_empty:
                mStateView.showEmpty();
                break;
            case R.id.show_retry:
                mStateView.showRetry();
                break;
            case R.id.show_loading:
                mStateView.showLoading();
                break;
            case R.id.show_content:
                mStateView.showContent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
