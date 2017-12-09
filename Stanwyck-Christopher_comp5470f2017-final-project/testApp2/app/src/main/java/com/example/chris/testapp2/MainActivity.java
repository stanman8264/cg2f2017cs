package com.example.chris.testapp2;

        import android.content.Intent;
        import android.os.PowerManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
       PowerManager mgr;
       PowerManager.WakeLock wakeLock;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            mgr = (PowerManager)getSystemService(POWER_SERVICE);
            wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
            wakeLock.acquire();
    }

    /** called when the user taps the Send button */
    public void sendMessage(View view) {
        //Intent intent = new Intent(this, SendMessageService.class);
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
      //  startService(intent);
        startActivity(intent);

    }
}
