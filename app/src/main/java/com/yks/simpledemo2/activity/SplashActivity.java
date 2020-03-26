package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yks.simpledemo2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 描述：启动页，清晰度的一个过渡动画
 * 作者：zzh
 * time:2019/05/11
 */
public class SplashActivity extends Activity {

    private Activity mActivity = SplashActivity.this;
    private List<String> mList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.layout_splash);

        initImageData();
        initView();
    }

    private void initView() {
        ImageView iv_splash = findViewById(R.id.iv_splash);
        //todo 获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        //todo 随机获取列表中一张图片的url
        String url = mList.get(new Random().nextInt(mList.size()));
        //todo 设置加载图片的选项等
        Glide.with(mActivity).load(url).override(width,height).centerCrop().placeholder(R.mipmap.child).into(iv_splash);
        //todo 设置由模糊到清晰的动画效果
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f,1f);
        alphaAnimation.setDuration(5000);
        iv_splash.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(mActivity,MainActivity.class));
                Glide.with(mActivity).onDestroy();
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initImageData(){
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813794&di=062285a961a11f2d1163c045b03f69ba&imgtype=0&src=http%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D378254553%2C3884800361%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D2030");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813794&di=96899f737d8cad511088d56623aaa41b&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D3571592872%2C3353494284%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1200%26h%3D1290");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813793&di=8f54a958ec1259a81fcf5063c9bb1fb9&imgtype=0&src=http%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D3616242789%2C1098670747%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D900%26h%3D1350");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813793&di=4a72e34c1e3f40b37d1cbf64141ca4b1&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D1484500186%2C1503043093%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D853");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813793&di=a78c0e68b0bada9ecc887e76e33f7d47&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D2247852322%2C986532796%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D853");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813793&di=4f89728ef6fda5767d446f4aa0f30ea0&imgtype=0&src=http%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D3204887199%2C3790688592%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D4610%26h%3D2968");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813793&di=db8c96daa14fbadafde00cf187f1941d&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D3363001160%2C1163944807%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D830");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813792&di=d08ec32dbc6740a332ffa771ece95413&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D583874135%2C70653437%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D3607%26h%3D2408");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813791&di=9292521514b75d8e98243f7851e23450&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D1307125826%2C3433407105%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D5760%26h%3D3240");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585134813791&di=172a4e1659d2de26ad59f8ae811b9ce0&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D2268908537%2C2815455140%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D719");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585135012985&di=74ad6f19b179782e702a046cac31512b&imgtype=0&src=http%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D1179872664%2C290201490%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D854");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585135012984&di=934682cd7eb00e1c913b7b3f6373607e&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D2266751744%2C4253267866%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D854");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585135012983&di=e89567bcc8aed964cba7336f4ac6ddd7&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D3949188917%2C63856583%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D875");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585135012980&di=493e9d50e4b9d8051c3eb4ce88f5e737&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D4241966675%2C2405819829%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D854");
    }

}
