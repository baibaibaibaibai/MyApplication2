package com.example.android.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.android.myapplication.R;
import com.example.android.myapplication.base.BaseActivity;
import com.example.android.myapplication.base.Constant;
import com.example.android.myapplication.utils.DialogHelperUtil;
import com.example.android.myapplication.utils.UpdateManager;
import com.example.android.myapplication.views.TitleBar;

/**
 * 设置
 */

public class SettingActivity extends BaseActivity {
    private TitleBar titleBar;
    private String changeLog = "";
    private String fileUrl = "";
    private String forceUpdate = "";

    private String versionName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initTitleBar();

    }

    private void initTitleBar() {
        this.titleBar = ((TitleBar) findViewById(R.id.title));
        this.titleBar.setTitle("设置");

    }

    /**
     * 检查新版本
     */
    public void UpdateOnclick(View view) {

//        if (this.forceUpdate.equals("0")) {
        new UpdateManager(this).checkUpdate(this.versionName, this.versionName, this.fileUrl, this.changeLog, this.forceUpdate, false);
        return;
//        }
//        new UpdateManager(this).checkUpdate(this.versionName, this.versionName, this.fileUrl, this.changeLog, this.forceUpdate, false);

    }

    /**
     * 退出登录
     */
    public void ExitOnclick(View view) {
        DialogHelperUtil.show(this, "提示", "确认要退出吗？", "取消", "确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定
                SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();// 获取编辑器

                editor.putString(Constant.USER_INFO_TEL, "");
                editor.remove(Constant.USER_INFO_NAME);
                editor.remove(Constant.USER_INFO_User_ID);
                editor.remove(Constant.USER_INFO_PASSWORD);
                editor.commit();

                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                SettingActivity.this.finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {

        }
    }
}
