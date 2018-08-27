package com.yks.simpledemo2.tools;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by admin on 2018/1/26.
 */

public class NoAdWebViewClient extends WebViewClient {

    private final Context context;
    private final String homeUrl;
    private String [] adUrlString = {"ubmcmm.baidustatic.com","cpro2.baidustatic.com","cpro.baidustatic.com","s.lianmeng.360.cn",
            "nsclick.baidu.com","pos.baidu.com","cbjs.baidu.com","cpro.baidu.com","images.sohu.com/cs/jsfile/js/c.js","union.sogou.com/",
            "sogou.com/","a.baidu.com","c.baidu.com"};

    public NoAdWebViewClient(Context context, String homeUrl){
        this.context = context;
        this.homeUrl = homeUrl;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        url = url.toLowerCase();
        if (!url.contains(homeUrl)){
            if (!hasAd(url)){
                return super.shouldInterceptRequest(view,url);
            }
            return new WebResourceResponse(null,null,null);
        }
        return super.shouldInterceptRequest(view, url);
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
