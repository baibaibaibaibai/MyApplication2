package com.example.android.myapplication.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.myapplication.R;
import com.example.android.myapplication.views.TitleBar;

import static android.view.View.GONE;

/**
 *
 */

public class FindFragment extends Fragment {
    private static final String TAG = "TestFragment";
    private TitleBar titleBar;
    private WebView mWebview;
    private WebSettings mWebSettings;

    public static FindFragment newInstance(String s) {
        FindFragment homeFragment = new FindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uid", s);
        homeFragment.setArguments(bundle);
        return homeFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        initTitleBar(view);
        mWebview = (WebView) view.findViewById(R.id.webView);

        mWebSettings = mWebview.getSettings();

        mWebview.loadUrl("http://www.baidu.com/");


        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题在这里");

            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });


        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {


            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {


            }
        });


        return view;

    }

    private void initTitleBar(View view) {
        this.titleBar = ((TitleBar) view.findViewById(R.id.title));
        this.titleBar.setTitle("发现");
        this.titleBar.setLeftButtonVisibility(GONE);
    }

    //销毁Webview
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();

        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();

    }

//    //点击返回上一页面而不是退出浏览器
//    @Override
//    public  boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
//            mWebview.goBack();
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }


}
