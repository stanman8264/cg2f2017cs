package com.example.chris.testapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    public String message;
    public static final String EXTRA_MESSAGE_DISPLAY = "com.example.myfirstapp.MESSAGEDISPLAY";
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        serviceIntent = new Intent(this, SendMessageService.class);
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
        serviceIntent.putExtra(EXTRA_MESSAGE_DISPLAY, message);
        startService(serviceIntent);
     }

     //todo, on destroy? Stop service.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }
}

