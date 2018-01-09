package com.example.android.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.myapplication.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;

/**
 *
 */

public class BannerAdapter extends StaticPagerAdapter {

    private ArrayList<String> image;
    private Context context;

    public BannerAdapter(Context mContext, ArrayList<String> mData) {
        this.image = mData;
        this.context = mContext;
        image = new ArrayList<>();
        image.add("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E5%A3%81%E7%BA%B8%E4%B8%8B%E8%BD%BD&step_word=&hs=0&pn=1&spn=0&di=83931258290&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=331016196%2C904441162&os=3899671157%2C624944029&simid=3459060175%2C307533332&adpicid=0&lpn=0&ln=3960&fr=&fmq=1514376326094_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=wallpaper&bdtype=0&oriquery=&objurl=http%3A%2F%2Fdl.bizhi.sogou.com%2Fimages%2F2013%2F07%2F26%2F352342.jpg%3Ff%3Ddownload&fromurl=ippr_z2C%24qAzdH3FAzdH3Fktzit_z%26e3Bf5257_z%26e3Bv54AzdH3F1jpwtsAzdH3Ftgu5AzdH3Fncdn9d&gsm=0&rpstart=0&rpnum=0.png");

    }


    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
//        imageView.setImageResource(image.get(position));
        Glide.with(context).load(image.get(position)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public int getCount() {
        return image.size();
    }

}