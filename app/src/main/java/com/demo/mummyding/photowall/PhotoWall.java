package com.demo.mummyding.photowall;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.provider.MediaStore.Images.Media.CONTENT_TYPE;
import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;

public class PhotoWall extends AppCompatActivity {
    private GridView gridView;
    private ImageAdapter adapter;
    private Toolbar toolbar;
    private List<ImageBean> list = new ArrayList<>();
    public static List<ImageBean> checkedPhoto = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done:
                adapter.setList(checkedPhoto);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        gridView = (GridView) findViewById(R.id.photo_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("选择图片(0)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getPhotos();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(adapter);
    }
    private void getPhotos(){
        final ContentResolver cr = getContentResolver();
        final String selection = "(("+MIME_TYPE+"=?)or("+MIME_TYPE+
                "=?))";
        final String [] selectionArgs = new String[]{"image/jpeg","image/png"};
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = cr.query(EXTERNAL_CONTENT_URI, null, selection, selectionArgs, null);
                while (cursor!= null && cursor.moveToNext()){
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));

                    ImageBean imageBean = new ImageBean().setImgUrl(
                            Uri.fromFile(new File(path)));
                    list.add(imageBean);
                }
            }
        }).start();
        adapter = new ImageAdapter(list,this);

    }
}
