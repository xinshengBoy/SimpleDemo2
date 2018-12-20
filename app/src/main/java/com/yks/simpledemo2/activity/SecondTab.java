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

public class SecondTab extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second_tab);

        Button btn_secondtabs = findViewById(R.id.btn_secondtabs);
        btn_secondtabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(this,FirstGroupTab.class).addFlags()
                TabToActivity.tabHost.setCurrentTab(0);
            }
        });
    }
}
