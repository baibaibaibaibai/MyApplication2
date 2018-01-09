package com.example.android.myapplication.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateManager {
    private static final int DOWNLOAD = 1;

    private static final int DOWNLOAD_FINISH = 2;

    private String mSavePath;

    private int progress;

    private boolean cancelUpdate = false;

    private Context mContext;

    private ProgressBar mProgress;

    private Dialog mDownloadDialog;

    private static String mApkUrl;

    private static String mServiceCode;

    private static String mUpdateContent;

    private String mPackageNameString = "";

    public interface OnUpdateListener {
        void onUpdate(boolean isUpdate);
    }

    private OnUpdateListener mOnUpdateListener;

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        mOnUpdateListener = onUpdateListener;
    }

    public void setUpdateParms(String apkUrl, String serviceCode, String updateContent) {
        mApkUrl = apkUrl;
        String[] strs = mApkUrl.split("/");
        String last = strs[strs.length - 1];
        int index = last.indexOf(".");
        mPackageNameString = last.substring(0, index);
        mServiceCode = serviceCode;
        mUpdateContent = updateContent;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    if (mContext != null) {
                        mProgress.setProgress(progress);
                    }
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    if (mContext != null) {
                        installApk();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public UpdateManager(Activity activity) {
        this.mContext = activity;
    }

    public void checkUpdate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
    {
        System.out.println(paramString1 + "-" + paramString2 + "-" + paramString3);
        if (needUpdate(paramString1))
        {
            this.mApkUrl = paramString3;
            showNoticeDialog();
        }
    }
    public void checkUpdate(boolean showAlreadyNewDlg) {
        if(showAlreadyNewDlg) {
            showDownloadDialog();
        }else {
            if (isNeedUpdate()) {
                showNoticeDialog();
            } else {
                //ToastUtils.showShort(mContext, R.string.soft_update_no);
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onUpdate(false);
                }
            }
        }

    }
    public boolean needUpdate(String paramString)
    {
        System.out.println("code" + paramString + "-code" + getVersionCode(this.mContext));
        return !paramString.equals(getVersionCode(this.mContext));
    }

    public boolean isNeedUpdate() {
        String versionCode = getVersionCode(mContext);
        // float versionCode = 0;

        return mServiceCode.compareTo(versionCode) > 0;

    }

    private String getVersionCode(Context context) {
        String versionCode = "0";
        try {
            String packageName = context.getPackageName();
            versionCode = context.getPackageManager().getPackageInfo(
                    packageName, 0).versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private void showNoticeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新V" + mServiceCode + "版");
        if(mUpdateContent != null) {
            builder.setMessage(mUpdateContent);
        } else {
            builder.setMessage(R.string.Version_update_info);
        }

        builder.setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (mOnUpdateListener != null) {
                            mOnUpdateListener.onUpdate(false);
                        }
                    }
                });

        builder.setNegativeButton("确定更新",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showDownloadDialog();
                    }
                });

        AlertDialog noticeDialog = builder.create();
        noticeDialog.setCancelable(false);
        noticeDialog.show();
    }

    private void showDownloadDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.Version_soft_updating);
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_download_version, null);
        mProgress = (ProgressBar) v.findViewById(R.id.loadingImageView);
        builder.setView(v);
//    builder.setNegativeButton(R.string.soft_update_cancel,
//          new OnClickListener() {
//             @Override
//             public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
////                  cancelUpdate = true;
////                  if (mOnUpdateListener != null) {
////                     mOnUpdateListener.onUpdate(false);
////                  }
//             }
//          });
        mDownloadDialog = builder.create();
        mDownloadDialog.setCancelable(false);
        mDownloadDialog.show();
        downloadApk();
    }

    private void downloadApk() {
        new downloadApkThread().start();
    }

    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {

                String sdpath = getBaseSavePath(mContext) + "/";
                mSavePath = sdpath + "download";
                URL url = new URL(mApkUrl);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(mSavePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                File apkFile = new File(mSavePath, mPackageNameString);
//          Uri contentUri = getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", apkFile);


                FileOutputStream fos = new FileOutputStream(apkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    mHandler.sendEmptyMessage(DOWNLOAD);
                    if (numread <= 0) {
                        mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!cancelUpdate);
                fos.close();
                is.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mDownloadDialog.dismiss();
        }
    }

//    private void installApk() {
//        File apkfile = new File(mSavePath, mPackageNameString);
//        if (!apkfile.exists()) {
//            return;
//        }
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
//            Uri apkUri = getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", apkfile); //与manifest中定义的provider中的authorities="com.xinchuang.buynow.fileprovider"保持一致
//            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//            i.setDataAndType(apkUri, "application/vnd.android.package-archive");
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        } else {
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//                    "application/vnd.android.package-archive");
//        }
//        mContext.startActivity(i);
//    }
    private void installApk()
    {
        Object localObject = new File(this.mSavePath, "management.apk");
        Intent localIntent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N)
        {
            localObject = FileProvider.getUriForFile(this.mContext, "com.bt.cms.provider", (File)localObject);
            localIntent = new Intent("android.intent.action.VIEW");
            localIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setDataAndType((Uri)localObject, this.mContext.getContentResolver().getType((Uri)localObject));
            this.mContext.startActivity(localIntent);
            return;
        }
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setDataAndType(Uri.parse("file://" + ((File)localObject).toString()), "application/vnd.android.package-archive");
        this.mContext.startActivity(localIntent);
    }

    public static String getBaseSavePath(Context context) {
        String sdStatus = Environment.getExternalStorageState();
        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return context.getFilesDir().getAbsolutePath();
        }
    }

    public void handleContactPermission() {
        if ((Integer.parseInt(Build.VERSION.SDK) >= Build.VERSION_CODES.N) && (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE") != 0)) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                Builder localBuilder = new Builder(this.mContext);
                localBuilder.setTitle("权限申请");
                localBuilder.setMessage("您需要允许访问文件下载");
                localBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        paramAnonymousDialogInterface.dismiss();
                        UpdateManager.this.requestContactPermission();
                    }
                });
                localBuilder.create().show();
            }
            requestContactPermission();
        }
    }

    public void requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions((Activity) this.mContext, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 12);
        }
    }
}
