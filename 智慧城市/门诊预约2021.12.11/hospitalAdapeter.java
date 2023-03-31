package com.example.smartcity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class hospitalAdapeter extends BaseAdapter {
    private List<hosBean> data1;
    private Context mContext;

    public hospitalAdapeter(List<hosBean> data, Context context) {
        this.data1 = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return data1.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hospital_listview_item, parent, false);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.hospital_item_img);
            viewHolder.mTextView1 = (TextView) convertView.findViewById(R.id.hospital_item_name);
            viewHolder.mTextView2 = (TextView)convertView.findViewById(R.id.hospital_item_xin);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTextView1.setText(data1.get(position).getName());
        viewHolder.mTextView2.setText("星级："+data1.get(position).getNum());
        Glide.with(convertView)
                .load("http://124.93.196.45:10001"+data1.get(position).getImg())
                .into(viewHolder.mImageView);

        return convertView;
    }

    static class ViewHolder {
        ImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;
    }
}
