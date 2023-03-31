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
import android.widget.EditText;
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

public class hospital_jiuZhenCard_information extends AppCompatActivity {
    EditText ifmt_name;
    EditText ifmt_sex;
    EditText ifmt_idcard;
    EditText ifmt_birthday;
    EditText ifmt_address;
    EditText ifmt_phone;
    Button ifmt_define;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_jiuzhencard_information);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("门诊预约");

        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        String sex = bundle.getString("sex");
        String phone = bundle.getString("phone");

        ifmt_name.setText(name);
        ifmt_sex.setText(sex);
        ifmt_phone.setText(phone);



        //提交
        OkHttpClient client = new OkHttpClient();
        MediaType type = MediaType.parse("application/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        String sexValue;
        if (ifmt_sex.getText().toString().equals("男")) {
            sexValue = "0";
        } else {
            sexValue = "1";
        }
        try {
            jsonObject.put("address", ifmt_address.getText().toString());
            jsonObject.put("birthday", ifmt_birthday.getText().toString());
            jsonObject.put("cardId", ifmt_idcard.getText().toString());
            jsonObject.put("name", ifmt_name.getText().toString());
            jsonObject.put("sex", sexValue);
            jsonObject.put("tel", ifmt_phone.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(type,jsonObject.toString());
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        Request request = new Request.Builder()
                .url("http://124.93.196.45:10001/prod-api/api/hospital/patient")
                .post(requestBody)
                .addHeader("Authorization",sp.getString("token",null))
                .build();

        ifmt_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(hospital_jiuZhenCard_information.this,"提交失败",Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(hospital_jiuZhenCard_information.this,"提交成功！",Toast.LENGTH_SHORT).show();

                                try {
                                    JSONObject jsonObject = new JSONObject(responseData);
                                    Log.d("c", "onResponse: "+jsonObject );
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Intent intent1 = new Intent(hospital_jiuZhenCard_information.this
                                        ,hosoital_jiuZhenCard.class);
                                intent1.putExtra("idcard",ifmt_idcard.getText().toString());
                                startActivity(intent1);
                                finish();
                            }
                        });


                    }
                });

            }
        });

    }

    private void initView() {
        ifmt_name = (EditText) findViewById(R.id.hospital_information_name);
        ifmt_sex = (EditText) findViewById(R.id.hospital_information_sex);
        ifmt_idcard = (EditText) findViewById(R.id.hospital_information_idcard);
        ifmt_birthday = (EditText) findViewById(R.id.hospital_information_birthday);
        ifmt_address = (EditText) findViewById(R.id.hospital_information_address);
        ifmt_phone = (EditText) findViewById(R.id.hospital_information_phone);
        ifmt_define = (Button) findViewById(R.id.hospital_information_define);
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