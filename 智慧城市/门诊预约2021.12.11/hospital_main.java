package com.example.smartcity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class hospital_main extends AppCompatActivity {
    private List<hosBean> data1 = new ArrayList<>();
    private List<hosBean> data2 = new ArrayList<>();
    private boolean flag = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_main);

        //返回键
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("门诊预约");



        ListView listView = (ListView) findViewById(R.id.hospital_lv);


        Network network = new Network();



        network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/hospital/hospital/list", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray jsonArray = network.useShowResponse(responseData);
                        listView.setAdapter(new hospitalAdapeter(data1,hospital_main.this));//医院列表适配器
                        for (int i =0;i<jsonArray.length();i++){
                            try {
                                String img = jsonArray.getJSONObject(i).getString("imgUrl");
                                String name = jsonArray.getJSONObject(i).getString("hospitalName");
                                int num = jsonArray.getJSONObject(i).getInt("level");
                                hosBean bean = new hosBean();
                                bean.setImg(img);
                                bean.setName(name);
                                bean.setNum(num);

                                data1.add(bean);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

        //医院详情跳转界面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(hospital_main.this,hospital_detail.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
