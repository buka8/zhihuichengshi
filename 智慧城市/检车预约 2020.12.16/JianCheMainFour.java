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
import android.widget.Button;
import android.widget.EditText;


public class JianCheMainFour extends Fragment implements View.OnClickListener{
View view;
    Button bt;
    EditText edt1,edt2,edt3,edt4,edt5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.jian_che_main_four, container, false);

        this.edt1=view.findViewById(R.id.gledt_1);
        this.edt2=view.findViewById(R.id.gledt_2);
        this.edt3=view.findViewById(R.id.gledt_3);
        this.edt4=view.findViewById(R.id.gledt_4);
        this.edt5=view.findViewById(R.id.gledt_5);

//        tv=view.findViewById(R.id.lj_tv);
        bt=view.findViewById(R.id.glbt_jia);
        bt.setOnClickListener(this);

        setHasOptionsMenu(true);
        return view;

    }





    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add("车辆管理").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public void onClick(View v) {
        String cp=edt1.getText().toString();
        String cj=edt2.getText().toString();
        String cl=edt3.getText().toString();
        String gl=edt4.getText().toString();
        String sj=edt5.getText().toString();

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("cp", cp);
        editor.putString("cj", cj);
        editor.putString("cl", cl);
        editor.putString("gl", gl);
        editor.putString("sj", sj);
        editor.apply();
    }
}