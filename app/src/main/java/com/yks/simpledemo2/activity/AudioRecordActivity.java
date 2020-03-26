package com.yks.simpledemo2.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jia.jspermission.listener.JsPermissionListener;
import com.jia.jspermission.utils.JsPermission;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;
import com.yks.simpledemo2.tools.ToastUtils;
import com.yks.simpledemo2.widget.DashBoardView;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 描述：录音及分贝计算
 * 作者：zzh
 * time:2019/07/05
 */
public class AudioRecordActivity extends Activity implements View.OnClickListener, JsPermissionListener {
    private Context mContext = AudioRecordActivity.this;
    private Activity mActivity = AudioRecordActivity.this;

    private TextView txt_record_count;
    private RelativeLayout btn_record,btn_stop;
    private ImageView iv_record,iv_stop;
    private DashBoardView view_dbv;

    private boolean isRecord = false;//是否正在录制
    private AudioRecord audioRecord;
    /**
     * 采样率，现在能够保证在所有设备上使用的采样率是44100Hz, 但是其他的采样率（22050, 16000, 11025）在一些设备上也可以使用。
     */
    private final int SAMPLE_RATE_INHZ = 44100;
    /**
     * 声道数。CHANNEL_IN_MONO and CHANNEL_IN_STEREO. 其中CHANNEL_IN_MONO是可以保证在所有设备能够使用的。
     */
    private final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    /**
     * 返回的音频数据的格式。 ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, and ENCODING_PCM_FLOAT.
     */
    private final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private final int UPDATETIMES = 0;//更新时间
    private final int UPDATERATES = 1;//更新声音分贝
    private String path;
    private int minBufferSize;
    private long startTime;//记录开始时间
    private MyHandler handler;

    private MediaRecorder mMediaRecorder;
    private MediaPlayer player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_audio_record);

        handler = new MyHandler(mActivity);
        initView();
    }

    private void initView() {
        Info.setActionBar(mActivity,R.id.headerLayout,"录音及分贝计算");

        txt_record_count = findViewById(R.id.txt_record_count);
        btn_record = findViewById(R.id.btn_record);
        btn_stop = findViewById(R.id.btn_stop);
        iv_record = findViewById(R.id.iv_record);
        iv_stop = findViewById(R.id.iv_stop);
        view_dbv = findViewById(R.id.view_dbv);

        btn_record.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_record){
            JsPermission.with(mActivity)
                    .requestCode(121)
                    .permission(Manifest.permission.RECORD_AUDIO)
                    .callBack(this)
                    .send();
        }else if (view == btn_stop){
            if (isRecord) {
                iv_record.setImageResource(R.mipmap.recording);
                iv_stop.setImageResource(R.mipmap.stoped);
                finishRecord();
//                stopAmr();
            }
        }
    }

    @Override
    public void onPermit(int i, String... strings) {
        if (!isRecord) {
            iv_record.setImageResource(R.mipmap.recorded);
            iv_stop.setImageResource(R.mipmap.stopping);
                startRecord();
//            recordAmr();
        }
    }

    @Override
    public void onCancel(int i, String... strings) {
        Info.showToast(mContext,"请求的权限已被拒绝或取消，请重试！",false);
        Info.playRingtone(mContext,false);
    }

    private class MyHandler extends Handler {
        final WeakReference<Activity> mWeakReference;

        MyHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mWeakReference.get() != null) {
                if (msg.what == UPDATETIMES) {//更新录音时间
                    Bundle bundle = msg.getData();
                    txt_record_count.setText(bundle.getString("msg"));
                }else if (msg.what == UPDATERATES){//更新声音分贝
                    Bundle bundle = msg.getData();
                    view_dbv.cgangePer(Float.parseFloat(bundle.getString("msg")));
                }
            }
        }
    }
    /**
     * 描述：开始录音
     * 作者：zzh
     */
    private void startRecord(){
        ToastUtils.show(mContext,"开始录音");
        startTime = System.currentTimeMillis();
        minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_INHZ,CHANNEL_CONFIG,AUDIO_FORMAT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,SAMPLE_RATE_INHZ,CHANNEL_CONFIG,AUDIO_FORMAT,minBufferSize);
        final byte[] data = new byte[minBufferSize];
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/MyRecord",System.currentTimeMillis()+".pcm");
        if (!file.mkdirs()){
            file.mkdirs();
        }
        if (file.exists()){
            file.delete();
        }
        path = file.getAbsolutePath();
        audioRecord.startRecording();
        isRecord = true;
        Object object = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream os = null;
                try {
                    os = new FileOutputStream(file);
//                    short[] buffer = new short[minBufferSize];
                    if (null != os){
                        while (isRecord){
                            int read = audioRecord.read(data,0,minBufferSize);
                            //如果读取音频数据没有出现错误，就将数据写入到文件
                            if (AudioRecord.ERROR_INVALID_OPERATION != read){
                                os.write(data);
                            }
                            //todo 计算时间
                            long currentTime = System.currentTimeMillis();
                            double second = (currentTime - startTime) / 1000;
                            int time = (int) Math.floor(second);
                            String times;

                            if (time < 10){
                                times = "00:0"+time;
                            }else if (time > 10 && time < 60){
                                times = "00:"+time;
                            }else{
                                int minute = (int) Math.floor((time/60));
                                int self = time % 60;
                                if (minute < 10){
                                    times = "0"+minute+":"+self;
                                }else {
                                    times = minute+":"+self;
                                }
                            }
                            sendMessage(UPDATETIMES,times);
                            //计算分贝
                            int v = 0;
                            for (int i=0;i<data.length;i++){
                                v += data[i]*data[i];
                            }
                            //平方和除以数据总长度，得到音量大小
                            double mean = v / (double)read;
                            double volume = 10 * Math.log10(mean);
                            Log.d("myvolume","分贝值："+volume);
                            sendMessage(UPDATERATES,volume+"");
//                            synchronized (object){
//                                try {
//                                    object.wait(100);
//                                }catch (InterruptedException e){
//                                    e.printStackTrace();
//                                }
//                            }
                        }

                        os.close();
                    }
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 描述：完成录音
     * 作者：zzh
     */
    private void finishRecord(){
        isRecord = false;
        if (audioRecord != null){
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            LemonHello.getSuccessHello("成功","录制成功，音频文件存放于："+path).addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                @Override
                public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                    pcmToWav();
                    lemonHelloView.hide();
                }
            })).show(mContext);
        }
    }

    /**
     * 描述：将pcm格式转为wav并播放
     * 作者：zzh
     */
    private void pcmToWav(){
        String pcmPath = path;
        String wavPath = pcmPath.replace("pcm","wav");
        converPcmToWav(pcmPath,wavPath);
        try {
            player = new MediaPlayer();
            player.setDataSource(wavPath);
            player.prepare();
            player.start();
            File file = new File(path);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述：将pcm个格式的音频转为wav格式的音频
     * 作者：zzh
     * @param pcmPath pcm文件存储路径
     * @param wavPath 要转的wav完成后要存储的路径
     */
    private void converPcmToWav(String pcmPath,String wavPath){

        FileInputStream in = null;
        FileOutputStream out = null;
        long sampleRate = SAMPLE_RATE_INHZ;
        int channels = CHANNEL_CONFIG == AudioFormat.CHANNEL_IN_MONO ? 1 : 2;
        long byteRate = 16 * SAMPLE_RATE_INHZ * CHANNEL_CONFIG / 8;
        byte[] data = new byte[minBufferSize];
        try {
            //采样率
//            long byteRate = sampleRate * channels * bitNum / 8;
            in = new FileInputStream(pcmPath);
            out = new FileOutputStream(wavPath);
            //pcm文件的大小
            long totalAudioLen = in.getChannel().size();
            long totalDataLen = totalAudioLen  + 36;
            writeWaveFileHeader(out,totalAudioLen ,totalDataLen,sampleRate,channels,byteRate);
            int length = 0;
            while ((length = in.read(data)) > 0){
                out.write(data,0,length);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 加入wav文件头
     */
    private void writeWaveFileHeader(FileOutputStream out, long totalAudioLen, long totalDataLen, long longSampleRate, int channels, long byteRate)
            throws IOException {
        byte[] header = new byte[44];
        // RIFF/WAVE header
        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        //WAVE
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        // 'fmt ' chunk
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        // 4 bytes: size of 'fmt ' chunk
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        // format = 1
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        // block align
        header[32] = (byte) (2 * 16 / 8);
        header[33] = 0;
        // bits per sample
        header[34] = 16;
        header[35] = 0;
        //data
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }

    /**
     * 描述：发送handler消息
     * 作者：zzh
     * @param id 需要进入到的handler
     * @param msg 传递的消息
     */
    private void sendMessage(int id,String msg){
        Bundle bundle = new Bundle();
        bundle.putString("msg",msg);
        Message message = new Message();
        message.what = id;
        message.setData(bundle);
        handler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        handler.removeCallbacksAndMessages(null);
        if (mMediaRecorder != null){
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        if (player != null){
            player.stop();
            player.reset();
            player.release();
            player = null;
        }
    }

    private void recordAmr(){
        if (mMediaRecorder == null){
            mMediaRecorder = new MediaRecorder();
        }

        try {
            Toast.makeText(mContext,"开始录音",Toast.LENGTH_SHORT).show();
            path = Environment.getExternalStorageDirectory().getPath()+"/MyRecord/"+System.currentTimeMillis()+".amr";
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//设置音频文件编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置输出文件格式
            mMediaRecorder.setOutputFile(path);
            mMediaRecorder.setMaxDuration(60*1000);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            startTime = System.currentTimeMillis();
            isRecord = true;
            updateMicStatus();
        }catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long stopAmr(){
        if (mMediaRecorder == null){
            return 0L;
        }
        long endTime = System.currentTimeMillis();
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
        isRecord = false;
        LemonHello.getSuccessHello("成功","录音成功，存放路径："+path).addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
            @Override
            public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                try {
                    MediaPlayer player = new MediaPlayer();
                    player.setDataSource(path);
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                lemonHelloView.hide();
            }
        })).show(mContext);
        return endTime - startTime;
    }

    private void updateMicStatus() {
        if (mMediaRecorder != null){
            handler.postDelayed(mUpdateMicStatusTimer,100);
        }
    }

    private Runnable mUpdateMicStatusTimer = new Runnable() {
        @Override
        public void run() {
            updateMicStatus();
            //todo 计算时间
            long currentTime = System.currentTimeMillis();
            double second = (currentTime - startTime) / 1000;
            int time = (int) Math.floor(second);
            String times;

            if (time < 10){
                times = "00:0"+time;
            }else if (time > 10 && time < 60){
                times = "00:"+time;
            }else{
                int minute = (int) Math.floor((time/60));
                int self = time % 60;
                if (minute < 10){
                    times = "0"+minute+":"+self;
                }else {
                    times = minute+":"+self;
                }
            }
            sendMessage(UPDATETIMES,times);

            if (mMediaRecorder != null) {
                double ratio = mMediaRecorder.getMaxAmplitude();
                if (ratio > 1) {
                    ratio = 20 * Math.log10(ratio);
                    int db = (int) Math.floor(ratio);
                    Log.d("myvolume", "分贝值：" + db);
                    sendMessage(UPDATERATES, db + "");
                }
            }
        }
    };
}
