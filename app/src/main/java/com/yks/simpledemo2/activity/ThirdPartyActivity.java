package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import java.io.File;

/**
 * 描述：调用第三方应用
 * 作者：zzh
 * time:2019/07/04
 */
public class ThirdPartyActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_third_party);
        Info.setActionBar(this,R.id.headerLayout,"调用第三方应用");
    }

    public void openQQ(View view){
        PackageManager manager = getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage("com.tencent.mobileqq");
        startActivity(intent);
    }

    public void openQQSplash(View view){
        Intent intent = new Intent();
        ComponentName name = new ComponentName("com.tencent.mobileqq","com.tencent.mobileqq.activity.SplashActivity");
        intent.setComponent(name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openGoogleSearch(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY,"钟志华");
        startActivity(intent);
    }

    public void openWebsite(View view){
        Uri uri = Uri.parse("http://www.zhibo8.cc");
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void openMap(View view){
        Uri uri = Uri.parse("geo:36.899533,66.036476");
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void callPhone(View view){
        Uri uri = Uri.parse("tel:1008611");
        startActivity(new Intent(Intent.ACTION_DIAL,uri));
    }

    public void sendMessage(View view){
        Uri uri = Uri.parse("smsto:15083336958");
        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
        intent.putExtra("sms_body","宝宝，想你了，今天还没喝水呢，快喝一点");
        startActivity(intent);
    }

    public void playMedia(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String path = Environment.getExternalStorageDirectory().getPath()+"/demo.mp4";
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri,"video/*");
        startActivity(intent);
    }

    public void openSetting(View view){
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    public void openCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,111);
    }

    public void openGallery(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,112);
    }

    public void openRecord(View view){
        startActivity(new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION));
    }
}
