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
    //Sensor Manager Information
    SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION); //3 values
    TextView dab = new TextView(getApplicationContext());
    SensorEventListener AccEvent = new DabSensorListener(dab);

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sensor Manager Information

        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_main);;
        dab.setText("");
        dab.setTextColor(Color.BLACK);
        ll.addView(dab);

        sensorManager.registerListener(AccEvent, accSensor, SensorManager.SENSOR_DELAY_GAME);


    }
    public void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(AccEvent, accSensor);
    }
    public void onResume()
    {
        super.onResume();
        sensorManager.registerListener(AccEvent, accSensor, SensorManager.SENSOR_DELAY_GAME);
    }
}
