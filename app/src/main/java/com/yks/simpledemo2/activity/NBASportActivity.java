package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.NBASport;
import com.yks.simpledemo2.tools.CommonAdapter;
import com.yks.simpledemo2.tools.Info;
import com.yks.simpledemo2.tools.ViewHolder;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：NBA赛事（通过接口获取数据）
 * 作者：zzh
 * time:2018/10/15
 * 参考：https://www.avatardata.cn/Docs/Api/51649e51-4842-4c62-b12b-680f9ab867fe
 */

public class NBASportActivity extends Activity implements View.OnClickListener{
    private Activity mActivity = NBASportActivity.this;
    private GridView list_nba_daily;
    private ImageView iv_nba_refresh;
    private List<NBASport> mList = new ArrayList<>();
    private final int GETSPORTSUCCESS = 0;
    private final int GETSPORTFAIL = 1;
    private boolean isProgressShow = false;//加载进度条是否在显示
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nba_sport);

        initView();
        initData();
    }

    private void initView() {
        Info.setActionBar(mActivity,R.id.headerLayout,"NBA每日赛程结果");

        list_nba_daily = findViewById(R.id.list_nba_daily);
        iv_nba_refresh = findViewById(R.id.iv_nba_refresh);
        iv_nba_refresh.setOnClickListener(this);
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GETSPORTSUCCESS){//获取nba每日赛果成功
                LemonBubble.hide();
                isProgressShow = false;
                list_nba_daily.setAdapter(new SportAdapter(NBASportActivity.this,mList,R.layout.item_nba_sport));
            }else if (msg.what == GETSPORTFAIL){//获取nba每日赛果失败
                Bundle bundle = msg.getData();
                LemonBubble.showError(NBASportActivity.this,bundle.getString("error"),1500);
                isProgressShow = false;
            }
        }
    };

    @Override
    public void onClick(View view) {
        if (view == iv_nba_refresh){
            mList.clear();
            initData();
        }
    }

    private class SportAdapter extends CommonAdapter<NBASport>{

        SportAdapter(Context context, List<NBASport> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, NBASport item) {
            ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
            ImageView iv_custom_logo = helper.getView(R.id.iv_custom_logo);
            TextView homename = helper.getView(R.id.txt_item_nba_homename);
            TextView customname = helper.getView(R.id.txt_item_nba_customname);
            TextView status = helper.getView(R.id.txt_item_nbastatus);
            TextView score = helper.getView(R.id.txt_item_nba_score);
            TextView gameTime = helper.getView(R.id.txt_item_nba_time);

            Glide.with(NBASportActivity.this).load(item.getHomeLogo()).into(iv_home_logo);
            Glide.with(NBASportActivity.this).load(item.getCustomLogo()).into(iv_custom_logo);

            homename.setText(item.getHomeTeam());
            customname.setText(item.getCustomTeam());
            score.setText(item.getScore());
            gameTime.setText(item.getTime());
            String gameStatus = item.getStatus();
            if (gameStatus.equals("0")){
                status.setText("未开赛");
            }else if (gameStatus.equals("1")){
                status.setText("直播中");
            }else {
                status.setText("已结束");
            }
        }
    }

    private void initData(){
        LemonBubble.showRoundProgress(NBASportActivity.this,"加载中...");
        isProgressShow = true;
        String url = "https://api.avatardata.cn/Nba/NomalRace?key=5f9d54d09cc5448f966f9c326d85ccbd&dtype=JSON&format=true";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                String success = object.getString("error_code");
                if (success.equals("0")){//成功
                    JSONObject object5 = object.getJSONObject("result");
                    JSONArray array = object5.optJSONArray("list");
                    for (int i=0;i<array.length();i++){
                        JSONObject object1 = array.getJSONObject(i);
                        JSONArray array1 = object1.getJSONArray("tr");
                        for (int j=0;j<array1.length();j++){
                            JSONObject object2 = array1.getJSONObject(j);
                            NBASport sport = new NBASport(object2.getString("player1"),object2.getString("player2"),object2.getString("score"),
                                    object2.getString("player1logo"),object2.getString("player2logo"),object2.getString("time"),
                                    object2.getString("status"),object2.getString("m_link1url"));
                            mList.add(sport);
                        }
                    }

                    handler.sendEmptyMessage(GETSPORTSUCCESS);
                }else {
                    //失败
                    Message message = new Message();
                    message.what = GETSPORTFAIL;
                    Bundle bundle = new Bundle();
                    bundle.putString("error",object.getString("reason"));
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Message message = new Message();
                message.what = GETSPORTFAIL;
                Bundle bundle = new Bundle();
                bundle.putString("error",e.getMessage());
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }, error -> {//失败
            Message message = new Message();
            message.what = GETSPORTFAIL;
            Bundle bundle = new Bundle();
            bundle.putString("error",error.getMessage());
            message.setData(bundle);
            handler.sendMessage(message);
        });
        MainActivity.mQueue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        handler.removeCallbacksAndMessages(null);
        if (mList != null && mList.size() != 0){
            mList.clear();
            mList = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isProgressShow){
                LemonBubble.forceHide();
            }else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
