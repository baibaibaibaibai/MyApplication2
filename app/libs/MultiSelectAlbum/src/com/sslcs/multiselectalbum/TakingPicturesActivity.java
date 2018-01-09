package com.sslcs.multiselectalbum;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 拍照
 * 
 * @author neusoft
 */
// 1566、3009 modify 12/4 baidan
public class TakingPicturesActivity extends Activity {
	private String capturePath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {

			Intent getImageByCamera = new Intent(
					"android.media.action.IMAGE_CAPTURE");
			String out_file_path = "/sdcard/myImage/";
			File dir = new File(out_file_path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			capturePath = "/sdcard/myImage/" + System.currentTimeMillis()
					+ ".jpg";
			getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(capturePath)));
			getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

			startActivityForResult(getImageByCamera, 0);
		} else {
			Toast.makeText(getApplicationContext(), "请确认已经插入SD卡",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {

			Intent i = new Intent();

			Bundle nameStr = new Bundle();
			nameStr.putString("picture", capturePath);

			i.putExtras(nameStr);

			setResult(11, i);
			finish();
		}
		// 3542 add begin
        else {
            finish();
        }
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
