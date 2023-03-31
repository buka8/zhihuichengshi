package com.example.smartcity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class bus_two extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_two);

        //返回键
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("智慧巴士");

        //下一步
        Button btn = (Button)findViewById(R.id.bus_two_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bus_two.this,bus_three.class);
                startActivity(intent);
            }
        });

        DatePicker time = (DatePicker) findViewById(R.id.bus_two_time);
        TextView txt = (TextView) findViewById(R.id.bus_two_txt);

        //要求api26 或以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            time.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    String result = year+"-"+monthOfYear+"-"+dayOfMonth;
                    txt.setText(result);

                    SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);

                    sp.edit().remove("time")
                            .commit();

                    sp.edit().putString("time",result)
                            .apply();

                }
            });
        }else {
            Toast.makeText(bus_two.this,"版本不够，无法获取数据...",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}