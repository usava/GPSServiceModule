package com.example.cowboy.asynctaskmodule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String LOG_TAG = "log";
    protected BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.tv_main_logcontainer);

        // создаем BroadcastReceiver
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                String task = intent.getStringExtra(MyLocationService.RESULT_KEY);

                Log.d(LOG_TAG, "onReceive: task = " + task);
                textView.setText(task);
            }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(MyLocationService.NOTIFICATION);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(broadcastReceiver, intFilt);


        Intent intent = new Intent(this, MyLocationService.class);
        this.startService(intent);

        textView.setText("Service started" + "\n");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // дерегистрируем (выключаем) BroadcastReceiver
        unregisterReceiver(broadcastReceiver);
    }

}