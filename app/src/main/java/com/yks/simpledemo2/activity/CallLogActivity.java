package com.yks.simpledemo2.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.CallLogs;
import com.yks.simpledemo2.tools.CommonAdapter;
import com.yks.simpledemo2.tools.Info;
import com.yks.simpledemo2.tools.ViewHolder;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：读取手机通话记录
 * 作者：zzh
 * time:2018/10/25
 *  参考：https://www.jb51.net/article/94309.htm
 */

public class CallLogActivity extends Activity {

    private ListView list_calllog;
    private List<CallLogs> mList = new ArrayList<>();
    private final int GETCALLLOGS = 0;
    private final int GETCALLLOGSSUCCESS = 1;
    private final int GETCALLLOGSFAIL = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calllog);

        handler.sendEmptyMessage(GETCALLLOGS);
        initView();
    }

    private void initData() {
        LemonBubble.showRoundProgress(CallLogActivity.this, "获取中...");
        ContentResolver resolver = getContentResolver();
        /**
         * @param uri 需要查询的URI，（这个URI是ContentProvider提供的）
         * @param projection 需要查询的字段
         * @param selection sql语句where之后的语句
         * @param selectionArgs ?占位符代表的数据
         * @param sortOrder 排序方式
         *
         */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            handler.sendEmptyMessage(GETCALLLOGSFAIL);
            return;
        }
        Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, // 查询通话记录的URI
                new String[]{CallLog.Calls.CACHED_NAME// 通话记录的联系人
                        , CallLog.Calls.NUMBER// 通话记录的电话号码
                        , CallLog.Calls.DATE// 通话记录的日期
                        , CallLog.Calls.DURATION// 通话时长
                        , CallLog.Calls.TYPE}// 通话类型
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        while (cursor.moveToNext()) {
            //联系人名称
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            if (name == null) {
                name = "未备注联系人";
            }
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            //通话类型
            String types = "";
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    types = "接入";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    types = "呼出";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    types = "未接";
                    break;
                default:
                    break;
            }
            //通话日期
            long dateLog = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLog));
            //通话时长
            String durations = "";
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            int sec = duration % 60;
            int min = duration / 60;
            if (sec > 0){
                if (min > 0){
                    durations = min + "分" + sec + "秒";
                }else {
                    durations = sec + "秒";
                }
            }
            CallLogs logs = new CallLogs(name, number, date, durations, types);
            mList.add(logs);
        }

        handler.sendEmptyMessage(GETCALLLOGSSUCCESS);
    }

    private void initView() {
        Info.setActionBar(CallLogActivity.this,R.id.headerLayout,"通话记录");

        list_calllog = findViewById(R.id.list_calllog);
        list_calllog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri uri = Uri.parse("tel:" + mList.get(i).getNumber());
                intent.setData(uri);
                if (ActivityCompat.checkSelfPermission(CallLogActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    LemonBubble.showError(CallLogActivity.this,"没有拨打电话的权限!");
                    return;
                }
                startActivity(intent);
            }
        });
    }

    private class CallLogAdapter extends CommonAdapter<CallLogs>{

        private CallLogAdapter(Context context, List<CallLogs> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, CallLogs item) {
            TextView name = helper.getView(R.id.txt_item_calllog_name);//联系人名称
            TextView type = helper.getView(R.id.txt_item_calllog_type);//通话类型
            TextView number = helper.getView(R.id.txt_item_calllog_number);//电话号码
            TextView date = helper.getView(R.id.txt_item_calllog_time);//通话日期
            TextView duration = helper.getView(R.id.txt_item_calllog_long);//通话时长

            name.setText(item.getUserName());
            type.setText(item.getType());
            number.setText(item.getNumber());
            date.setText(item.getDate());
            duration.setText(item.getDuration());
        }
    }

    private final Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GETCALLLOGS){
                initData();//获取通话记录
            }else if (msg.what == GETCALLLOGSSUCCESS){
                LemonBubble.hide();
                list_calllog.setAdapter(new CallLogAdapter(CallLogActivity.this,mList,R.layout.item_calllogs));
            }else if (msg.what == GETCALLLOGSFAIL){
                LemonBubble.showError(CallLogActivity.this,"请先同意读取通话记录权限",2000);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessage(GETCALLLOGS);
    }
}
