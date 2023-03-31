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

public class findhouseAdapter extends BaseAdapter {
    private List<findhouseBean> data;
    private Context context;

    public findhouseAdapter(List<findhouseBean> data, Context context) {
        this.data = data;
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.findhouse_listview_item, parent, false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.findHouse_item_imageView);
        viewHolder.textView1 = (TextView) convertView.findViewById(R.id.findHouse_item_textView);
        viewHolder.textView2 = (TextView) convertView.findViewById(R.id.findHouse_item_textView2);
        viewHolder.textView3 = (TextView) convertView.findViewById(R.id.findHouse_item_textView3);
        viewHolder.textView4 = (TextView) convertView.findViewById(R.id.findHouse_item_textView4);

        viewHolder.textView1.setText(data.get(position).getTitle());
        viewHolder.textView2.setText("面积："+data.get(position).getArea()+"平方米");
        viewHolder.textView3.setText("价格："+data.get(position).getPrice());
        viewHolder.textView4.setText( "详情："+data.get(position).getDescription());
        Glide.with(context)
                .load("http://124.93.196.45:10001" + data.get(position).getImg())
                .into(viewHolder.imageView);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }
}
