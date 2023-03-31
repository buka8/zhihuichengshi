package com.example.smartcity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class bus_siteAdapter extends BaseAdapter {
    private List<bus_siteBean> data;
    private Context mContext;


    public bus_siteAdapter(List<bus_siteBean> data, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bus_site_item, parent, false);

            viewHolder.mTextView1 = (TextView) convertView.findViewById(R.id.bus_site_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTextView1.setText(data.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView mTextView1;
    }
}
