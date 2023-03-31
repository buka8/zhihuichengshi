package com.example.smartcity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class bus_three extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private EditText mEditText;
    private EditText mEditText2;
    private EditText mEditText3;
    private EditText mEditText4;
    private List<bus_siteBean> data1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_three);

        //返回键
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("智慧巴士");


        initView();
        Network network = new Network();

        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        String first = sp.getString("first",null);
        String end = sp.getString("end",null);
        int position = sp.getInt("position",0);
        mTextView1.setText(first);
        mTextView2.setText(end);


        //下一步
        Button btn = (Button)findViewById(R.id.bus_three_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().remove("name")
                        .remove("tel1")
                        .remove("shang")
                        .remove("xia")
                        .commit();
                sp.edit().putString("name",mEditText.getText().toString())
                        .putString("tel1",mEditText2.getText().toString())
                        .putString("shang",mEditText3.getText().toString())
                        .putString("xia",mEditText4.getText().toString())
                        .apply();

                Intent intent = new Intent(bus_three.this,bus_fort.class);
                startActivity(intent);
            }
        });

        mTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/bus/stop/list?linesId="+position, new Callback() {
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(bus_three.this);
                                builder.setView(layout_alert)
                                        .create()
                                        .show();

                                site_lv.setAdapter(new bus_siteAdapter(data1,bus_three.this));//适配器

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
        });



    }

    private void initView() {
        mTextView1 = (TextView) findViewById(R.id.bus_three_txt);
        mTextView2 = (TextView) findViewById(R.id.bus_three_txt2);
        mTextView3 = (TextView) findViewById(R.id.bus_three_txt3);
        mEditText = (EditText) findViewById(R.id.bus_three_etxt);
        mEditText2 = (EditText) findViewById(R.id.bus_three_etxt2);
        mEditText3 = (EditText) findViewById(R.id.bus_three_etxt3);
        mEditText4 = (EditText) findViewById(R.id.bus_three_etxt4);
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