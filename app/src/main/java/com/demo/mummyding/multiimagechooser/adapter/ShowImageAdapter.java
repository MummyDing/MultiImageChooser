package com.demo.mummyding.multiimagechooser.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.mummyding.multiimagechooser.R;
import com.demo.mummyding.multiimagechooser.adapter.BasicAdapter;
import com.demo.mummyding.multiimagechooser.model.ImageBean;
import com.demo.mummyding.multiimagechooser.ui.ImageDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by mummyding on 15-11-3.
 */
public class ShowImageAdapter extends BasicAdapter {
    public ShowImageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageBean imageBean= (ImageBean) getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.item_show_image,null);
            viewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.image);
            viewHolder.close_btn = (ImageView) convertView.findViewById(R.id.close_btn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setLayoutParams(frameParams);
        viewHolder.image.setImageURI(Uri.parse(imageBean.getImageUri()));
        viewHolder.close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList().remove(position);
                updateUI();
            }
        });
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageDetailsActivity.class);
                intent.putExtra("imageUri", imageBean.getImageUri().toString());
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, ImageDetailsActivity.class);
        intent.putExtra("imageUri",((ImageBean)getItem(position)).getImageUri().toString());
        mContext.startActivity(intent);
    }

    class ViewHolder{
        SimpleDraweeView image;
        ImageView close_btn;
    }


}
