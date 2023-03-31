package com.example.smartcity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class findhouse_detail extends AppCompatActivity {
private String tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findhouse_detail);

        //返回键
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("找房子");


        ImageView img = (ImageView) findViewById(R.id.findHouse_detail_img);
        TextView textView1 = (TextView) findViewById(R.id.findHouse_detail_name);
        TextView textView2 = (TextView) findViewById(R.id.findHouse_detail_area);
        TextView textView3 = (TextView) findViewById(R.id.findHouse_detail_price);
        TextView textView4 = (TextView) findViewById(R.id.findHouse_detail_type);
        TextView textView5 = (TextView) findViewById(R.id.findHouse_detail_description);
        Button btn1 = (Button) findViewById(R.id.findHouse_detail_mainbtn);
        Button btn2 = (Button) findViewById(R.id.findHouse_detail_bohaobtn);
        //返回找房子主页
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //拨号
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + tel);
                Log.e("c", "onClick: "+data );
                intent.setData(data);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt("value");


        Network network = new Network();
        network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/house/housing/" + position, new Callback() {
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
                            JSONObject jsonObject = new JSONObject(responesData);

                            String adress = jsonObject.getJSONObject("data").getString("pic");
                            Glide.with(findhouse_detail.this)
                                    .load("http://124.93.196.45:10001" +adress)
                                    .into(img);
                            textView1.setText(jsonObject.getJSONObject("data").getString("sourceName"));
                            textView2.setText(jsonObject.getJSONObject("data").getString("areaSize"));
                            textView3.setText(jsonObject.getJSONObject("data").getString("price"));
                            textView4.setText(jsonObject.getJSONObject("data").getString("houseType"));
                            textView5.setText(jsonObject.getJSONObject("data").getString("description"));
                            tel = jsonObject.getJSONObject("data").getString("tel");

                        } catch (JSONException e) {
                            e.printStackTrace();
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