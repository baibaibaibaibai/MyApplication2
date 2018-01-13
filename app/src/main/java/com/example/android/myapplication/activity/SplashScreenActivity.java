package com.example.android.myapplication.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.android.myapplication.R;
import com.example.android.myapplication.base.BaseActivity;

/**
 * 闪屏页
 */
public class SplashScreenActivity extends BaseActivity {

    private Dialog mDialog;
    private boolean flage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        // 闪屏的核心代码
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences flageSp = getSharedPreferences("first_login",
                        Activity.MODE_PRIVATE);
                flage = flageSp.getBoolean("first_login", false);

                startIntent();

            }
        }, 3000); // 启动动画持续3秒钟
    }


    private void startIntent() {
        if (flage) {
            Intent intent = new Intent(SplashScreenActivity.this,
                    MainActivity.class); // 从启动动画ui跳转到主ui
            startActivity(intent);
            SplashScreenActivity.this.finish(); // 结束启动动画界面

        }
        // 第一次登录
        else {
            Intent intent = new Intent(SplashScreenActivity.this,
                    MainActivity.class); // 从启动动画ui跳转到主ui
            startActivity(intent);
            SplashScreenActivity.this.finish(); // 结束启动动画界面
        }

    }

}
