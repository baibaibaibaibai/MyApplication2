package com.example.android.myapplication.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.example.android.myapplication.R;
import com.example.android.myapplication.adapter.ViewPagerAdapter;
import com.example.android.myapplication.base.BaseFragmentActivity;
import com.example.android.myapplication.base.Constant;
import com.example.android.myapplication.fragment.FindFragment;
import com.example.android.myapplication.fragment.HomepageFragment;
import com.example.android.myapplication.fragment.MessageFragment;
import com.example.android.myapplication.fragment.MineFragment;
import com.example.android.myapplication.fragment.IntelligenceFragment;
import com.example.android.myapplication.views.MyViewPager;
import com.example.android.myapplication.utils.UpdateManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 */

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener, AMapLocationListener{
    private long mExitTime = 0;
    private String changeLog = "";
    private String fileUrl = "";
    private String forceUpdate = "";

    private String versionName = "";

    private ImageView img_home, img_message, img_shed, img_find, img_mine;
    private TextView text_home, text_message, text_find, text_mine;
    private LinearLayout btn_home, btn_message, btn_shed, btn_find, btn_mine;

    private MyViewPager mPager;
    private ViewPagerAdapter mPageAdapter;
    private ArrayList<Fragment> fragmentsList;
    private HomepageFragment homeFragment;//
    private MessageFragment messageFragment;//
    private IntelligenceFragment shedFragment;//
    private FindFragment findFragment;//
    private MineFragment mineFragment;//
    private int pageitem;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);

        setContentView(R.layout.activity_main);
        initView();
        initViewPager();
        initLocation();
    }


    private void initView() {
        mPager = (MyViewPager) findViewById(R.id.viewpage);
        img_home = (ImageView) findViewById(R.id.img_home);
        img_message = (ImageView) findViewById(R.id.img_message);
        img_shed = (ImageView) findViewById(R.id.img_shed);
        img_find = (ImageView) findViewById(R.id.img_find);
        img_mine = (ImageView) findViewById(R.id.img_mine);
        text_home = (TextView) findViewById(R.id.text_home);
        text_message = (TextView) findViewById(R.id.text_message);
//        text_shed=(TextView)findViewById(R.id.text_shed);
        text_find = (TextView) findViewById(R.id.text_find);
        text_mine = (TextView) findViewById(R.id.text_mine);
        btn_home = (LinearLayout) findViewById(R.id.btn_home);
        btn_message = (LinearLayout) findViewById(R.id.btn_message);
        btn_shed = (LinearLayout) findViewById(R.id.btn_shed);
        btn_find = (LinearLayout) findViewById(R.id.btn_find);
        btn_mine = (LinearLayout) findViewById(R.id.btn_mine);

        //点击事件
        btn_home.setOnClickListener(this);
        btn_message.setOnClickListener(this);
        btn_shed.setOnClickListener(this);
        btn_find.setOnClickListener(this);
        btn_mine.setOnClickListener(this);


    }

    private void initViewPager() {

        fragmentsList = new ArrayList<Fragment>();
        Fragment homeFragment = new HomepageFragment();
        Fragment messageFragment = new MessageFragment();
        Fragment shedFragment = new IntelligenceFragment();
        Fragment findFragment = new FindFragment();
        Fragment mineFragment = new MineFragment();

        fragmentsList.add(homeFragment);
        fragmentsList.add(messageFragment);
        fragmentsList.add(shedFragment);
        fragmentsList.add(findFragment);
        fragmentsList.add(mineFragment);

        mPageAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentsList);
        mPager.setAdapter(mPageAdapter);
        mPager.setCurrentItem(0);
        //禁止viewpager滑动
        mPager.setNoScroll(true);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 获取定位坐标
     */
    public void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        mLocationOption.setOnceLocationLatest(true);
        //开/关闭缓存机制
        mLocationOption.setLocationCacheEnable(true);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:

                    initialization();

                    text_home.setTextColor(getResources().getColor(R.color.text_select));
                    img_home.setImageResource(R.mipmap.group_icon);
                    pageitem = 0;
                    homeFragment = (HomepageFragment) mPageAdapter.getItem(arg0);
                    break;
                case 1:

                    initialization();
                    pageitem = 1;
                    text_message.setTextColor(getResources().getColor(R.color.text_select));
                    img_message.setImageResource(R.mipmap.group_icon);
                    messageFragment = (MessageFragment) mPageAdapter.getItem(arg0);

                    break;
                case 2:

                    initialization();
                    pageitem = 2;
                    img_shed.setImageResource(R.mipmap.group_icon);
                    shedFragment = (IntelligenceFragment) mPageAdapter.getItem(arg0);

                    break;
                case 3:

                    initialization();
                    pageitem = 3;
                    text_find.setTextColor(getResources().getColor(R.color.text_select));
                    img_find.setImageResource(R.mipmap.group_icon);
                    findFragment = (FindFragment) mPageAdapter.getItem(arg0);

                    break;
                case 4:

                    initialization();
                    pageitem = 4;
                    text_mine.setTextColor(getResources().getColor(R.color.text_select));
                    img_mine.setImageResource(R.mipmap.group_icon);
                    mineFragment = (MineFragment) mPageAdapter.getItem(arg0);

                    break;
                default:
                    break;
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public void forceUpdate() {
        if (this.forceUpdate.equals("0")) {
            new UpdateManager(this).checkUpdate(this.versionName, this.versionName, this.fileUrl, this.changeLog, this.forceUpdate, false);
            return;
        }
        new UpdateManager(this).checkUpdate(this.versionName, this.versionName, this.fileUrl, this.changeLog, this.forceUpdate, false);
    }

    private void initialization() {
        this.text_home.setTextColor(getResources().getColor(R.color.text_normal));
        this.text_message.setTextColor(getResources().getColor(R.color.text_normal));
        this.text_find.setTextColor(getResources().getColor(R.color.text_normal));
        this.text_mine.setTextColor(getResources().getColor(R.color.text_normal));
        this.img_home.setImageResource(R.mipmap.ic_launcher);
        this.img_find.setImageResource(R.mipmap.ic_launcher);
        this.img_message.setImageResource(R.mipmap.ic_launcher);
        this.img_shed.setImageResource(R.mipmap.ic_launcher);
        this.img_mine.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_home://主页
                initialization();

                text_home.setTextColor(getResources().getColor(R.color.text_select));
                img_home.setImageResource(R.mipmap.group_icon);
                pageitem = 0;
                mPager.setCurrentItem(pageitem);

                break;
            case R.id.btn_message://消息
                initialization();
                pageitem = 1;
                text_message.setTextColor(getResources().getColor(R.color.text_select));
                img_message.setImageResource(R.mipmap.group_icon);
                mPager.setCurrentItem(pageitem);

                break;
            case R.id.btn_shed://棚
                initialization();
                pageitem = 2;
                img_shed.setImageResource(R.mipmap.group_icon);
                mPager.setCurrentItem(pageitem);

                break;
            case R.id.btn_find://发现
                initialization();
                pageitem = 3;
                text_find.setTextColor(getResources().getColor(R.color.text_select));
                img_find.setImageResource(R.mipmap.group_icon);
                mPager.setCurrentItem(pageitem);

                break;
            case R.id.btn_mine://我的
                initialization();
                pageitem = 4;
                text_mine.setTextColor(getResources().getColor(R.color.text_select));
                img_mine.setImageResource(R.mipmap.group_icon);
                mPager.setCurrentItem(pageitem);

                break;
            default:
                break;
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
            {

                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();

            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
//            new AlertDialog.Builder(this).setTitle("退出登录").setMessage("是否退出？").setPositiveButton("是", new DialogInterface.OnClickListener()
//            {
//                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
//                {
////                    AppManager.getAppManager().finishAllActivity();
//                }
//            }).setNegativeButton("否", new DialogInterface.OnClickListener()
//            {
//                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
//            }).show();

    }

       /**
     * 高德定位回调
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            Double latitude = amapLocation.getLatitude();
            Double longitude = amapLocation.getLongitude();
            amapLocation.getCityCode();//城市编码
            amapLocation.getAdCode();//地区编码
            SharedPreferences flageSp = getSharedPreferences(
                    Constant.USER_CONSERVE, Activity.MODE_PRIVATE);
            // 获得SharedPreferences.Editor
            SharedPreferences.Editor editor = flageSp.edit();
            // 获得putXxx对象
            editor.putString(Constant.CITY, amapLocation.getCity());
            // 将数据库保存在文件中
            editor.commit();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(this);
        mLocationClient = null;
    }

}
