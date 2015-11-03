package com.demo.mummyding.multiimagechooser.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.demo.mummyding.multiimagechooser.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        gridView = (GridView) findViewById(R.id.image_grid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.text_image_chooser);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
