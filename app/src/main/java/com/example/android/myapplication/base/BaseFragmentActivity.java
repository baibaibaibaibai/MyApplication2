package com.example.android.myapplication.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by baidan on 2017/12/13.
 */

public class BaseFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenManager.getScreenManager().pushActivity(this);

    }

    protected void onResume() {
        super.onResume();
        ScreenManager.getScreenManager().reStart();

    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        ScreenManager.getScreenManager().popActivity(this);
        super.onDestroy();
    }
}
