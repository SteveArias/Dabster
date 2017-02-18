package com.example.steve.dabster;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sensor Manager Information
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION); //3 values

        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_main);;
        TextView dab = new TextView(getApplicationContext());
        dab.setTextColor(Color.BLACK);
        ll.addView(dab);

        SensorEventListener AccEvent = new DabSensorListener(dab);
        sensorManager.registerListener(AccEvent, accSensor, SensorManager.SENSOR_DELAY_GAME);
    }
}
