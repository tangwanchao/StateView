package com.github.nukc.sample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.nukc.stateview.StateView;
import com.github.nukc.stateview.animations.FadeScaleAnimatorProvider;
import com.github.nukc.stateview.animations.FlipAnimatorProvider;
import com.github.nukc.stateview.animations.SlideAnimatorProvider;

public class AnimatorActivity extends AppCompatActivity {

    private StateView mStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        mStateView = (StateView) findViewById(R.id.stateView);
        mStateView.setEmptyResource(R.layout.view_empty);
        setBtn();

        mStateView.setOnInflateListener(new StateView.OnInflateListener() {
            @Override
            public void onInflate(@StateView.ViewType int viewType, View view) {
                if (viewType == StateView.EMPTY) {
                    // set text or other
                    ViewGroup emptyView = (ViewGroup) view;
                    TextView tvMessage = emptyView.findViewById(R.id.tv_message);
                    ImageView ivState = emptyView.findViewById(R.id.iv_state);
                    tvMessage.setText("custom message");
                    ivState.setImageResource(R.drawable.retry);
                }
            }
        });
    }

    private void setBtn() {
        findViewById(R.id.btnFadeScale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateView.setAnimatorProvider(new FadeScaleAnimatorProvider());
                showAnimationView();
            }
        });

        findViewById(R.id.btnFlip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateView.setAnimatorProvider(new FlipAnimatorProvider(false));
                showAnimationView();
            }
        });

        findViewById(R.id.btnSlide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateView.setAnimatorProvider(new SlideAnimatorProvider());
                showAnimationView();
            }
        });
    }

    private int mStatus = 0;
    private void showAnimationView() {
        switch (mStatus){
            case 0:
                mStateView.showLoading();
                mStatus = 1;
                break;
            case 1:
                mStateView.showEmpty();
                mStatus = 0;
                break;
        }
    }
}
