package com.demo.mummyding.multiimagechooser.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Toast;

import com.demo.mummyding.multiimagechooser.R;
import com.demo.mummyding.multiimagechooser.model.ImageBean;
import com.demo.mummyding.multiimagechooser.ui.ImageDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-11-3.
 */
public class SelectImageAdapter extends BasicAdapter implements AdapterView.OnItemClickListener{
    public List<ImageBean> checkedList = new ArrayList<>();

    public SelectImageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       // Log.d("giiii",position+"");

        ViewHolder viewHolder = new ViewHolder();
        final ImageBean imageBean = (ImageBean) getItem(position);
        Log.d("giiii", imageBean.getID() + "");
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_choose_image,null);
            viewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.image);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
           // viewHolder.image.setOnClickListener(this);
            viewHolder.checkBox.setChecked(imageBean.isChecked());

            //viewHolder.checkBox.setChecked(imageBean.isChecked());
            viewHolder.checkBox.setTag(position);
//            viewHolder.checkBox.setTag(convertView.getId());
          //  viewHolder.checkBox.setOnCheckedChangeListener(this);
            //viewHolder.checkBox.setId(position);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                imageBean.setIsChecked(isChecked);
                if(checkedList.contains(imageBean)){
                    if(imageBean.isChecked()==false) {
                        checkedList.remove(imageBean);
                    }
                }else if(imageBean.isChecked()){
                    checkedList.add(imageBean);
                }
                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("选择图片("+checkedList.size()+")");
             }
        });
        viewHolder.checkBox.setChecked(imageBean.isChecked());
        viewHolder.image.setLayoutParams(frameParams);
        viewHolder.image.setImageURI(imageBean.getImageUri());
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Log.d("onitem",position+"");
        Intent intent = new Intent(mContext, ImageDetailsActivity.class);
        intent.putExtra("imageUri",((ImageBean)getItem(position)).getImageUri().toString());
        mContext.startActivity(intent);
    }

    class ViewHolder{
        SimpleDraweeView image;
        CheckBox checkBox;
    }
}
