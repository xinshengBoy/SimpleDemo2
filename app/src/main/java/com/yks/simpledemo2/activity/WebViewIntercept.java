package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.widget.MyActionBar;

/**
 * 描述：webview拦截特定的信息(如广告等)
 * http://blog.csdn.net/lcwoooo/article/details/74474640
 * Created by admin on 2018/1/26.
 */

public class WebViewIntercept extends Activity {

    private ProgressBar pb_loading;
    private String [] adUrlString = {"ubmcmm.baidustatic.com","cpro2.baidustatic.com","cpro.baidustatic.com","s.lianmeng.360.cn",
            "nsclick.baidu.com","pos.baidu.com","cbjs.baidu.com","cpro.baidu.com","images.sohu.com/cs/jsfile/js/c.js","union.sogou.com/",
            "sogou.com/","a.baidu.com","c.baidu.com","static.zongheng.com/upload/","https://yun.tuiant.com/", "http://duokoo.baidu.com/game/"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intercept_webview);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "webview拦截");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);
        initView();
    }

    private void initView() {
        pb_loading = findViewById(R.id.pb_loading);
        WebView webView = findViewById(R.id.webview_intercept);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                pb_loading.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                url = url.toLowerCase();
                if (!hasAd(url)){
                    return super.shouldInterceptRequest(view,url);
                }
                return new WebResourceResponse(null,null,null);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pb_loading.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl("https://m.zongheng.com/h5/chapter/list?bookid=390021&fr=shenma_freexx&v=1494355519748&k=c12dfd");
    }

    private boolean hasAd(String url){
        for (String urls : adUrlString){
            if (url.contains(urls)){
                return true;
            }
        }
        return  false;
    }
}
