package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.photoview.PhotoView;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.CommonUtil;
import com.yks.simpledemo2.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述：图片转字符显示
 * 作者：zzh
 * time:2018/09/29
 * 参考：https://github.com/meiniepan/Pic2Ascii
 */

public class PicAsciiActivity extends Activity implements View.OnClickListener{

    private Button btn_pic_black,btn_pic_color,btn_pic_save;
    private PhotoView iv_pic_show;
    private Bitmap bitmap;
    private int CHOOSE_REQUEST_COLOR = 500;
    private String filePath;
    private String path;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_picascii);

        initView();
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.zhiling);
    }

    private void initView() {
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "图片转字符集");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_pic_black = findViewById(R.id.btn_pic_black);
        btn_pic_color = findViewById(R.id.btn_pic_color);
        btn_pic_save = findViewById(R.id.btn_pic_save);
        iv_pic_show = findViewById(R.id.iv_pic_show);

        btn_pic_black.setOnClickListener(this);
        btn_pic_color.setOnClickListener(this);
        btn_pic_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_pic_black){
            CommonUtil.choosePhoto(this, PictureConfig.CHOOSE_REQUEST);
        }else if (view == btn_pic_color){
            CommonUtil.choosePhoto(this,CHOOSE_REQUEST_COLOR);
        }else if (view == btn_pic_save){
            CommonUtil.saveBitmap2file(bitmap,PicAsciiActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == PictureConfig.CHOOSE_REQUEST){//黑白
                List<LocalMedia> mList = PictureSelector.obtainMultipleResult(data);
                String path = "";
                if (mList != null && mList.size() > 0){//有返回
                    LocalMedia media = mList.get(0);
                    if (media.isCompressed()){
                        path = media.getCompressPath();
                    }else if (media.isCut()){//截图裁剪
                        path = media.getCutPath();
                    }else {
                        path = media.getPath();
                    }
                }
                filePath = CommonUtil.amendRotatePhoto(path,PicAsciiActivity.this);
                bitmap = CommonUtil.createAsciiPic(filePath,PicAsciiActivity.this);
                iv_pic_show.setImageBitmap(bitmap);
            }else if (requestCode == CHOOSE_REQUEST_COLOR){//彩色
                List<LocalMedia> mList = PictureSelector.obtainMultipleResult(data);
                if (mList != null && mList.size() > 0){
                    LocalMedia media = mList.get(0);
                    if (media.isCompressed()){
                        path = media.getCompressPath();
                    }else if (media.isCut()){
                        path = media.getCutPath();
                    }else {
                        path = media.getPath();
                    }
                }

                LemonBubble.showRoundProgress(PicAsciiActivity.this,"处理中");
                io.reactivex.Observable.fromCallable(new Callable<Bitmap>() {
                    @Override
                    public Bitmap call() throws Exception {
                        filePath = CommonUtil.amendRotatePhoto(path,PicAsciiActivity.this);
                        bitmap = CommonUtil.createAsciiPic(filePath,PicAsciiActivity.this);
                        return bitmap;
                    }
                }).compose(PicAsciiActivity.<Bitmap>switchSchedulers()).subscribeWith(new DisposableObserver<Bitmap>(){
                    @Override
                    public void onNext(Bitmap bitmap) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        iv_pic_show.setImageBitmap(bitmap);
                        LemonBubble.hide();
                    }
                });
            }
        }
    }

    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).doOnSubscribe(disposable -> {
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
