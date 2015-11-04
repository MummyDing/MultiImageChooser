package com.demo.mummyding.multiimagechooser.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.demo.mummyding.multiimagechooser.R;
import com.demo.mummyding.multiimagechooser.adapter.BasicAdapter;
import com.demo.mummyding.multiimagechooser.model.ImageBean;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by mummyding on 15-11-3.
 */
public class ShowImageAdapter extends BasicAdapter {
    public ShowImageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageBean imageBean= (ImageBean) getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.item_show_image,null);
            viewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.image);
            viewHolder.close_btn = (ImageButton) convertView.findViewById(R.id.close_btn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setLayoutParams(frameParams);
        viewHolder.image.setImageURI(Uri.parse(imageBean.getImageUri()));
        viewHolder.close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"test",Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mContext,"itemo",Toast.LENGTH_SHORT).show();
    }

    class ViewHolder{
        SimpleDraweeView image;
        ImageButton close_btn;
    }


}
