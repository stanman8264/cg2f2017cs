package com.example.chris.testapp2;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;


public class SendMessageService extends Service implements SensorEventListener{
    udpClientSend udpClientSend;
    public String message;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    public SendMessageService() {

    }
    @Override//
    public int onStartCommand(Intent intent, int flags, int startId) {
        message = intent.getStringExtra(DisplayMessageActivity.EXTRA_MESSAGE_DISPLAY);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onSensorChanged(SensorEvent event) {
        Log.v("Sensor Reading", "Time: " +event.timestamp + " X: " + event.values[0] + " Y: " + event.values[1] + " Z: " + event.values[2]);
          /* send these to the server! */
        Log.v("IP address:", message);
        udpClientSend = new udpClientSend(message, event);
        udpClientSend.start();
    }
    @Override
    public void onDestroy() {
        // Call disable to ensure that the trigger request has been canceled.
        mSensorManager.unregisterListener(this);
    }

}
