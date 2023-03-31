package com.example.atest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CityAdapter extends ArrayAdapter<CityBean> {

    private int resource_id;

    public CityAdapter(@NonNull Context context, int resource, @NonNull List<CityBean> objects) {

        super(context, resource, objects);

        resource_id = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CityBean city_bean = getItem(position);
        View view;
        ViewHolder view_holder;

        if (convertView == null){

            view = LayoutInflater.from(getContext()). inflate(resource_id, parent, false);

            view_holder = new ViewHolder();

            view_holder.city_lineId = (TextView) view.findViewById(R.id.city_lineId);
            view_holder.city_lineName = (TextView) view.findViewById(R.id.city_lineName);
            view_holder.city_nextStep = (TextView) view.findViewById(R.id.city_nextStep);
            view_holder.city_reachTime = (TextView) view.findViewById(R.id.city_reachTime);

            view.setTag(view_holder);

        }else{
            view = convertView;

            view_holder = (ViewHolder) view.getTag();
        }

        view_holder.city_lineId.setText(city_bean.getCity_lineId());
        view_holder.city_lineName.setText(city_bean.getCity_lineName());
        view_holder.city_nextStep.setText(city_bean.getCity_nextStep());
        view_holder.city_reachTime.setText(city_bean.getCity_reachTime());

        return view;
    }

    class ViewHolder{
        TextView city_lineId;
        TextView city_lineName;
        TextView city_nextStep;
        TextView city_reachTime;
    }
}
