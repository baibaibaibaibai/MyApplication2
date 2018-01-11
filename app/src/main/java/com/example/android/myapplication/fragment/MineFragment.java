package com.example.android.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.myapplication.R;
import com.example.android.myapplication.activity.PersonalActivity;
import com.example.android.myapplication.activity.PersonalInfoActivity;
import com.example.android.myapplication.activity.SettingActivity;
import com.example.android.myapplication.views.TitleBar;

import static android.view.View.GONE;

/**
 *
 */

public class MineFragment extends Fragment {
    private static final String TAG = "TestFragment";
    private TitleBar titleBar;

    public static MineFragment newInstance(String s) {
        MineFragment homeFragment = new MineFragment();
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

    /**
     * 个人信息
     */
    public void MineOnclick(View view) {
        Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);

        startActivity(intent);
    }

    /**
     * 设置
     */
    public void SettingOnclick(View view) {

        Intent intent = new Intent(getActivity(), SettingActivity.class);

        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initTitleBar(view);

        return view;

    }

    private void initTitleBar(View view) {
        this.titleBar = ((TitleBar) view.findViewById(R.id.title));
        this.titleBar.setTitle("我的");
        this.titleBar.setLeftButtonVisibility(GONE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Log.d(TAG, "TestFragment-----onDestroy");
    }

}
