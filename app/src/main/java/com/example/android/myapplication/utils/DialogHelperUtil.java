package com.example.android.myapplication.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.R;

public class DialogHelperUtil {
    /**
     * dialog底部两个按钮
     */

    public static Dialog show(Context mContext, String strTitle, String strContent, String left, String right,
                              final OnClickListener clickCancel, final OnClickListener clickSure) {
        if (mContext == null) {
            return null;
        }

        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        try {
            Window view = dialog.getWindow();
            try {

                // 防止窗体溢出
                if (mContext != null && !((Activity) mContext).isFinishing()) {
                    dialog.show();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            view.setContentView(R.layout.dialog_common_two);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView content = (TextView) view.findViewById(R.id.content);
            Button cancel = (Button) view.findViewById(R.id.cancel);
            Button sure = (Button) view.findViewById(R.id.sure);
            // 赋值
            title.setText(strTitle);
            content.setText(strContent);
            cancel.setText(left);
            sure.setText(right);
            // 左侧按钮点击
            cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    clickCancel.onClick(v);

                }
            });
            // 右侧按钮点击
            sure.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    clickSure.onClick(v);
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
        }
        return dialog;
    }

    /**
     * dialog底部一个按钮
     */
    public static AlertDialog show(Context mContext, String strTitle, String strContent, String left,
                                   final OnClickListener clickCancel) {
        if (mContext == null) {
            return null;
        }
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        try {

            Window view = dialog.getWindow();
            try {

                // 防止窗体溢出
                if (mContext != null && !((Activity) mContext).isFinishing()) {
                    dialog.show();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            view.setContentView(R.layout.dialog_common_one);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView content = (TextView) view.findViewById(R.id.content);
            Button cancel = (Button) view.findViewById(R.id.cancel);
            // 赋值
            title.setText(strTitle);
            content.setText(strContent);
            cancel.setText(left);
            // 底部按钮点击
            cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    clickCancel.onClick(v);

                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
        return dialog;

    }

    /**
     * dialog自定义layout
     */
    public static Window show(Context mContext, int layout) {
        if (mContext == null) {
            return null;
        }
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        Window view = dialog.getWindow();
        // 防止窗体溢出
        if (mContext != null) {
            dialog.show();
        }
        view.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        return view;

    }

   
}
