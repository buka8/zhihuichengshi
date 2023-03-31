package com.example.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class hosoital_jiuZhenCard extends AppCompatActivity {

    private TextView mName;
    private TextView mSex;
    private TextView mPhone;
    private LinearLayout mLbtn;
    private Button mBtn;
    private TextView mIdcard;
    private TextView mBirthday;
    private TextView mAddress;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_jiuzhencard);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("门诊预约");

        initView();
        SharedPreferences sp = getSharedPreferences("information", MODE_PRIVATE);
        mName.setText(sp.getString("name", null));
        mSex.setText(sp.getString("sex", null));
        mPhone.setText(sp.getString("phone", null));

        //初始化事件
        Intent intent = getIntent();
        String idcard = intent.getStringExtra("idcard");
        if (idcard != null){
            initEvent(idcard);
        }

        mLbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hosoital_jiuZhenCard.this, hospital_jiuZhenCard_information.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", sp.getString("name", null));
                bundle.putString("sex", sp.getString("sex", null));
                bundle.putString("phone", sp.getString("phone", null));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(hosoital_jiuZhenCard.this,hospital_department.class);
                startActivity(intent1);
            }
        });



    }

    private void initEvent(String idcard) {
        OkHttpClient client = new OkHttpClient();
        SharedPreferences sp1 = getSharedPreferences("user", MODE_PRIVATE);
        Request request = new Request.Builder()
                .url("http://124.93.196.45:10001/prod-api/api/hospital/patient/list?cardId="+idcard)
                .addHeader("Authorization",sp1.getString("token",null))
                .build();

        client.newCall(request).enqueue(new Callback() {
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
                            JSONArray jsonArray = jsonObject.getJSONArray("rows");

                            mName.setText(jsonArray.getJSONObject(0).getString("name"));
                            mSex.setText(jsonArray.getJSONObject(0).getString("sex"));
                            mPhone.setText(jsonArray.getJSONObject(0).getString("tel"));
                            mIdcard.setText(jsonArray.getJSONObject(0).getString("cardId"));
                            mBirthday.setText(jsonArray.getJSONObject(0).getString("birthday"));
                            mAddress.setText(jsonArray.getJSONObject(0).getString("address"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        mName = (TextView) findViewById(R.id.hospital_jiuzhenCard_name);
        mSex = (TextView) findViewById(R.id.hospital_jiuzhenCard_sex);
        mPhone = (TextView) findViewById(R.id.hospital_jiuzhenCard_phone);
        mIdcard = (TextView) findViewById(R.id.hospital_jiuzhenCard_idCard);
        mBirthday = (TextView) findViewById(R.id.hospital_jiuzhenCard_birthday);
        mAddress = (TextView) findViewById(R.id.hospital_jiuzhenCard_address);
        mLbtn = (LinearLayout) findViewById(R.id.hospital_jiuzhenCardBtn);
        mBtn = (Button) findViewById(R.id.hospital_jiuzhenCard_btn);
        mImageView = (ImageView) findViewById(R.id.hospital_jiuzhenCard_department);
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
