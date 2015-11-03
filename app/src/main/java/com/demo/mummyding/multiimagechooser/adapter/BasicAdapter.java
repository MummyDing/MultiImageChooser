package com.demo.mummyding.multiimagechooser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.demo.mummyding.multiimagechooser.model.ImageBean;

import java.util.List;

/**
 * Created by mummyding on 15-11-3.
 */
public abstract class BasicAdapter extends BaseAdapter implements View.OnClickListener{
    private List<ImageBean> mList;
    private Context mContext;
    public BaseAdapter setList(List<ImageBean> list){
        this.mList = list;
        return this;
    }
    public BaseAdapter updateUI(){
        this.notifyDataSetChanged();
        return this;
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
        return 0;
    }
}
