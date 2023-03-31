package com.example.atest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CityPage extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5;
    String lineName,lineId;

    private List<CitysBean> citysBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_page);

        tv1=(TextView) findViewById(R.id.tv_first);
        tv2=(TextView) findViewById(R.id.tv_end);
        tv3=(TextView) findViewById(R.id.tv_time);
        tv4=(TextView) findViewById(R.id.tv_number);
        tv5=(TextView) findViewById(R.id.tv_km);

        initFruits();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        CitysAdapter adapter = new CitysAdapter(citysBeanList);
        recyclerView.setAdapter(adapter);



        Intent intent = getIntent();
        lineName = intent.getStringExtra("lineName");
        lineId = intent.getStringExtra("lineId");

        ActionBar action_bar = getSupportActionBar();
        action_bar.setDisplayHomeAsUpEnabled(true);
        action_bar.setTitle(lineName);

        Log.d("ccc", "onCreate: "+lineId);
        String url="http://124.93.196.45:10001/prod-api/api/metro/line/"+lineId;
        NetRequest netRequest=new NetRequest();
        netRequest.useSendRequestWithOkHttp(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(str);

                            String f1 = jsonObject.getJSONObject("data").getString("first");
                            String e1 = jsonObject.getJSONObject("data").getString("end");
                            String t1 = jsonObject.getJSONObject("data").getString("startTime");
                            String n1 = jsonObject.getJSONObject("data").getString("stationsNumber");
                            String k1 = jsonObject.getJSONObject("data").getString("km");

                            tv1.setText(f1);
                            tv2.setText(e1);
                            tv3.setText(t1);
                            tv4.setText(n1);
                            tv5.setText(k1);
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
            case android.R.id.home:
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            CitysBean apple = new CitysBean("Apple", R.drawable.ic_launcher_foreground);
            citysBeanList.add(apple);
            CitysBean banana = new CitysBean("Banana", R.drawable.ic_launcher_foreground);
            citysBeanList.add(banana);
            CitysBean orange = new CitysBean("Orange", R.drawable.ic_launcher_foreground);
            citysBeanList.add(orange);
            CitysBean watermelon = new CitysBean("Watermelon", R.drawable.ic_launcher_background);
            citysBeanList.add(watermelon);
            CitysBean pear = new CitysBean("Pear", R.drawable.ic_launcher_foreground);
            citysBeanList.add(pear);
            CitysBean grape = new CitysBean("Grape", R.drawable.ic_launcher_foreground);
            citysBeanList.add(grape);
            CitysBean pineapple = new CitysBean("Pineapple", R.drawable.ic_launcher_foreground);
            citysBeanList.add(pineapple);
            CitysBean strawberry = new CitysBean("Strawberry", R.drawable.ic_launcher_foreground);
            citysBeanList.add(strawberry);
            CitysBean cherry = new CitysBean("Cherry", R.drawable.ic_launcher_foreground);
            citysBeanList.add(cherry);
            CitysBean mango = new CitysBean("Mango", R.drawable.ic_launcher_foreground);
            citysBeanList.add(mango);
        }
    }
//     * TextView 显示高亮
//     * @param str1  要高亮显示的文字（输入的关键词）
//     * @param str2  包含高亮文字的字符串

//    private void setTextHighLight(TextView textView, String str1,String str2) {
//
//        str1="剩余时间||间隔站||剩余站";
//
//        SpannableString sp = new SpannableString(str2);
//        // 遍历要显示的文字
//        for (int i = 0 ; i < str1.length() ; i ++){
//            // 得到单个文字
//            String s1 = str1.charAt(i) + "";
//            // 判断字符串是否包含高亮显示的文字
//            if (str2.contains(s1)){
//                // 查找文字在字符串的下标
//                int index = str2.indexOf(s1);
//                // 循环查找字符串中所有该文字并高亮显示
//                while (index != -1) {
//                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#db384c"));
//                    sp.setSpan(colorSpan, index, index + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                    // s1从这个索引往后开始第一个出现的位置
//                    index = str2.indexOf(s1, index + 1);
//                }
//            }
//        }
//        // 设置控件
//        textview.setText(sp);
//    }

}