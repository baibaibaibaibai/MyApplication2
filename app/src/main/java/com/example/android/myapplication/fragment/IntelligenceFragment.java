package com.example.android.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.services.weather.LocalWeatherForecast;
import com.example.android.myapplication.R;
import com.example.android.myapplication.adapter.BannerAdapter;
import com.example.android.myapplication.adapter.IntelligenceAdapter;
import com.example.android.myapplication.views.TitleBar;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 *
 */

public class IntelligenceFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private static final String TAG = "TestFragment";
    private TitleBar titleBar;
    private ViewPager vp;
    private int[] imas;
    private LinearLayout ll;
    private ArrayList<String> imaList;
    private boolean flag = false;
    private LocalWeatherForecast weatherForecast;

    public static IntelligenceFragment newInstance(LocalWeatherForecast weatherForecast) {
        IntelligenceFragment fragment = new IntelligenceFragment();
        Bundle args = new Bundle();
        args.putParcelable(TAG, weatherForecast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (getArguments() != null) {
            weatherForecast = getArguments().getParcelable(TAG);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intelligence, container, false);
        initTitleBar(view);
        GridView gv = (GridView) view.findViewById(R.id.gridview);
        vp = (ViewPager) view.findViewById(R.id.vp);
        vp.setOnPageChangeListener(this);
        ll = (LinearLayout) view.findViewById(R.id.point_container);
        //为GridView设置适配器
        gv.setAdapter(new IntelligenceAdapter(getActivity()));
        //注册监听事件
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(), "pic" + position, Toast.LENGTH_SHORT).show();
            }
        });

        initaaaaa(view);
        return view;

    }

    private void initTitleBar(View view) {
        this.titleBar = ((TitleBar) view.findViewById(R.id.title));
        this.titleBar.setTitle("棚");
        this.titleBar.setLeftButtonVisibility(GONE);
    }

    private RollPagerView mRollViewPager;

    private void initaaaaa(View view) {
        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(1000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new BannerAdapter(getActivity(),imaList));

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);
    }

    private class TestNormalAdapter {
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ll.getChildCount(); i++) {
            ll.getChildAt(i).setEnabled(false);
        }
        ll.getChildAt(position % 5).setEnabled(true);//设置小白点
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        flag = false;
        Log.d(TAG, "TestFragment-----onDestroy");
    }

}
