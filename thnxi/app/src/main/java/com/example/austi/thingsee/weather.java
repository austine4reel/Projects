package com.example.austi.thingsee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by austi on 04/05/2018.
 */

public  class weather extends AppCompatActivity {
    SensorValue sensorValue = new SensorValue();
    EditText tempfield, humidField, pressureField, lumField;
    Double tempData, humidData, pressureData, lumData;
    String tempval, humidval, pressureval, lumval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        tempfield = (EditText)findViewById(R.id.tempField);
        humidField = (EditText) findViewById(R.id.humidField);
        pressureField = (EditText) findViewById(R.id.pressureField);
        lumField = (EditText)findViewById(R.id.lumField);
        humidData = sensorValue.getHum();
        tempData = sensorValue.getTemp();
        pressureData = sensorValue.getPressure();
        lumData = sensorValue.getLux();
        humidval = humidData.toString();
        lumval = lumData.toString();
        pressureval = pressureData.toString();
        tempval = tempData.toString();
        tempfield.setText(humidval);
        humidField.setText(tempval);
        pressureField.setText(pressureval);
        lumField.setText(lumval);

    }
}
