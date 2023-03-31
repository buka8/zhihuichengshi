package com.example.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class bus_one extends AppCompatActivity {

    private TextView mName;
    private TextView mT1;
    private TextView mT2;
    private TextView mT3;
    private TextView mT4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_one);

        //返回键
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("智慧巴士");

        //下一步
        Button btn = (Button)findViewById(R.id.bus_one_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bus_one.this,bus_two.class);
                startActivity(intent);
            }
        });

        mName = (TextView) findViewById(R.id.bus_one_name);
        mT1 = (TextView) findViewById(R.id.bus_one_txt);
        mT2 = (TextView) findViewById(R.id.bus_one_txt2);
        mT3 = (TextView) findViewById(R.id.bus_one_txt3);
        mT4 = (TextView) findViewById(R.id.bus_one_txt4);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt("position")+1;
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        sp.edit().remove("first")
                .remove("end")
                .remove("position")
                .remove("name")
                .remove("price")
                .commit();


        Network network = new Network();
        network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/bus/line/" + position, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(responseData);


                            String name = jsonObject.getJSONObject("data").getString("name");
                            String first = jsonObject.getJSONObject("data").getString("first");
                            String end = jsonObject.getJSONObject("data").getString("end");
                            String price = jsonObject.getJSONObject("data").getString("price");
                            String mileage = jsonObject.getJSONObject("data").getString("mileage");
                            sp.edit().putInt("position",position)
                                    .putString("first",first)
                                    .putString("end",end)
                                    .putString("name",name)
                                    .putString("price",price)
                                    .apply();
                            mName.setText(name);
                            mT1.setText(first);
                            mT2.setText(end);
                            mT3.setText(price);
                            mT4.setText(mileage);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                });


            };
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