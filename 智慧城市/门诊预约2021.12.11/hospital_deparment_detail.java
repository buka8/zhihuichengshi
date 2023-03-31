package com.example.smartcity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class hospital_deparment_detail extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Fragment mFrag1;
    private Fragment mFrag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_deparment_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("门诊预约");

        init();
        selectTab(0);//默认起始为首页
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(1);
            }
        });
    }

    private void init() {
        btn1 = (Button) findViewById(R.id.hos_deparment_debtn1);
        btn2 = (Button) findViewById(R.id.hos_deparment_debtn2);
    }


    private void selectTab(int i) {
        FragmentManager manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        if (mFrag1!=null){
            transaction.hide(mFrag1);
        }
        if (mFrag2!=null){
            transaction.hide(mFrag2);
        }

        switch (i) {
            //当选中的点击的是第一页的Tab时
            case 0:
                if (mFrag1 == null) {
                    mFrag1 = new hos_deparment_detail_fragment();
                    transaction.add(R.id.hospital_deparment_detailC, mFrag1);
                } else {
                    //如果第一页对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFrag1);
                }
                break;
            case 1:
                if (mFrag2 == null) {
                    mFrag2 = new hos_deparment_detail_fragment2();
                    transaction.add(R.id.hospital_deparment_detailC, mFrag2);
                } else {
                    //如果第一页对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFrag2);
                }
                break;
        }
        transaction.commit();
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