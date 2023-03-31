package com.example.smartcity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class bus_fort extends AppCompatActivity {
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;
    private TextView mTextView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_fort);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("智慧巴士");


        initView();

        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        mTextView1.setText(sp.getString("name",null));
        mTextView2.setText(sp.getString("tel1",null));
        mTextView3.setText(sp.getString("shang",null));
        mTextView4.setText(sp.getString("xia",null));
        mTextView5.setText(sp.getString("time",null));
        mTextView6.setText(sp.getString("price",null));


        //提交订单
        Button button = (Button)findViewById(R.id.bus_fort_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                OkHttpClient client = new OkHttpClient();

                JSONObject json = new JSONObject();
                try {
                    json.put("start",sp.getString("shang",null));
                    json.put("end",sp.getString("xia",null));
                    json.put("path",sp.getString("name",null));
                    json.put("price",sp.getString("price",null));
                    json.put("status",0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String token = sp.getString("token",null);

                RequestBody requestBody = RequestBody.create(JSON,json.toString());
                Request request = new Request.Builder()
                        .url("http://124.93.196.45:10001/prod-api/api/bus/order")
                        .post(requestBody)
                        .addHeader("Authorization",token)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
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

                                    Log.e("ccc", "返回结果: "+jsonObject );
                                    Toast.makeText(bus_fort.this,"提交成功",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(bus_fort.this,MainActivity.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });

            }
        });



    }

    private void initView() {
        mTextView1 = (TextView) findViewById(R.id.bus_fort_txt);
        mTextView2 = (TextView) findViewById(R.id.bus_fort_txt2);
        mTextView3 = (TextView) findViewById(R.id.bus_fort_txt3);
        mTextView4 = (TextView) findViewById(R.id.bus_fort_txt4);
        mTextView5 = (TextView) findViewById(R.id.bus_fort_txt5);
        mTextView6 = (TextView) findViewById(R.id.bus_fort_txt6);
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