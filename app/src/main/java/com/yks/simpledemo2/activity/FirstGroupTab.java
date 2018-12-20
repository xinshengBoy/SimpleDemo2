package com.yks.simpledemo2.activity;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yks.simpledemo2.R;

/**
 * 描述：
 * 作者：
 * time:2018/11/06
 */

public class FirstGroupTab extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first_tab);

        Button btn_firsttabs = findViewById(R.id.btn_firsttabs);
        btn_firsttabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabToActivity.tabHost.setCurrentTab(1);
            }
        });
    }
}
