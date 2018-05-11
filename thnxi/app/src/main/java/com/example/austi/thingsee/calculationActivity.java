package com.example.austi.thingsee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.austi.thingsee.User;
import com.example.austi.thingsee.MainActivity;
import com.example.austi.thingsee.R;

public class calculationActivity extends AppCompatActivity implements View.OnClickListener{


    TextView distance,velocity;
    Button back;
    String nam, pas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        /*back = (Button) findViewById(R.id.buttonBack);
        if(back!=null)
        back.setOnClickListener(this);*/
        distance = (TextView) findViewById(R.id.distance);
        velocity = (TextView) findViewById(R.id.velocity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extra = getIntent().getExtras();
        double d = extra.getDouble("distance");
        long v = extra.getLong("velocity");

        distance.setText("Distance: "+d+" Km");
        velocity.setText("Velocity: "+v+ " m/s");


    }

    @Override
    public void onClick(View v){
        User user = new User(calculationActivity.this);
        user.setNam(nam);
        user.setPas(pas);

        Intent backInt = new Intent(this, MainActivity.class);


        startActivity(backInt);

    }

}
