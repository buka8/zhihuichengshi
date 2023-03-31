package com.example.smartcity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class findHouse_main extends AppCompatActivity implements View.OnClickListener {

    private Banner mBanner;
    private List<findhouseBean> data = new ArrayList<>();
    private Network network;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private EditText search;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findhouse_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("找房子");


        initView();
        network = new Network();
        mListView = (ListView) findViewById(R.id.findHouse_lv);
        txt1.setOnClickListener(this);
        txt2.setOnClickListener(this);
        txt3.setOnClickListener(this);
        txt4.setOnClickListener(this);
        houselist("houseType=二手");//初始化事件



        //轮播图
        network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/house/housing/list", new Callback() {
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

                            initBanner(network.useShowResponse(responseData));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

        //搜索框
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String searchKey = search.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_SEARCH ) {
                    houselist("sourceName=" + searchKey);

                }

                return false;
            }
        });

        //房源详情跳转界面
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(findHouse_main.this, findhouse_detail.class);
                Bundle bundle = new Bundle();
                bundle.putInt("value", data.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }



    //轮播图方法
    private void initBanner(JSONArray jsonArray) throws JSONException {
        List<BannerBean> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String img = jsonArray.getJSONObject(i).getString("pic");
            int id = jsonArray.getJSONObject(i).getInt("id");
            list.add(new BannerBean(img, id));
        }
        mBanner.setAdapter(new BannerImageAdapter<BannerBean>(list) {
            @Override
            public void onBindView(BannerImageHolder bannerImageHolder, BannerBean bannerBean, int position, int size) {
                Glide.with(bannerImageHolder.itemView)
                        .load("http://124.93.196.45:10001" + bannerBean.getImg())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(bannerImageHolder.imageView);

                bannerImageHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(findHouse_main.this, findhouse_detail.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("value", bannerBean.getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(getApplicationContext()));
    }

    private void initView() {
        mBanner = (Banner) findViewById(R.id.myBanner);

        search = (EditText) findViewById(R.id.findHouse_search);
        txt1 = (TextView) findViewById(R.id.findHouse_textView);
        txt2 = (TextView) findViewById(R.id.findHouse_textView2);
        txt3 = (TextView) findViewById(R.id.findHouse_textView3);
        txt4 = (TextView) findViewById(R.id.findHouse_textView4);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.findHouse_textView:
                houselist("houseType=二手");
                break;
            case R.id.findHouse_textView2:
                houselist("houseType=租房");
                break;
            case R.id.findHouse_textView3:
                houselist("houseType=楼盘");
                break;
            case R.id.findHouse_textView4:
                houselist("houseType=中介");
                break;
        }
    }

    //房源方法
    private void houselist(String type) {

        network.useSendRequestWithOkHttp("http://124.93.196.45:10001/prod-api/api/house/housing/list?" + type, new Callback() {
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
                        data.clear();
                        if (jsonArray.length() != 0) {
                            mListView.setAdapter(new findhouseAdapter(data, findHouse_main.this));
                            for (int i = 0; i < jsonArray.length(); i++) {

                                try {
                                    String img = jsonArray.getJSONObject(i).getString("pic");
                                    String title = jsonArray.getJSONObject(i).getString("sourceName");
                                    String area = jsonArray.getJSONObject(i).getString("areaSize");
                                    String price = jsonArray.getJSONObject(i).getString("price");
                                    String description = jsonArray.getJSONObject(i).getString("description");
                                    int id = jsonArray.getJSONObject(i).getInt("id");
                                    findhouseBean bean = new findhouseBean();
                                    bean.setId(id);
                                    bean.setImg(img);
                                    bean.setTitle(title);
                                    bean.setArea(area);
                                    bean.setPrice(price);
                                    bean.setDescription(description);
                                    data.add(bean);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(findHouse_main.this, "未找到房源信息...", Toast.LENGTH_SHORT).show();
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
