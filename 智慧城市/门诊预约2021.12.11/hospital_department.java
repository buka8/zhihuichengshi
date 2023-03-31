package com.example.smartcity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class hospital_department extends AppCompatActivity {
    private List<hosBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_department);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("门诊预约");

        ListView listView = (ListView) findViewById(R.id.hospital_deparment_lv);

        Network network = new Network();

        network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/hospital/category/list", new Callback() {
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

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                String name = jsonArray.getJSONObject(i).getString("categoryName");
                                hosBean bean = new hosBean();
                                bean.setName(name);

                                data.add(bean);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        listView.setAdapter(new hospital_deparmentAdapter(data, hospital_department.this));
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(hospital_department.this,hospital_deparment_detail.class);
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