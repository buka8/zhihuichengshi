package com.example.atest;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class JianCheMainThree extends Fragment implements View.OnClickListener{
    View view;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.jian_che_main_three, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String cp = pref.getString("cp", "");
        String cj = pref.getString("cj", "");
        String cl = pref.getString("cl", "");
        String gl = pref.getString("gl", "");
        String sj = pref.getString("sj", "");
        String s = pref.getString("s", "");
        String dizhi = pref.getString("dizhi", "");
        String ri = pref.getString("ri", "");

        textView=view.findViewById(R.id.tv_wd);
        textView.setText("车牌号："+cp+"\n"+"车架号："+cj+"\n"+"车辆类型："
                +cl+"\n"+"公里数："+gl+"\n"+"手机号:"+sj+"\n"+"地址:"+dizhi+"\n"+"日期:"+ri+"\n"+"时间:"+s);

        setHasOptionsMenu(true);
        return view;
    }





    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add("我的预约").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public void onClick(View v) {

    }
}