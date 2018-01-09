
package com.example.android.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.myapplication.R;
import com.example.android.myapplication.adapter.MyViewPagerAdapter;
import com.example.android.myapplication.adapter.ViewPagerAdapter;
import com.example.android.myapplication.base.BaseActivity;
import com.example.android.myapplication.base.Constant;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * 引导页
 */
public class GuideActivity extends BaseActivity implements OnClickListener,
        OnPageChangeListener {
    // 定义ViewPager对象
    private ViewPager mViewPager;
    // 定义ViewPager适配器
    private MyViewPagerAdapter mViewPagerAdapter;
    // 定义一个ArrayList来存放View
    private ArrayList<View> mViews;
    private Button startBtn;
    private LayoutInflater inflater;
    private boolean misScrolled;
    // 引导图片资源
    private static final int[] mGuidePics = {
            R.mipmap.public_house_fragment_bg,
            R.mipmap.public_house_fragment_bg, R.mipmap.public_house_fragment_bg
    };

    // 底部小点的图片
    private ImageView[] mPoints;
    // 记录当前选中位置
    private int currentIndex;
    private String fromPage;// 记录从哪个页跳转过来
    // 3626 add begin wangcheng 1/15
    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        inflater = getLayoutInflater();
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        Intent intent = getIntent();
        fromPage = intent.getStringExtra("setting");

    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化ArrayList对象
        mViews = new ArrayList<View>();
        // 实例化ViewPager
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        // 实例化ViewPager适配器
        mViewPagerAdapter = new MyViewPagerAdapter(mViews);
        SharedPreferences flageSp = getSharedPreferences(
                Constant.USER_FIRST_LOGIN, Activity.MODE_PRIVATE);
        // 获得SharedPreferences.Editor
        SharedPreferences.Editor editor = flageSp.edit();
        // 获得putXxx对象
        editor.putBoolean(Constant.USER_FIRST_LOGIN, true);
        // 将数据库保存在文件中
        editor.commit();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        // 初始化引导图片列表
        for (int i = 0; i < mGuidePics.length - 1; i++) {
            ImageView mImage = new ImageView(this);
            mImage.setLayoutParams(mParams);
            mImage.setImageResource(mGuidePics[i]);
            mViews.add(mImage);
        }
        mViews.add(inflater.inflate(R.layout.activity_guide_last_page, null));

        // 设置数据
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置监听
        mViewPager.setOnPageChangeListener(this);
        // 初始化底部小点
        initPoint();
        try {
            Field leftEdgeField = mViewPager.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = mViewPager.getClass().getDeclaredField("mRightEdge");
            if (leftEdgeField != null && rightEdgeField != null) {
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(mViewPager);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(mViewPager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 初始化底部小点
     */
    private void initPoint() {
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        mPoints = new ImageView[mGuidePics.length];

        // 循环取得小点图片
        for (int i = 0; i < mGuidePics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            mPoints[i] = (ImageView) mLinearLayout.getChildAt(i);
            // 默认都设为灰色
            mPoints[i].setEnabled(true);
            // 给每个小点设置监听
            mPoints[i].setOnClickListener(this);
            // 设置位置tag，方便取出与当前位置对应
            mPoints[i].setTag(i);
        }

        // 设置当面默认的位置
        currentIndex = 0;
        // 设置为白色，即选中状态
        mPoints[currentIndex].setEnabled(false);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                misScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                misScrolled = true;
                break;
        }

        if (rightEdge != null && !rightEdge.isFinished()) {// 到了最后一张并且还继续拖动，出现蓝色限制边条了
//            if (!TextUtils.isEmpty(fromPage) && fromPage.equals("setting")) {
//                Intent intent = new Intent(GuideActivity.this, SettingActivity.class);
//                startActivity(intent);
//            } else {
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
//            }
            GuideActivity.this.finish();
        }

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    /**
     * 当新的页面被选中时调用
     */

    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        setCurDot(position);
    }

    /**
     * 通过点击事件来切换当前的页面
     */
    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position) {
        if (position < 0 || position >= mGuidePics.length) {
            return;
        }
        mViewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon) {
        if (positon < 0 || positon > mGuidePics.length - 1
                || currentIndex == positon) {
            return;
        }
        mPoints[positon].setEnabled(false);
        mPoints[currentIndex].setEnabled(true);

        currentIndex = positon;
    }

//    // positon立即体验，在XML文件中设置onClick属性
//    public void startbutton(View v) {
//        if (!TextUtils.isEmpty(fromPage) && fromPage.equals("setting")) {
//            Intent intent = new Intent(GuideActivity.this, SettingActivity.class);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//            startActivity(intent);
//        }
//        GuideActivity.this.finish();
//    }

}
