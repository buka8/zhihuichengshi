package com.example.smartcity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class busAdapter extends BaseAdapter {
    private List<busBean> data;
    private Context mContext;


    public busAdapter(List<busBean> data, Context context) {
        this.data = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return data.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bus_item, parent, false);

            viewHolder.mTextView1 = (TextView) convertView.findViewById(R.id.bus_item1);
            viewHolder.mTextView2 = (TextView) convertView.findViewById(R.id.bus_item2);
            viewHolder.mTextView3 = (TextView) convertView.findViewById(R.id.bus_item3);
            viewHolder.mTextView4 = (TextView) convertView.findViewById(R.id.bus_item4);
            viewHolder.mTextView5 = (TextView) convertView.findViewById(R.id.bus_item5);
            viewHolder.mTextView6 = (TextView) convertView.findViewById(R.id.bus_item6);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTextView1.setText(data.get(position).getName());
        viewHolder.mTextView2.setText(data.get(position).getEnd());
        viewHolder.mTextView3.setText(data.get(position).getFirst());

        String runTime = data.get(position).getStartTime()+"~"+data.get(position).getEndTime();
        viewHolder.mTextView4.setText(runTime);

        viewHolder.mTextView5.setText(data.get(position).getPrice());
        viewHolder.mTextView6.setText(data.get(position).getMileage());
        return convertView;
    }

    static class ViewHolder {
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        TextView mTextView4;
        TextView mTextView5;
        TextView mTextView6;
    }
}
