package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import java.io.File;
import java.io.IOException;

/**
 * 描述：实现歌曲边下边播功能
 * 作者：zzh
 * time:2019/05/09
 * 参考：MP3歌曲下载网址：http://music.ifkdy.com/
 */
public class DownContinueActivity extends Activity {

    private Context mContext = DownContinueActivity.this;
    private Activity mActivity = DownContinueActivity.this;
    private MediaPlayer player;
    private DownloadManager manager;
    private long taskId = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_downcontinue);

        initView();
    }

    private void initView() {
        Info.setActionBar(mActivity,R.id.headerLayout,"边下边播");

        Button btn_start_radio = findViewById(R.id.btn_start_radio);
        Button btn_stop_radio = findViewById(R.id.btn_stop_radio);

        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setLooping(true);
        btn_start_radio.setOnClickListener(view -> downMusic());

        btn_stop_radio.setOnClickListener(view -> {
            player.reset();
            try {
                player.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+"/1.mp3");
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 描述：下载音乐
     * 作者：zzh
     */
    private void downMusic(){
        String url = "http://dl.stream.qqmusic.qq.com/M500002G0sJY2wThyx.mp3?vkey=F414004281C02027E30E76FB568D16EC2676F17DF3F1A682F828B9C0255E65C6B8EDEB327FA773ECDF754FA26774809C644680C3A3E149E8&guid=5150825362&fromtag=1";
        String fileName = "喜欢你.mp3";

        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + fileName;

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        File file = new File(downloadPath);
        if (file.exists()){//已存在
            try {
                player.reset();
                player.setDataSource(downloadPath);
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
                Info.showToast(mContext,"1："+e.getMessage(),false);
            }
        }else {
            try {
                player.reset();
                player.setDataSource(url);
                player.prepare();
                player.start();
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
                Info.showToast(mContext,"2："+e.getMessage(),false);
            }
        }

        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir("/download",fileName);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);//仅wifi能下载
        request.setVisibleInDownloadsUi(false);
        request.setTitle("下载");
        request.setDescription("歌曲正在下载中...");
        //获取下载管理器
        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        assert manager != null;
        taskId = manager.enqueue(request);
        registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

//        OkHttpUtils.post().url(url)
//                .build()
//                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "春天里.mp3") {
//                    @Override
//                    public void inProgress(float progress, long total, int id) {
//                        super.inProgress(progress, total, id);
//                        File file = new File(path);
//                        if (file.exists()) {
//                            try {
//                                player.reset();
//                                player.setDataSource(path);
//                                player.prepare();
//                                player.start();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                                Info.showToast(mContext,"1："+e.getMessage(),false);
//                            }
//                        }else {
//                            try {
//                                player.reset();
//                                player.setDataSource(url);
//                                player.prepare();
//                                player.start();
//                            } catch (IOException | IllegalArgumentException e) {
//                                e.printStackTrace();
//                                Info.showToast(mContext,"2："+e.getMessage(),false);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Info.showToast(mContext,"下载失败："+e,false);
//                    }
//
//                    @Override
//                    public void onResponse(File response, int id) {
//                        Info.showToast(mContext,"下载完成！"+response.toString(),true);
//                    }
//                });
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                checkDownLoadStatus();//检查下载状态
            }
        }
    };

    private void checkDownLoadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(taskId);//筛选下载任务
        Cursor cursor = manager.query(query);
        if (cursor.moveToFirst()){
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:

                case DownloadManager.STATUS_PENDING:

                case DownloadManager.STATUS_RUNNING:
                    break;

                case DownloadManager.STATUS_SUCCESSFUL:
                    break;
                case DownloadManager.STATUS_FAILED:
                    break;
            }
        }
    }
}
