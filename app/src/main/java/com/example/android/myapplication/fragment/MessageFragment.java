package com.example.android.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.adapter.MessageAdapter;
import com.example.android.myapplication.entity.MessageEntity;
import com.example.android.myapplication.views.TitleBar;

import java.util.List;

import static android.view.View.GONE;

/**
 *
 */

public class MessageFragment extends Fragment {
    private static final String TAG = "TestFragment";
    private TitleBar titleBar;
    private ListView mListView;
    private MessageAdapter mAdapter;
    private List<MessageEntity> mData;

    public static MessageFragment newInstance(String s) {
        MessageFragment homeFragment = new MessageFragment();
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
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initTitleBar(view);
        mListView = view.findViewById(R.id.listview);
        mAdapter = new MessageAdapter(getActivity(), mData);
        mListView.setAdapter(mAdapter);
        return view;

    }
    private void initTitleBar(View view) {
        this.titleBar = ((TitleBar) view.findViewById(R.id.title));
        this.titleBar.setTitle("消息");
        this.titleBar.setLeftButtonVisibility(GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Log.d(TAG, "TestFragment-----onDestroy");
    }

}
