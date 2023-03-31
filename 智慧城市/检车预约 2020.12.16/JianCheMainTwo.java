package com.example.atest;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JianCheMainTwo extends Fragment implements View.OnClickListener{
    View view;
    Button bt;
    TextView textView;
    EditText et1,et2,et3;
    String[] str_list;
    String str1,str2,str3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.jian_che_main_two, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String cp = pref.getString("cp", "");
        String cj = pref.getString("cj", "");
        String cl = pref.getString("cl", "");
        String gl = pref.getString("gl", "");
        String sj = pref.getString("sj", "");

        textView=view.findViewById(R.id.lj_tv);
        textView.setText("车牌号："+cp+"\n"+"车架号："+cj+"\n"+"车辆类型："+cl+"\n"+"公里数："+gl+"\n"+"手机号:"+sj);

        setHasOptionsMenu(true);

       et1=view.findViewById(R.id.ljedt_1);
        et2=view.findViewById(R.id.ljedt_2);
        et3=view.findViewById(R.id.ljedt_3);
        et1.setInputType(InputType.TYPE_NULL);
        et2.setInputType(InputType.TYPE_NULL);
        et1.setOnClickListener(this);
        et2.setOnClickListener(this);

        bt=view.findViewById(R.id.lj_bt);
        bt.setOnClickListener(this);


        get();
        return view;
    }



    private void get(){

        String url="http://124.93.196.45:10001/prod-api/api/traffic/checkCar/place/list";

        SharedPreferences pref = getActivity().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String token = pref.getString("token", "");
        Log.d("ccccc", token);

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

                    Log.d("aaaaa", resStr);
                    JSONObject jsonObject = new JSONObject(resStr);	//取出返回的数据
                    JSONArray jsonArray=jsonObject.getJSONArray("rows");

                     str_list = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i ++){
                        str_list[i] = jsonArray.getJSONObject(i).getString("address");
                    }


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
        menu.add("立即预约").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ljedt_1:
                Calendar c=Calendar.getInstance();
                new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                str1=(year + "年" + (month + 1)
                                        + "月" + dayOfMonth + "日");
                                et1.setText("您选择了：" + str1);
                            }
                        }
                        , c.get(Calendar.YEAR)
                        , c.get(Calendar.MONTH)
                        , c.get(Calendar.DAY_OF_MONTH)).show();

                break;
            case R.id.ljedt_2:
                Calendar b=Calendar.getInstance();
                new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            @Override
                            public void onTimeSet(TimePicker tp, int hourOfDay,
                                                  int minute)
                            {
                                str2=(hourOfDay + "时" + minute
                                        + "分");
                                et2.setText("您选择了：" + str2);
                            }
                        }
                        //设置初始时间
                        , b.get(Calendar.HOUR_OF_DAY)
                        , b.get(Calendar.MINUTE)
                        //true表示采用24小时制
                        , true).show();

        break;
            case R.id.ljedt_3:

                AlertDialog.Builder builder2=new AlertDialog.Builder(getActivity());
                builder2.setTitle("选择地点");

                builder2.setSingleChoiceItems(str_list,-1,new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        str3=str_list[which];
                        et3.setText(str3);
                    }
                }).setPositiveButton("确定",null).show().setCancelable(false);
                break;

            case R.id.lj_bt:
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.jcmain,new JianCheMainThree(),null)
                        .addToBackStack(null).commit();

                String ri=str1;
                String s=str2;
                String dizhi=str3;


                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("ri", ri);
                editor.putString("s", s);
                editor.putString("dizhi", dizhi);
                editor.apply();
                break;



        }
    }
}
