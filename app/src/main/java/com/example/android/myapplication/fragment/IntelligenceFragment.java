package com.example.android.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.example.android.myapplication.R;
import com.example.android.myapplication.adapter.BannerAdapter;
import com.example.android.myapplication.adapter.IntelligenceAdapter;
import com.example.android.myapplication.base.Constant;
import com.example.android.myapplication.views.TitleBar;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * 智能
 */

public class IntelligenceFragment extends Fragment implements ViewPager.OnPageChangeListener, WeatherSearch.OnWeatherSearchListener {
    private static final String TAG = "TestFragment";
    private TitleBar titleBar;

    private int[] imas;
    private LinearLayout ll;
    private ArrayList<String> imaList;
    private boolean flag = false;
    private WeatherSearchQuery weatherQuery;
    private WeatherSearch weatherSearch;
    private LocalWeatherForecast weatherForecast;
    private RollPagerView mRollViewPager;

    private TextView weather_day1, weather_day2, weather_day3, weather_w1, weather_w2, weather_w3, weather_t1, weather_t2, weather_t3;//天气

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

        ll = (LinearLayout) view.findViewById(R.id.point_container);
        //为GridView设置适配器
        gv.setAdapter(new IntelligenceAdapter(getActivity()));
        //注册监听事件
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(), "pic" + position, Toast.LENGTH_SHORT).show();
            }
        });
        initView(view);
        initRollPager(view);
        SharedPreferences preferences = getActivity().getSharedPreferences(Constant.USER_CONSERVE, Context.MODE_PRIVATE);
        String city = preferences.getString(Constant.CITY, "defaultname");
        //查询天气
        sendRequestWithHttpClient(TextUtils.isEmpty(city) ? "北京" : city);


        return view;

    }

    private void initTitleBar(View view) {
        this.titleBar = ((TitleBar) view.findViewById(R.id.title));
        this.titleBar.setTitle("智能");
        this.titleBar.setLeftButtonVisibility(GONE);
    }

    private void initView(View view) {
        weather_day1 = (TextView) view.findViewById(R.id.weather_day1);
        weather_day2 = (TextView) view.findViewById(R.id.weather_day2);
        weather_day3 = (TextView) view.findViewById(R.id.weather_day3);
        weather_w1 = (TextView) view.findViewById(R.id.weather_w1);
        weather_w2 = (TextView) view.findViewById(R.id.weather_w2);
        weather_w3 = (TextView) view.findViewById(R.id.weather_w3);
        weather_t1 = (TextView) view.findViewById(R.id.weather_t1);
        weather_t2 = (TextView) view.findViewById(R.id.weather_t2);
        weather_t3 = (TextView) view.findViewById(R.id.weather_t3);
    }


    private void initRollPager(View view) {
        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(1000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new BannerAdapter(getActivity(), imaList));

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

    private void sendRequestWithHttpClient(String city) {
        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        weatherQuery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        weatherSearch = new WeatherSearch(getActivity());
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(weatherQuery);
        weatherSearch.searchWeatherAsyn(); //异步搜索

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

    /**
     * 实时天气查询回调
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();

//                humidity.setText("湿度         "+weatherlive.getHumidity()+"%");
            } else {
                Log.e("", "查询天气失败");
            }
        } else {
            Log.e("", "查询天气失败");
        }
    }

    /**
     * 天气预报
     *
     * @param localWeatherForecastResult
     * @param rCode
     */
    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int rCode) {
        if (rCode == 1000) {
            LocalWeatherForecast weatherForecast = localWeatherForecastResult.getForecastResult();
            List<LocalDayWeatherForecast> dayWeatherList = weatherForecast.getWeatherForecast();

            String forestMsgInString = "";
            if (dayWeatherList != null && dayWeatherList.size() >= 3) {
//            for (int j = 0; j < dayWeatherList.size(); j++) {
//                forestMsgInString += dayWeatherList.get(j).getDate() + "\n";
//                forestMsgInString += dayWeatherList.get(j).getDayWeather() + "\n";
//                forestMsgInString += dayWeatherList.get(j).getDayTemp() + "\n";
//            }
                weather_day1.setText("今天");
                weather_day2.setText("明天");
                weather_day3.setText(dayWeatherList.get(2).getWeek());
                weather_w1.setText(dayWeatherList.get(0).getDayWeather());
                weather_w2.setText(dayWeatherList.get(1).getDayWeather());
                weather_w3.setText(dayWeatherList.get(2).getDayWeather());
                weather_t1.setText(dayWeatherList.get(0).getNightTemp() + "/" + dayWeatherList.get(0).getDayTemp());
                weather_t2.setText(dayWeatherList.get(1).getNightTemp() + "/" + dayWeatherList.get(1).getDayTemp());
                weather_t3.setText(dayWeatherList.get(2).getNightTemp() + "/" + dayWeatherList.get(2).getDayTemp());
            }

        } else {
            Log.e("", "查询天气失败");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        flag = false;
        Log.d(TAG, "TestFragment-----onDestroy");
    }

}
