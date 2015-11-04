package com.demo.mummyding.multiimagechooser.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.demo.mummyding.multiimagechooser.R;
import com.demo.mummyding.multiimagechooser.adapter.SelectImageAdapter;
import com.demo.mummyding.multiimagechooser.model.ImageBean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;

public class PhotoWallActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView gridView;
    private SelectImageAdapter adapter;
    private List<ImageBean> imageList = new ArrayList<>();
    private List<ImageBean> originalImageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        initData();
    }
    private void initData(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        gridView = (GridView) findViewById(R.id.image_grid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("选择图片(0)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getData();
        adapter = (SelectImageAdapter) new SelectImageAdapter(this).setList(imageList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(adapter);

        Intent intent = getIntent();
        adapter.checkedList = (List<ImageBean>) intent.getSerializableExtra("checkedImage");
        Log.d("check",adapter.checkedList.size()+"");
        for(ImageBean imageBean :adapter.checkedList){
            originalImageList.add(imageBean);
            //imageList.get(imageBean.getID()).setIsChecked(imageBean.isChecked());
        }
    }
    private void getData(){
        final ContentResolver cr = getContentResolver();
        final String selection = "(("+MIME_TYPE+"=?)or("+MIME_TYPE+"=?))";
        final String [] selectionArgs = new String[]{"image/jpeg","image/png"};
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = cr.query(EXTERNAL_CONTENT_URI,null,
                        selection,selectionArgs,null);
                while (cursor!=null && cursor.moveToNext()){
                    String path = cursor.getString(
                            cursor.getColumnIndex(DATA));
                    ImageBean imageBean = new ImageBean().setImageUri(
                            Uri.fromFile(new File(path)).toString()).setID(imageList.size());
                    imageList.add(imageBean);
                }
            }
        }).start();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_done, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onDone(MenuItem item) {
        Toast.makeText(PhotoWallActivity.this,"ok",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("checkedImage", (Serializable) adapter.checkedList);
        setResult(MainActivity.PICK_IMG, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Log.d("origin",originalImageList.size()+"");
        Intent intent = new Intent();
        intent.putExtra("checkedImage", (Serializable) originalImageList);
        setResult(MainActivity.PICK_IMG, intent);
        super.onBackPressed();
    }
}
