package com.example.atest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class JianCheMain extends AppCompatActivity implements View.OnClickListener{

    JianCheMainOne jcone;
    JianCheMainTwo jctwo;
    JianCheMainThree jcthree;
    JianCheMainFour jcfour;
    FragmentManager fragment_manager;
    FragmentTransaction fragment_transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jian_che_main);

        ActionBar action_bar = getSupportActionBar();
        action_bar.setDisplayHomeAsUpEnabled(true);
        action_bar.setTitle("预约检车");

        setDefault();
        jcone = new JianCheMainOne();
        jctwo = new JianCheMainTwo();
        jcthree = new JianCheMainThree();
        jcfour = new JianCheMainFour();

        fragment_manager = getSupportFragmentManager();

        fragment_transaction = fragment_manager.beginTransaction();
        fragment_transaction.replace(R.id.jcmain, jcone);
        fragment_transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.jcnav_iv_1:
                fragment_transaction = fragment_manager.beginTransaction();
                fragment_transaction.replace(R.id.jcmain, jcone);
                fragment_transaction.commit();
                break;

            case R.id.jcnav_iv_2:
                fragment_transaction = fragment_manager.beginTransaction();
                fragment_transaction.replace(R.id.jcmain, jctwo);
                fragment_transaction.commit();
                break;

            case R.id.jcnav_iv_3:
                fragment_transaction = fragment_manager.beginTransaction();
                fragment_transaction.replace(R.id.jcmain, jcthree);
                fragment_transaction.commit();
                break;

            case R.id.jcnav_iv_4:
               fragment_transaction = fragment_manager.beginTransaction();
                fragment_transaction.replace(R.id.jcmain,jcfour);
                fragment_transaction.commit();
                break;

    }
}
          private void setDefault() {
              ImageView jcnav_iv_1 = findViewById(R.id.jcnav_iv_1);
              ImageView jcnav_iv_2 = findViewById(R.id.jcnav_iv_2);
              ImageView jcnav_iv_3 = findViewById(R.id.jcnav_iv_3);
              ImageView jcnav_iv_4 = findViewById(R.id.jcnav_iv_4);


              jcnav_iv_1.setOnClickListener(this);
              jcnav_iv_2.setOnClickListener(this);
              jcnav_iv_3.setOnClickListener(this);
              jcnav_iv_4.setOnClickListener(this);

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

}