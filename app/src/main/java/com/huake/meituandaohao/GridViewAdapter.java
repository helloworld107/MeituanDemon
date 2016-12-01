package com.huake.meituandaohao;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhangxutong on 2016/2/16.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<HeaderViewBean> mDatas;
    private LayoutInflater mLayoutInflater;
    /**
     * 页数下标,从0开始(通俗讲第几页)
     */
    private int mIndex;
    /**
     * 每页显示最大条目个数 ,默认是dimes.xml里 HomePageHeaderColumn 属性值的两倍(每页多少个图标)
     */
    private int mPageSize;

    public GridViewAdapter(Context context, List<HeaderViewBean> mDatas, int mIndex) {
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mIndex = mIndex;
        mPageSize = context.getResources().getInteger(R.integer.HomePageHeaderColumn) * 2;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (mIndex+1)*mPageSize,
     * 如果够，则直接返回每一页显示的最大条目个数mPageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - mIndex * mPageSize);(说白了 最后一页就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (mIndex + 1) * mPageSize ? mPageSize : (mDatas.size() - mIndex * mPageSize);

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + mIndex * mPageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("TAG", "position:" + position+"   :"+this);
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_gridview_header, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.textView);
            vh.iv = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + mIndex * mPageSize，
         */
        int pos = position + mIndex * mPageSize;
        vh.tv.setText(mDatas.get(pos).name);
        vh.iv.setImageResource(mDatas.get(pos).iconRes);
        return convertView;
    }


    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}