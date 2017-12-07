package com.example.chris.testapp2;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class DisplayMessageActivity extends AppCompatActivity implements SensorEventListener {

    udpClientSend udpClientSend;
    public String message;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
     }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onSensorChanged(SensorEvent event) {
          Log.v("Sensor Reading", "Time: " +event.timestamp + " X: " + event.values[0] + " Y: " + event.values[1] + " Z: " + event.values[2]);
          /* send these to the server! */
           udpClientSend = new udpClientSend(message, event);
           udpClientSend.start();
       //    try {
               /* don't send too much data! */
       //        wait(100);

     //   } catch (InterruptedException e) {
       //   e.printStackTrace();

        //}
    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
       // Call disable to ensure that the trigger request has been canceled.
        mSensorManager.unregisterListener(this);
    }
}

