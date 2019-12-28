package com.duwamish.radio.nearby_wifi_positioning.handlers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.duwamish.radio.nearby_wifi_positioning.view.NearbyViewController;

public class SensorsDataManager {
    private SensorManager sensorManager;
    private Sensor osensor;
    private SensorEventListener oSensorListener;
    //
    private Sensor msensor;
    private SensorEventListener mSensorListener;

    public float[] temp_m = new float[3];
    public float[] temp_r = new float[3];

    private volatile static SensorsDataManager sensorsDataManager = null;

    public static SensorsDataManager getInstance() {
        if (sensorsDataManager == null) {
            synchronized (SensorsDataManager.class) {
                if (sensorsDataManager == null) {
                    sensorsDataManager = new SensorsDataManager();
                }
            }
        }
        return sensorsDataManager;
    }

    // 初始化各种Sensors的操作
    public void initSensors() {
        sensorManager = (SensorManager) NearbyViewController.mainactivity
                .getSystemService(Context.SENSOR_SERVICE);
        msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        osensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorListener = new MSensorListener();
        oSensorListener = new OSensorListener();
        // sensorManager.registerListener(mSensorListener, msensor,
        // SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(oSensorListener, osensor,
                SensorManager.SENSOR_DELAY_UI);
    }

    private class MSensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            temp_m[0] = event.values[0];
            temp_m[1] = event.values[1];
            temp_m[2] = event.values[2];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    private class OSensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            temp_r[0] = event.values[0];
            temp_r[1] = event.values[1];
            temp_r[2] = event.values[2];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    public void unregist() {
        sensorManager.unregisterListener(oSensorListener);
    }
}
