package com.yks.simpledemo2.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.yks.simpledemo2.R;

/**
 * 描述：
 * 作者：
 * time:2018/10/09
 */

public class TabHostActivity extends ActivityGroup {

    private TabHost tabHost;
    private Class activitys[] = {TaskListActivity.class,ScanPickOutActivity.class,PickedListActivity.class,PickingActivity.class};
    private String lables[] = {"任务列表","扫描拣货","已拣货列表","待拣货列表"};
    private int icons[] = {R.mipmap.homes,R.mipmap.search,R.mipmap.notice,R.mipmap.setting};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tabhost);

        initTabView();
    }

    private void initTabView() {
        this.tabHost = findViewById(R.id.mytabhost);
        tabHost.setup(this.getLocalActivityManager());
        for (int i=0;i<activitys.length;i++){
            View view = View.inflate(this,R.layout.tab_layout,null);
            LinearLayout layout = view.findViewById(R.id.layout);
            ImageView src = view.findViewById(R.id.iv_tabicon);
            TextView title = view.findViewById(R.id.txt_tabname);

            src.setImageResource(icons[i]);
            title.setText(lables[i]);

            Intent intent = new Intent(TabHostActivity.this,activitys[i]);
            TabHost.TabSpec spec = tabHost.newTabSpec(lables[i]).setIndicator(view).setContent(intent);
            tabHost.addTab(spec);
        }
    }
}
