package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.print.PrintHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.io.File;
import java.util.List;

/**
 * 描述：人脸识别
 * 作者：zzh
 * time:2018/12/17
 * 参考文章：https://www.cnblogs.com/mainroadlee/p/android_sdk_face_detection.html
 */

public class FaceRecognitionActivity extends Activity implements View.OnClickListener{

    private Context mContext = FaceRecognitionActivity.this;
    private ImageView iv_facerecognite;
    private Button btn_takephote;
    private static final int LUBANYASUO = 1;
    private static final int REQUEST_CODE_SETTING = 300;
    public int REQUEST_CODE_CAMERA = 4;
    private String path = Environment.getExternalStorageDirectory()+"/zzh/";
    private String imgPath = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_facerecognite);

        initView();
    }

    private void initView() {
        Info.setActionBar(FaceRecognitionActivity.this,R.id.headerLayout,"人脸识别");

        iv_facerecognite = findViewById(R.id.iv_facerecognite);
        btn_takephote = findViewById(R.id.btn_takephote);
        btn_takephote.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_takephote){
//            takePhoto();
            doPhotoPrint();
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LUBANYASUO){
                LemonBubble.hide();
                iv_facerecognite.setImageURI(Uri.parse(imgPath));
                Bitmap tmpBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.zzh);
                FaceDetector faceDet = new FaceDetector(tmpBitmap.getWidth(),tmpBitmap.getHeight(),1);
                FaceDetector.Face [] faceList = new FaceDetector.Face[1];
                faceDet.findFaces(tmpBitmap,faceList);
            }
        }
    };
    private void takePhoto(){
        //权限管理 拍照需要调取相机和存储图片
        //todo 注意，安卓7.0及以上调用相机必须要弹框确认权限，否则会无法打开相机
        AndPermission.with(mContext)
                .permission(Permission.CAMERA,Permission.STORAGE)//多个权限
                .callback(permissionListener)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(mContext,rationale).show();
                    }
                }).start();


        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        //调用相机
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //下面这句指定调用相机拍照后的照片存储的路径
        imgPath = path+System.currentTimeMillis()+".jpg";
        File file = new File(path);
        if (!file.mkdirs()) {
            file.mkdirs();
        }
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgPath)));
        startActivityForResult(takeIntent, REQUEST_CODE_CAMERA);
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            Toast.makeText(mContext, "申请成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(mContext, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(FaceRecognitionActivity.this, REQUEST_CODE_SETTING).show();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAMERA){
            LemonBubble.showRoundProgress(mContext,"获取中...");
            handler.sendEmptyMessage(LUBANYASUO);
        }
    }

    private float eyesDistance(FaceDetector.Face [] list){
        for (int i=0;i<list.length;i++){
            FaceDetector.Face face = list[i];
            if (face != null){
                PointF pointF = new PointF();
                face.getMidPoint(pointF);
                RectF rectF = new RectF();
                rectF.left = pointF.x - face.eyesDistance() / 2;
                rectF.right = pointF.x + face.eyesDistance() / 2;
                rectF.top = pointF.y - face.eyesDistance() / 2;
                rectF.bottom = pointF.y + face.eyesDistance() / 2;

            }
        }
        return 0.02f;
    }

    private void doPhotoPrint(){
        PrintHelper helper = new PrintHelper(mContext);
        helper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.zzh);
        helper.printBitmap("test print",bitmap);
    }
}
