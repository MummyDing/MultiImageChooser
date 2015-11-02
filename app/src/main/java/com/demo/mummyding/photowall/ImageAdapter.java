package com.demo.mummyding.photowall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by mummyding on 15-11-2.
 */
public class ImageAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private List<ImageBean> imageBeans;
    private Context mContext;
    private int imageWidth;
    public ImageAdapter(List<ImageBean> imageBeans,Context context) {
        this.imageBeans = imageBeans;
        this.mContext = context;
        imageWidth = Values.ScreenWidth/3;
    }
    public void setList(List<ImageBean> list){
        this.imageBeans =list;
    }

    @Override
    public int getCount() {
        return imageBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return imageBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageBean imageBean = (ImageBean) getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.item_img_grid,null);
            viewHolder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.photo);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageWidth,imageWidth);
        viewHolder.imageView.setLayoutParams(layoutParams);
        viewHolder.imageView.setImageURI(imageBean.getImgUrl());
        if(imageBean.isChecked()){
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setChecked(true);
        }else{
            viewHolder.checkBox.setVisibility(View.GONE);
            viewHolder.checkBox.setChecked(false);
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageBean imageBean = (ImageBean) getItem(position);
        imageBean.setIsChecked(imageBean.isChecked() ? false : true);
        if(PhotoWall.checkedPhoto.contains(imageBean)){
            if(imageBean.isChecked()){
                PhotoWall.checkedPhoto.add(imageBean);
            }else{
                PhotoWall.checkedPhoto.remove(imageBean);
            }
        }else if(imageBean.isChecked()){
            PhotoWall.checkedPhoto.add(imageBean);
        }
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        SimpleDraweeView imageView;
        CheckBox checkBox;
    }
}
