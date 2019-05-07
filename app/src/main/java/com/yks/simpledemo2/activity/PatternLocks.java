package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import java.util.List;

/**
 * 描述：滑动解锁控件
 * 作者：zzh
 * 参考网址:https://github.com/aritraroy/PatternLockView
 * Created by admin on 2018/8/21.
 */

public class PatternLocks extends Activity {

    private PatternLockView view_pattern_lock;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_patternlock);

        initView();
    }

    private void initView() {
        Info.setActionBar(PatternLocks.this,R.id.headerLayout,"滑动解锁");

        view_pattern_lock = findViewById(R.id.view_pattern_lock);
        view_pattern_lock.addPatternLockListener(listner);
    }

    private PatternLockViewListener listner = new PatternLockViewListener() {
            @Override
            public void onStarted() {
                Info.showLog("pattern","开始了");
//                Info.showToast(PatternLocks.this,"开始了：",true);
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
                Info.showLog("pattern","开始滑动了："+getClass().getName()+ "。Pattern progress: " +
                        PatternLockUtils.patternToString(view_pattern_lock, progressPattern));
//                Info.showToast(PatternLocks.this,"开始滑动了："+getClass().getName()+ "。Pattern progress: " +
//                        PatternLockUtils.patternToString(view_pattern_lock, progressPattern),true);
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                Info.showLog("pattern","完成了："+PatternLockUtils.patternToString(view_pattern_lock, pattern));
                Info.showToast(PatternLocks.this,"结果："+PatternLockUtils.patternToString(view_pattern_lock, pattern),true);
            }

            @Override
            public void onCleared() {
                Info.showLog("pattern","清除了");
//                Info.showToast(PatternLocks.this,"清除了",true);
            }
        };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view_pattern_lock.removePatternLockListener(listner);
    }
}
