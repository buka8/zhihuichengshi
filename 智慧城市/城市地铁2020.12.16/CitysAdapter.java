package com.example.atest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CitysAdapter extends RecyclerView.Adapter<CitysAdapter.ViewHolder> {

    private List<CitysBean> mCitysBeanList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View view) {
            super(view);
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }
    public CitysAdapter(List<CitysBean> citysBeanList) {
        mCitysBeanList = citysBeanList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citys_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CitysBean citysBean = mCitysBeanList.get(position);
        holder.fruitImage.setImageResource(citysBean.getImageId());
        holder.fruitName.setText(citysBean.getName());
    }
    @Override
    public int getItemCount() {
        return mCitysBeanList.size();
    }
}


