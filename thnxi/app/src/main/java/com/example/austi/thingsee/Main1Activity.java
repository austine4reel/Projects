package com.example.austi.thingsee;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.austi.thingsee.Main2Activity;
import com.example.austi.thingsee.MainActivity;
import com.example.austi.thingsee.R;
import com.example.austi.thingsee.User;

public class Main1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        // for the timer
        Handler handler = new Handler();
        handler.postDelayed (new Runnable() {
            @Override
            public void run() {

                if(new User(Main1Activity.this).getNam()!="") {

                    Intent intent = new Intent(Main1Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(Main1Activity.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2500);
    }
}
