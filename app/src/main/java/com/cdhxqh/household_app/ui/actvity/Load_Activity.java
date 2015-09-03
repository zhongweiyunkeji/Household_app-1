package com.cdhxqh.household_app.ui.actvity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.cdhxqh.household_app.R;

/**
 * Entry Activity
 *
 * @author bxbxbai
 */
public class Load_Activity extends BaseActivity {

    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;


    private static final int[] SPLASH_ARRAY = {
            R.drawable.front_cover,

    };

    ImageView mSplashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_);
        mSplashImage=(ImageView)findViewById(R.id.iv_entry);
        mSplashImage.setImageResource(R.drawable.front_cover);
        animateImage();
    }


    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mSplashImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mSplashImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent();
                /*if(!mIsLogin || myshared.getString(Constants.PASS_KEY, "").equals("")) {
                    intent.setClass(Load_Activity.this, Activity_Login.class);
                }else {
                    intent.setClass(Load_Activity.this, MainActivity.class);
                }*/
                intent.setClass(Load_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
