package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

/**
 * 描述：动画的使用
 * 作者：zzh
 * time:2019/05/03
 */
public class AnimationActivity extends Activity {

    private Context mContext = AnimationActivity.this;
    private Activity mActivity = AnimationActivity.this;
    private int screenWidth = 0,screenHeight = 0;
    private ImageView iv_animation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animation);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        initView();
    }

    private void initView() {
        Info.setActionBar(mActivity,R.id.headerLayout,"动画");

        iv_animation = findViewById(R.id.iv_animation);

        Button btn_alpha_animation = findViewById(R.id.btn_alpha_animation);
        btn_alpha_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1f,0.2f);
                alphaAnimation.setDuration(3000);
                iv_animation.startAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f,0.9f);
                        alphaAnimation.setDuration(2000);
                        iv_animation.startAnimation(alphaAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        Button btn_rotate_animation = findViewById(R.id.btn_rotate_animation);
        btn_rotate_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.rotates);
                animation.setDuration(3000);
                animation.setRepeatCount(2);
                iv_animation.startAnimation(animation);
            }
        });

        Button btn_traslate_animation = findViewById(R.id.btn_traslate_animation);
        btn_traslate_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TranslateAnimation translateAnimation = new TranslateAnimation(0,screenWidth-iv_animation.getWidth(),0,screenHeight-iv_animation.getHeight());
                translateAnimation.setDuration(3000);
                iv_animation.startAnimation(translateAnimation);

                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        Button btn_scale_animation = findViewById(R.id.btn_scale_animation);
        btn_scale_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f,5f,1f,5f);
                scaleAnimation.setDuration(3000);
                iv_animation.startAnimation(scaleAnimation);
            }
        });
    }
}
