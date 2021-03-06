package com.sslcs.multiselectalbum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sslcs.multiselectalbum.ImageGridAdapter.TextCallback;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ImageGridActivity extends Activity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;
	AlbumHelper helper;
	String listName;
	Button bt;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(ImageGridActivity.this,
						getResources().getString(R.string.publish_photo_max),
						Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
		}
	};
	 @Override
	    protected void onResume() {
	        // TODO Auto-generated method stub
	        helper.getImagesBucketList(false);
	        List<ImageItem> list = helper.getImagesList(listName);
	        if (list != null)
	            adapter.setList(list);
	        adapter.notifyDataSetChanged();
	        super.onResume();
	    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_grid);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		Intent intent = getIntent();
		listName = intent.getStringExtra(SingleImageGridActivity.EXTRA_IMAGE_LIST);
		initView();
		bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}

				Intent i = new Intent();

				Bundle b = new Bundle();
				b.putStringArrayList("photo", list);

				i.putExtras(b);

				setResult(10, i);

				finish();
			}

		});
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				bt.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }

				adapter.notifyDataSetChanged();
			}

		});

	}

	@Override
	protected void onStop() {
		dataList = null;
		super.onStop();
	}

	public void cancleOnclick(View v) {
		this.finish();

	}
}
