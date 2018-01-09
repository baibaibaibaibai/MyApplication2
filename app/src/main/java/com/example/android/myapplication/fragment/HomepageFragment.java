package com.example.android.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.myapplication.R;
import com.example.android.myapplication.views.TitleBar;

import static android.view.View.GONE;

/**
 *
 */

public class HomepageFragment extends Fragment {
    private static final String TAG = "TestFragment";
    private TitleBar titleBar;

    public static HomepageFragment newInstance(String s) {
        HomepageFragment homeFragment = new HomepageFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initTitleBar(view);
        return view;

    }
    private void initTitleBar(View view) {
        this.titleBar = ((TitleBar) view.findViewById(R.id.title));
        this.titleBar.setTitle("主页");
        this.titleBar.setLeftButtonVisibility(GONE);
//        this.titleBar.setOnLeftBtClickedListener(new View.OnClickListener()
//        {
//            public void onClick(View paramAnonymousView)
//            {
//               this.finish();
//            }
//        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Log.d(TAG, "TestFragment-----onDestroy");
    }

}
