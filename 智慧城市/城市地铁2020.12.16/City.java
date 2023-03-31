package com.example.atest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class City extends AppCompatActivity {

    EditText et1;
    private List<CityBean> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);

        ActionBar action_bar = getSupportActionBar();
        action_bar.setDisplayHomeAsUpEnabled(true);
        action_bar.setTitle("城市地铁");

        et1=(EditText) findViewById(R.id.et1);

        CityAdapter adapter = new CityAdapter(City.this, R.layout.city_item, data);	//声明适配器
        ListView list_view = (ListView) findViewById(R.id.list_item);

        String str="http://124.93.196.45:10001/prod-api/api/metro/list?currentName=建国门";
        NetRequest netRequest=new NetRequest();
        netRequest.useSendRequestWithOkHttp(str, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str1=response.body().string();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(str1);

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            initCity(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        list_view.setAdapter(adapter);

                    }
                });
            }
        });

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CityBean cityBean=data.get(position);

                Intent intent=new Intent(City.this,CityPage.class);
                intent.putExtra("lineName",cityBean.getCity_lineName());
                intent.putExtra("lineId",cityBean.getCity_lineId());
                startActivity(intent);
            }
        });


    }

    private void initCity(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                CityBean one = new CityBean(
                        jsonArray.getJSONObject(i).getString("lineId"),
                        jsonArray.getJSONObject(i).getString("lineName"),
                        jsonArray.getJSONObject(i).getJSONObject("nextStep").getString("name"),
                        jsonArray.getJSONObject(i).getString("reachTime"));
                data.add(one);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return false;
            case R.id.metro:
                Intent intent=new Intent(City.this,CityMetro.class);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }
}
