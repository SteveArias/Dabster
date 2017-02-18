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

    enum ySTATE{yWAIT, yFALL1, yRISE1, yDAB};
    ySTATE state1 = ySTATE.yWAIT;
    enum zSTATE{zWAIT, zFALL1, zRISE1, zFALL2, zDAB};
    zSTATE state2 = zSTATE.zWAIT;
    boolean dab;

    final float [] yThresh = {0,0,0};
    final float [] zThresh = {0,0,0,0};

    final int SAMPLEDEFAULT = 100;
    int sampleCounter = SAMPLEDEFAULT;

    private float x;
    private float y;
    private float z;

    private float [][] array = new float[3][100];//3x100 array

    public DabSensorListener(TextView dab) {
        out = dab;
    }

    @Override
    public void onSensorChanged(SensorEvent se) {
        x = se.values[0];
        y = se.values[1];
        z = se.values[2];
    }

    public void determine(float[] values){
        //filtering
        for(int i = 1; i < 100; i++){
            array[0][i-1] = array[0][i];
            array[1][i-1] = array[1][i];
            array[2][i-1] = array[2][i];
        }

        //adding new value to last element FILTERING
        array[0][99] += ( values[0] - array[0][99] ) / 10;
        array[1][99] += ( values[1] - array[1][99] ) / 10;
        array[2][99] += ( values[2] - array[2][99] ) / 10;

        StateChange();



    }
    public void StateChange(){
        float dy = array[1][99] - array[1][98];
        float dz = array[2][99] - array[2][98];

        switch(state1){//y values
            case yWAIT:
                sampleCounter = SAMPLEDEFAULT;

                if(dy > 0){
                    if(array[1][99] > yThresh[0]){
                        state1 = ySTATE.yFALL1;
                    }
                }
                break;
            case yFALL1:
                if(dy < 0){
                    if(array[1][99] < yThresh[1]){
                        state1 = ySTATE.yRISE1;
                    }
                }
                break;
            case yRISE1:
                if(dy > 0){
                    if(array[1][99] > yThresh[1]){
                        state1 = ySTATE.yDAB;
                    }
                }
                break;
            default:
                state1 = ySTATE.yDAB;
        }

        switch(state2){//z values
            case zWAIT:
                if(dz > 0){
                    if(array[2][99] > zThresh[0]){
                        state2 = zSTATE.zFALL1;
                    }
                }
                break;
            case zFALL1:
                if(dz < 0){
                    if(array[2][99] < zThresh[1]){
                        state2 = zSTATE.zRISE1;
                    }
                }
                break;
            case zRISE1:
                if(dz>0){
                    if(array[2][99] > zThresh[2]){
                        state2 = zSTATE.zFALL2;
                    }
                }
                break;
            case zFALL2:
                if(dz < 0){
                    if(array[2][99] < zThresh[3]){
                        state2 = zSTATE.zDAB;
                    }
                }
                break;
            default:
                state2 = zSTATE.zDAB;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}
