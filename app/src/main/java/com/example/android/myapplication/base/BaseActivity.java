package com.example.android.myapplication.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 基础文件配置
 */

public abstract class BaseActivity extends Activity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            ScreenManager.getScreenManager().pushActivity(this);

        }


        @Override
        protected void onResume() {
            super.onResume();
            ScreenManager.getScreenManager().reStart();

        }


        @Override
        protected void onStop() {

            super.onStop();
        }

        // bug 2816 add
        @Override
        protected void onDestroy() {
            ScreenManager.getScreenManager().popActivity(this);
            super.onDestroy();
        }



}
