package com.example.smartcity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class hospital_detail extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_detail_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("门诊预约");

        Network network = new Network();
        ImageView img = findViewById(R.id.hospital_detail_img);
        TextView tv = findViewById(R.id.hospital_detail_text);
        Button btn = findViewById(R.id.hospital_detail_btn);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt("position");
        Log.e("cj", "position: "+position );

        network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/hospital/hospital/list", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responesData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONArray jsonArray = network.useShowResponse(responesData);
                            Log.e("cj", "run: "+jsonArray );
                            String imgUrl = jsonArray.getJSONObject(position).getString("imgUrl");
                            tv.setText("简介："+jsonArray.getJSONObject(position).getString("brief"));
                            Glide.with(getApplicationContext())
                                    .load("http://124.93.196.45:10001"+imgUrl)
                                    .into(img);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(hospital_detail.this,hosoital_jiuZhenCard.class);
                startActivity(intent1);
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
