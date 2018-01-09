package com.example.android.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.entity.MessageEntity;

import java.util.List;

public class MessageAdapter extends BaseAdapter {

    public static int count = 0;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MessageEntity> messageEntity;

    public MessageAdapter(Context context, List<MessageEntity> mMessageEntity) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        messageEntity = mMessageEntity;

    }

    @Override
    public int getCount() {
//        return messageEntity.size();
        return 5;
    }

    @Override
    public Object getItem(int position) {

        return messageEntity.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_fragment_message, null);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.msgTextView = (TextView) convertView.findViewById(R.id.tv_message);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.titleTextView.setText(messageEntity.get(position).getTitle());
//        viewHolder.dateTextView.setText(messageEntity.get(position).getTime());
//        viewHolder.msgTextView.setText(messageEntity.get(position).getMessage());


        return convertView;
    }


    private class ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView msgTextView;
    }


}
