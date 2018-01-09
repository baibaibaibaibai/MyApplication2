package com.example.android.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.R;

/**
 * Created by baidan on 2017/12/23.
 */

public class IntelligenceAdapter extends BaseAdapter {
    //上下文对象
    private Context context;
    private LayoutInflater mInflater;
    //图片数组
    private Integer[] imgs = {
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
    };
    //
    private String[] text = {"放风", "卷膜", "卷帘", "滴灌", "监控", "光照", "二氧化碳", "土壤",
    };

    public IntelligenceAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return imgs.length;
    }

    public Object getItem(int item) {
        return item;
    }

    public long getItemId(int id) {
        return id;
    }

    //创建View方法
    public View getView(int position, View convertView, ViewGroup parent) {
        IntelligenceAdapter.ViewHolder viewHolder = new IntelligenceAdapter.ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_fragment_intelligence, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IntelligenceAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(imgs[position]);//为ImageView设置图片资源
        viewHolder.textView.setText(text[position]);//为ImageView设置图片资源

        return convertView;
    }


    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }


}