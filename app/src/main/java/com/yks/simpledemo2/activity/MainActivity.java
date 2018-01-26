package com.yks.simpledemo2.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yks.simpledemo2.R;

public class MainActivity extends Activity {

    //危险权限（运行时权限）
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int PERMISSION_REQUEST_CODE = 0;
    private long exitTime = 0;//退出时间
    //所有activity_main里面的id集合
    private int [] ids = new int[]{R.id.btn_float_window};
    //                              桌面悬浮框
    //所有点击id对应要跳转的页面
    private Class [] classes = new Class[]{FloatWindowTest.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 实例化和点击跳转
     */
    private void initData(){
        for (int i=0;i<ids.length;i++){
            Button button = (Button)findViewById(ids[i]);
            final int finalPosition = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,"我进来",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,classes[finalPosition]);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //// TODO: 2016/11/28 权限检测，只有在全部权限都同意的情况下才能进入程序，有一个不同意的话，则继续弹出这个权限的对话框
        PackageManager pkm = this.getPackageManager();//包管理器
        String pkName = this.getPackageName();//应用包名
        int len = PERMISSIONS.length;
        //所有权限是否全部允许
        boolean[] permissions = new boolean[len];
        for (int i = 0; i < len; i++){
            permissions[i] =   (PackageManager.PERMISSION_GRANTED
                    == pkm.checkPermission(PERMISSIONS[i], pkName));
        }
        boolean isAllPermissionAllowed = true;
        int index = 0;
        String[] tempArray = new String[len];
        for (int j = 0 ; j < len ; j++){
            //将不允许的权限放入这个临时的数组里面
            if (!permissions[j]){
                tempArray[index] = PERMISSIONS[j];
                index ++;
                isAllPermissionAllowed = false;
            }
        }
        //得到所有未允许的权限，再次请求
        String[] array = new String[index];
        for (int k = 0 ; k < index ; k++){
            array[k] = tempArray[k];
        }
        if (isAllPermissionAllowed) {// 这里才开始真的干活的
            initData();
        } else {
            ActivityCompat.requestPermissions(this, array, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - exitTime) > 2000){
                Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
