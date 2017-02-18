package com.example.steve.dabster;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

/**
 * Created by Erick on 2017-02-18.
 */

public class DabSensorListener implements SensorEventListener{
    private TextView out;

    //enum ySTATE = {yWAIT };
    public DabSensorListener(TextView dab) {
        out = dab;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}
