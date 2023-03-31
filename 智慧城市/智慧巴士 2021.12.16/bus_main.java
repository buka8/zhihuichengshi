package com.example.smartcity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class bus_main extends AppCompatActivity {

    private ListView mListView;

    private Network network;

    private List<busBean> data = new ArrayList<>();
    private List<bus_siteBean> data1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_main);

        //返回键
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("智慧巴士");

        //页面列表
        mListView = (ListView) findViewById(R.id.bus_lv);
        show();

        //长按事件
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                show1(id+1);
                return true;
            }
        });

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(bus_main.this,bus_one.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }




    private void show() {
        network = new Network();
        network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/bus/line/list", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        JSONArray jsonArray = network.useShowResponse(responseData);
                        mListView.setAdapter(new busAdapter(data,bus_main.this));//适配器

                        for (int i =0;i<jsonArray.length();i++){
                            try {
                                String name = jsonArray.getJSONObject(i).getString("name");
                                String first = jsonArray.getJSONObject(i).getString("first");
                                String end = jsonArray.getJSONObject(i).getString("end");
                                String startTime = jsonArray.getJSONObject(i).getString("startTime");
                                String endTime = jsonArray.getJSONObject(i).getString("endTime");
                                String price = jsonArray.getJSONObject(i).getString("price");
                                String mileage = jsonArray.getJSONObject(i).getString("mileage");

                                busBean bean = new busBean();

                                bean.setName(name);
                                bean.setFirst(first);
                                bean.setEnd(end);
                                bean.setStartTime(startTime);
                                bean.setEndTime(endTime);
                                bean.setPrice(price);
                                bean.setMileage(mileage);
                                data.add(bean);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });


            }
        });

    }

    private void show1( long id) {

        network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/bus/stop/list?linesId="+id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data1.clear();
                        JSONArray jsonArray = network.useShowResponse(responseData);

                        final LinearLayout layout_alert = (LinearLayout) getLayoutInflater().inflate(R.layout.bus_listview_site, null);
                        ListView site_lv = (ListView) layout_alert.findViewById(R.id.bus_site_lv);
                        AlertDialog.Builder builder = new AlertDialog.Builder(bus_main.this);
                        builder.setView(layout_alert)
                                .create()
                                .show();

                        site_lv.setAdapter(new bus_siteAdapter(data1,bus_main.this));//适配器

                        for (int i =0;i<jsonArray.length();i++){
                            try {
                                String name = jsonArray.getJSONObject(i).getString("name");
                                bus_siteBean bean = new bus_siteBean();
                                bean.setName(name);
                                data1.add(bean);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });


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