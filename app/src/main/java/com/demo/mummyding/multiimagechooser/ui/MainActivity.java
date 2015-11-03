package com.demo.mummyding.multiimagechooser.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.demo.mummyding.multiimagechooser.R;
import com.demo.mummyding.multiimagechooser.Utils.ScreenUti;
import com.demo.mummyding.multiimagechooser.adapter.ShowImageAdapter;
import com.demo.mummyding.multiimagechooser.model.ImageBean;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView gridView;
    private ShowImageAdapter adapter;
    private final int PICK_IMG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        ScreenUti.init(this);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        gridView = (GridView) findViewById(R.id.image_grid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.text_image_chooser);
    }

    public void chooseImage(View view) {
        Intent intent = new Intent(MainActivity.this,PhotoWallActivity.class);
        startActivityForResult(intent,PICK_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_IMG:
                if(data != null){
                    adapter.setList((List<ImageBean>) data.getSerializableExtra("list"))
                           .notifyDataSetChanged();
                }
                break;
        }
    }
}
