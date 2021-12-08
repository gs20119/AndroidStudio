package com.example.opengl3d;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;

import java.util.Objects;

public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer mRenderer;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorListener;
    private float mAcc;
    private float mAccNow;
    private float mAccLast;

    public MyGLSurfaceView(Context context)
    {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer(context);
        setRenderer(mRenderer);

        mAcc = 10.0f;
        mAccNow = SensorManager.GRAVITY_EARTH;
        mAccLast = SensorManager.GRAVITY_EARTH;
        mSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                mAccLast = mAccNow;
                mAccNow = (float)Math.sqrt((double)(x*x+y*y+z*z));
                float delta = mAccNow - mAccLast;
                mAcc = mAcc*0.9f+delta;
                if(Math.abs(mAcc)>12) mRenderer.Shake();
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy){}
        };

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
}
