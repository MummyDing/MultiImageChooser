package com.demo.mummyding.multiimagechooser.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;

import com.bm.library.PhotoView;
import com.demo.mummyding.multiimagechooser.R;
import com.demo.mummyding.multiimagechooser.Utils.ScreenUtil;
import com.polites.android.GestureImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LayoutParams layoutParams = null;
    private PhotoView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        initData();
    }
    private void initData(){
        image = (PhotoView) findViewById(R.id.source_img);
        image.enable();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.text_image);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        layoutParams = new LayoutParams(ScreenUtil.getScreenWidth(), ScreenUtil.getScreenHeight()-toolbar.getHeight());

        Intent intent = getIntent();
        image.setImageBitmap(getBitmapFromUri(Uri.parse(intent.getStringExtra(getString(R.string.id_image_uri)))));
        image.setLayoutParams(layoutParams);
    }
    /*
     * Get  thumbnails from Uri
     */
    private Bitmap getBitmapFromUri(Uri uri)
    {
        Bitmap resizedBitmap = null;
        BitmapFactory.Options outBitmap = new BitmapFactory.Options();
        outBitmap.inJustDecodeBounds = false; // the decoder will return a bitmap
        try {
            BitmapFactory.Options outDimens = getBitmapDimensions(uri);
            outBitmap.inSampleSize = outDimens.outWidth/ ScreenUtil.getScreenWidth();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            resizedBitmap = BitmapFactory.decodeStream(is, null, outBitmap);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resizedBitmap;
    }
    /*
     * Get size of the bitmap
     */
    private BitmapFactory.Options getBitmapDimensions(Uri uri) throws FileNotFoundException, IOException {
        BitmapFactory.Options outDimens = new BitmapFactory.Options();
        outDimens.inJustDecodeBounds = true; // the decoder will return null (no bitmap)
        InputStream is= getContentResolver().openInputStream(uri);
        // if Options requested only the size will be returned
        BitmapFactory.decodeStream(is, null, outDimens);
        is.close();

        return outDimens;
    }
}
