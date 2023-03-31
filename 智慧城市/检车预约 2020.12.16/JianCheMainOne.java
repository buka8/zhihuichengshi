package com.example.atest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JianCheMainOne extends Fragment {
    View view;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.jian_che_main_one, container, false);
        textView=view.findViewById(R.id.xz_tv);

        setHasOptionsMenu(true);
        get();
        return view;
    }

    private void get(){

        String url="http://124.93.196.45:10001/prod-api/api/traffic/checkCar/grt";

        SharedPreferences pref = getActivity().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
            String token = pref.getString("token", "");
        Log.d("bibibibibi", token);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", token)			//addHeader方法，在请求头设置认证信息，参数为字符串"Authorization"，值为字符串token（登录获取）
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response = client.newCall(request).execute();
                    String resStr = response.body().string();

                    Log.d("bibibibibibibi", resStr);
                    JSONObject jsonObject = new JSONObject(resStr);	//取出返回的数据

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                textView.setText(Html.fromHtml(jsonObject.getJSONObject("data").getString("notice")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    menu.add("预约须知").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
}