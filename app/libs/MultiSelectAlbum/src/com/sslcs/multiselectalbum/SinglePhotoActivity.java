
package com.sslcs.multiselectalbum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class SinglePhotoActivity extends Activity {

    // ArrayList<Entity> dataList;//用来装载数据源的列表
    // List<ImageBucket> dataList;
    GridView gridView;
    SinglePhotoAdapter adapter;// 自定义的适配器
    AlbumHelper helper;
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    public static Bitmap bimap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_bucket);

        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        initData();
        initView();
    }
 // bug 3443(wangcheng) add 12/10 start
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        helper.getImagesBucketList(false);
        adapter.notifyDataSetChanged();
        super.onResume();
    }
 // bug 3443(wangcheng) add 12/10 end
    /**
     * 初始化数据
     */
    public void initData() {
        bimap = BitmapFactory.decodeResource(getResources(),
                R.drawable.icon_addpic_unfocused);
    }

    /**
     * 初始化view视图
     */
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new SinglePhotoAdapter(SinglePhotoActivity.this, helper.getHelper()
                .getImagesBucketList());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {

                /**
                 * 通知适配器，绑定的数据发生了改变，应当刷新视图
                 */
                // adapter.notifyDataSetChanged();
                Intent intent = new Intent(SinglePhotoActivity.this,
                        SingleImageGridActivity.class);
                intent.putExtra(SinglePhotoActivity.EXTRA_IMAGE_LIST,
                        adapter.getItem(position).getBucketName());
                startActivityForResult(intent, 10);

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {

            // Bundle b = data.getExtras();
            //
            // String str = b.getString("photo");

            ArrayList<String> list = data.getExtras().getStringArrayList(
                    "photo");
            Intent i = new Intent();

            Bundle b = new Bundle();
            b.putStringArrayList("photo", list);

            i.putExtras(b);

            setResult(10, i);
            finish();

        }
    }

    public void cancleOnclick(View v) {

        this.finish();

    }

}
