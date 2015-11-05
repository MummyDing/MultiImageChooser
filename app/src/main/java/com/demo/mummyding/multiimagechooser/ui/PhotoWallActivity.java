package com.demo.mummyding.multiimagechooser.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getData();
        getSupportActionBar().setTitle(getString(R.string.text_selected_img) + "(" + adapter.checkedList.size() + ")");
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(adapter);
    }
    /*
     *  Get images from System Storage by Content Provider
     */
    private void getData(){
        adapter = (SelectImageAdapter) new SelectImageAdapter(this).setList(imageList);

        Intent intent = getIntent();
        adapter.checkedList = (List<ImageBean>) intent.getSerializableExtra(getString(R.string.id_selected_img));

        final ContentResolver cr = getContentResolver();
        final String selection = "(("+MIME_TYPE+"=?)or("+MIME_TYPE+"=?))";
        final String [] selectionArgs = new String[]{"image/jpeg","image/png"};
        new Thread(new Runnable() {
            @Override
            public  void run() {
                Cursor cursor = cr.query(EXTERNAL_CONTENT_URI,null,
                        selection,selectionArgs,null);
                while (cursor!=null && cursor.moveToNext()){
                    String path = cursor.getString(
                            cursor.getColumnIndex(DATA));
                    ImageBean imageBean = new ImageBean().setImageUri(
                            Uri.fromFile(new File(path)).toString()).setID(imageList.size());
                    imageList.add(imageBean);
                }
                for(ImageBean imageBean :adapter.checkedList){
                    imageBean.setIsChecked(true);
                    originalImageList.add(imageBean);
                    imageList.set(imageBean.getID(),imageBean);
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();

    }
    /*
     * update UI
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            return true;
        }
    });
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_done, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * this function is binding to Menu "Done"
     */
    public void onDone(MenuItem item) {
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.id_selected_img), (Serializable) adapter.checkedList);
        setResult(MainActivity.PICK_IMG, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.id_selected_img), (Serializable) originalImageList);
        setResult(MainActivity.PICK_IMG, intent);
        super.onBackPressed();
    }
}
