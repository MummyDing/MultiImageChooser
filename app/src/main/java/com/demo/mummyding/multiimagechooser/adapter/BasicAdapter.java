package com.demo.mummyding.multiimagechooser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.demo.mummyding.multiimagechooser.Utils.ScreenUti;
import com.demo.mummyding.multiimagechooser.model.ImageBean;

import java.util.List;

/**
 * Created by mummyding on 15-11-3.
 */
public abstract class BasicAdapter extends BaseAdapter implements View.OnClickListener{
    protected List<ImageBean> mList;
    protected Context mContext;
    protected static int imageWidth;
    protected static LinearLayout.LayoutParams linearParams;
    protected static FrameLayout.LayoutParams frameParams;
    static {
        imageWidth = ScreenUti.getScreenWidth()/3;
        linearParams = new LinearLayout.LayoutParams(imageWidth,imageWidth);
        frameParams = new FrameLayout.LayoutParams(imageWidth,imageWidth);
    }

    public BasicAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BaseAdapter setList(List<ImageBean> list){
        this.mList = list;
        return this;
    }
    public BaseAdapter updateUI(){
        this.notifyDataSetChanged();
        return this;
    }
    public List<ImageBean> getList(){
        return mList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getID();
    }
}
