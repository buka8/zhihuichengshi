package com.example.smartcity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Network {

    private String str_1;
    private String img;
    private String content;

    public String getStr_1() {
        return str_1;
    }

    public void setStr_1(String str_1) {
        this.str_1 = str_1;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void useSendRequestWithOkHttp(String address, okhttp3.Callback callback) {
        SendRequestWithOkHttp(address, callback);
    }

    public Response useSynchronousResquit(String address){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void useShowResponse(String response, String value) {
        ShowResponse(response, value);
    }
    public JSONArray useShowResponse(String response) {
        return ShowResponse(response);
    }

    private static void SendRequestWithOkHttp(String address, Callback callback) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(address).build();

        client.newCall(request).enqueue(callback);
    }

    private void ShowResponse(String response, String value) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("rows");

            setStr_1(jsonArray.getJSONObject(0).getString(value));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray ShowResponse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("rows");

           return jsonArray;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
