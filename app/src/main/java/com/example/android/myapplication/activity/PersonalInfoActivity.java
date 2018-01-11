package com.example.android.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.base.BaseActivity;
import com.example.android.myapplication.base.Constant;

import java.io.File;

/**
 * 个人信息
 */
public class PersonalInfoActivity extends BaseActivity {

    protected static final int PHOTO = 0;
    private ImageView Personal_head;// 头像
    private RelativeLayout personal_info_sex, ReplaceThepicture, // 更换头像
            birthday, // 生日
            region, // 地区
            Personal_name;// 相对布局 姓名
    private TextView bin_no;// 个人姓名
    private static TextView _tProvince;
    private static TextView _tCity;
    private TextView _name;
    private TextView personal_sex;// 性别显示
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final int INTENT_SEX = 4; // 返回性别
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private Button textView1, textView2;// 相册 拍照
    private Bitmap photo;
    private Bitmap imageBitmap;

    private static Context mContext;
    private String img;// image_url 用户头像路径
    public String name1_;
    private String uid, binzhi_id, name, sex, sexid, ubirthday, city, area,
            image_url, bzlevel;
    private TextView title_name;
    private TextView peason_notice, peason_notice_name;
    private SharedPreferences sp;
    private SharedPreferences sharedPreferences;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PHOTO:
//                    Intent intent = new Intent(mContext, GridImageActivity.class);
//                    startActivityForResult(intent, 421);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal);
        mContext = this;
        sharedPreferences = getSharedPreferences(Constant.SAVE,
                Activity.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", "");
        sp = getSharedPreferences("com.bzgj.noticewaring", Context.MODE_PRIVATE);
        initData();

    }

    @Override
    protected void onResume() {
        // TODO 自动生成的方法存根
        super.onResume();

    }

    private Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return bitmap;
    }

    /**
     * 查询用户个人信息
     *
     * @param uid
     */

    private void getAppuserByUId(final String uid) {

    }

    private void initData() {

        personal_info_sex = (RelativeLayout) findViewById(R.id.personal_info_sex);
        personal_info_sex.setOnClickListener(listener);
        personal_sex = (TextView) findViewById(R.id.personal_sex);// 性别
        title_name = (TextView) findViewById(R.id.title);
        ReplaceThepicture = (RelativeLayout) findViewById(R.id.rl1);

        Personal_head = (ImageView) findViewById(R.id.imagView_head);
        peason_notice = (TextView) findViewById(R.id.peason_notice);
        peason_notice_name = (TextView) findViewById(R.id.peason_notice_name);

        ReplaceThepicture.setOnClickListener(listener);

        bin_no = (TextView) findViewById(R.id.text_bin_no);

        birthday = (RelativeLayout) findViewById(R.id.rl7);
        region = (RelativeLayout) findViewById(R.id.rl8);

        birthday.setOnClickListener(listener);
        region.setOnClickListener(listener);
        _tProvince = (TextView) findViewById(R.id.text_province);
        _tCity = (TextView) findViewById(R.id.text_city);

        Personal_name = (RelativeLayout) findViewById(R.id.Personal_rl5);
        Personal_name.setOnClickListener(listener);
        _name = (TextView) findViewById(R.id.text_name);
        title_name.setText("个人信息");
        name1_ = _name.getText().toString().trim();
        getAppuserByUId(uid);
    }

    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Editor edit = sp.edit();
            switch (v.getId()) {

                case R.id.rl1:

                    final String[] str = new String[]{
                            (String) textView1.getText(),
                            (String) textView2.getText()};
                    new AlertDialog.Builder(PersonalInfoActivity.this)
                            .setTitle("设置头像")
                            .setItems(str, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int arg1) {
                                    if (str[arg1].equals("相册")) {

                                        new Thread() {
                                            @Override
                                            public void run() {
                                                Message msg = handler
                                                        .obtainMessage();
                                                msg.what = PHOTO;
                                                Bundle bundle = new Bundle();
                                                msg.obj = bundle;
                                                handler.sendMessage(msg);
                                            }
                                        }.start();

                                    } else if (str[arg1].equals("拍照")) {

                                        Intent intent2 = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent2.putExtra(
                                                MediaStore.EXTRA_OUTPUT,
                                                Uri.fromFile(new File(
                                                        Environment
                                                                .getExternalStorageDirectory(),
                                                        "head.jpg")));
                                        startActivityForResult(intent2, 422);

                                    }
                                }
                            }).setNegativeButton("取消", null).show();
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    String path;

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 423);
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);// 150
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 423);
    }


}
