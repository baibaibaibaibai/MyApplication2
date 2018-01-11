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
        image.add("http://nuuneoi.com/uploads/source/playstore/cover.jpg");

    }


    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
//        imageView.setImageResource(image.get(position));
        //显示图片
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