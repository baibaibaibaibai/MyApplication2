package com.example.android.myapplication.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.example.android.myapplication.R;
import com.example.android.myapplication.base.BaseActivity;
import com.example.android.myapplication.base.Constant;
import com.example.android.myapplication.utils.ImageLoader;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                    Intent intent = new Intent(mContext, GridImageActivity.class);
                    startActivityForResult(intent, 421);
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
        setNotice();
    }

    @Override
    protected void onResume() {
        // TODO 自动生成的方法存根
        super.onResume();
        setNotice();
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
                case R.id.title_back:
                    finish();
                    break;
                case R.id.personal_info_sex:// 设置性别
                    Util.uploadAli(AliUpLoadUtils.ACTIVITY_CLICK_USER_GENDER);
                    Intent sex_intent = new Intent(mContext,
                            PersonalSexActivity.class);
                    sex_intent.putExtra("sex", personal_sex.getText().toString()
                            .trim());
                    startActivityForResult(sex_intent, INTENT_SEX);
                    break;
                case R.id.Personal_rl5:


                    Intent intent1 = new Intent(PersonalInfoActivity.this,
                            PersonalNameActivity.class);
                    intent1.putExtra("name", name);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    break;
                case R.id.rl7:

                    DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                            PersonalInfoActivity.this, null);
                    ubirthday = dateTimePicKDialog.dateTimePicKDialog(datatime,
                            ubirthday);
                    break;

                case R.id.rl8:

                    Intent intent2 = new Intent(PersonalInfoActivity.this,
                            ProvinceListActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent2, 1);
                    int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                    if (version >= 5) {
                        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    }
                    break;

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

        if (requestCode == 421) {
            if (data == null) {
                return;
            }
            String uriString = data.getStringExtra("URI");
            Uri uri = Uri.parse(uriString);
            cropPhoto(uri);
        }
        if (requestCode == 422) {

            File temp = new File(Environment.getExternalStorageDirectory()
                    + "/head.jpg");
            cropPhoto(Uri.fromFile(temp));
        }
        if (requestCode == 423) {

            if (data == null) {
                return;
            }
            if (data != null) {
                try {

                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;
                    }
                    if (extras.getParcelable("data") == null) {
                        return;
                    }
                    photo = extras.getParcelable("data");
                } catch (Exception e) {

                }
                if (photo != null) {

//					Personal_head.setImageBitmap(photo);// 用ImageView显示出来

                    File picture = new File(Environment.getExternalStorageDirectory() + "/temp.png");
                    // startPhotoZoom(Uri.fromFile(picture));
                    String sdStatus = Environment.getExternalStorageState();

                    String name = new DateFormat().format("yyyyMMdd_hhmmss",
                            Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    // Bundle bundle = data.getExtras();
                    // final Bitmap bitmap = (Bitmap) bundle.get("data");//
                    // 获取相机返回的数据，并转换为Bitmap图片格式
                    FileOutputStream b = null;
                    File file = new File("/sdcard/myImage/");
                    file.mkdirs();// 创建文件夹
                    String fileName = "/sdcard/myImage/" + name;
                    // picName=name;
                    String path = "/sdcard/myImage/";
                    try {
                        b = new FileOutputStream(fileName);
                        photo.compress(Bitmap.CompressFormat.JPEG, 10, b);// 把数据写入文件

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            b.flush();
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Personal_head.setImageBitmap(photo);
                    // send(iamgepath);
                    // uploadFile();
                    try {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {

                               //图片上传
                            }
                        }).start();
                    } catch (Exception e) {

                    }
                }
            }
        }

        if (resultCode == INTENT_SEX) {

            personal_sex.setText(data.getStringExtra("sex"));
        }
        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
//					+ picture.getPath());
            // startPhotoZoom(Uri.fromFile(picture));
            String sdStatus = Environment.getExternalStorageState();

            String name = new DateFormat().format("yyyyMMdd_hhmmss",
                    Calendar.getInstance(Locale.CHINA))
                    + ".jpg";
            Bundle bundle = data.getExtras();
            final Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/" + name;
            // picName=name;
            String path = "/sdcard/myImage/";
            // ////System.out.println("环境变量"+path);
            // iamgepath=path + "/" + name;
            // ////System.out.println("全部路径"+iamgepath+"的说法|"+picName);
            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Personal_head.setImageBitmap(bitmap);
            // send(iamgepath);
            // uploadFile();

            new Thread(new Runnable() {

                @Override
                public void run() {

                    if (MyUtils_Http
                            .isNetworkConnected(PersonalInfoActivity.this)) {

                        Bitmap file = bitmap;// 图片
                        String type = "0001"; // 0001：头像；0002：身份证；0003：名片
                        String RequestURL = Constant.SERVER_ADDRESS
                                + "/uploadFile/uploadpic?";
                        String str = MyUtils_Http.requestByBitmapImageHttpPost(
                                uid, file, type, RequestURL);

                    } else {
                        Looper.prepare();
                        Toast.makeText(PersonalInfoActivity.this, "网络链接失败 ", 0)
                                .show();
                        Looper.loop();
                    }
                }
            }).start();
        }

        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }

        // 处理结果
        if (requestCode == PHOTORESOULT) {

            Uri imageFileUri = data.getData();
            Bundle bundle1 = data.getExtras();
            path = Environment.getExternalStorageDirectory().getPath();

            // 取得当前显示区域
            Display currentDisplay = getWindowManager().getDefaultDisplay();
            float dw = 150;
            float dh = 150;
            try {
                // 对图片进行缩放
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = true;
                Bitmap bmp = BitmapFactory
                        .decodeStream(
                                getContentResolver().openInputStream(
                                        imageFileUri), null, bmpFactoryOptions);

                int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
                        / (float) dh);
                int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                        / (float) dw);

                if (heightRatio > 1 && widthRatio > 1) {
                    if (heightRatio > widthRatio) {
                        bmpFactoryOptions.inSampleSize = heightRatio;
                    } else {
                        bmpFactoryOptions.inSampleSize = widthRatio;
                    }
                }
                bmpFactoryOptions.inJustDecodeBounds = false;
                // 缩放后的图片
                bmp = BitmapFactory
                        .decodeStream(
                                getContentResolver().openInputStream(
                                        imageFileUri), null, bmpFactoryOptions);
                // 创建一张新图片
                photo = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
                        bmp.getConfig());
                Canvas canvas = new Canvas(photo);
                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                // 调定画笔宽度
                paint.setStrokeWidth(5);
                Matrix matrix = new Matrix();
                canvas.drawBitmap(bmp, matrix, paint);
                Personal_head.setImageBitmap(photo);

                String[] pojo = {MediaStore.Images.Media.DATA};
                ////System.out.println("照片+" + pojo);
                Cursor cursor = managedQuery(imageFileUri, pojo, null, null,
                        null);
                ////System.out.println("路径" + cursor.toString());
                if (cursor != null) {
                    ContentResolver cr = this.getContentResolver();
                    int colunm_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    ////System.out.println("路径" + colunm_index);
                    cursor.moveToFirst();
                    path = cursor.getString(colunm_index);
                    ////System.out.println("得到路径" + path + "|");

                }
            } catch (Exception e) {
                ////System.out.println("抛出异常");
            }
            new Thread(new Runnable() {

                @Override
                public void run() {

                    if (MyUtils_Http
                            .isNetworkConnected(PersonalInfoActivity.this)) {

                        Bitmap file = photo;// 图片
                        String type = "0001"; // 0001：头像；0002：身份证；0003：名片
                        String RequestURL = Constant.SERVER_ADDRESS
                                + "/uploadFile/uploadpic?";
                        String str = MyUtils_Http.requestByBitmapImageHttpPost(
                                uid, file, type, RequestURL);

                    } else {
                        Looper.prepare();
                        Toast.makeText(PersonalInfoActivity.this, "网络链接失败 ", 0)
                                .show();
                        Looper.loop();
                    }
                }
            }).start();
            // //Toast.makeText(mContext, "头像上传成功，请耐心等待管理猿大大的审核哦！", 0).show();
            // }
        }
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

    private void setNotice() {
        String leveNotice = sp.getString(Constants.LEVEL, null);
        String nameNotice = sp.getString(Constants.USERNAME, null);
        if (leveNotice != null) {
            peason_notice.setVisibility(View.VISIBLE);
        } else {
            peason_notice.setVisibility(View.INVISIBLE);
        }
        if (nameNotice != null) {
            peason_notice_name.setVisibility(View.VISIBLE);
        } else {
            peason_notice_name.setVisibility(View.INVISIBLE);
        }
    }

}
