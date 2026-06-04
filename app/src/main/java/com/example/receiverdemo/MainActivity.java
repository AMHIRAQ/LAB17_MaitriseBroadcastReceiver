package com.example.receiverdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private AirplaneModeReceiver airplaneReceiver;
    private CustomEventReceiver customReceiver;
    private boolean isReceiverRegistered = false;
    private Button btnToggleAirplane, btnSendCustom;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        airplaneReceiver = new AirplaneModeReceiver();
        tvStatus = findViewById(R.id.tvStatus);
        btnToggleAirplane = findViewById(R.id.btnToggleAirplane);
        btnSendCustom = findViewById(R.id.btnSendCustom);

        // Register custom receiver dynamically
        customReceiver = new CustomEventReceiver();
        IntentFilter customFilter = new IntentFilter("com.example.receiverdemo.CUSTOM_EVENT");
        ContextCompat.registerReceiver(this, customReceiver, customFilter, ContextCompat.RECEIVER_NOT_EXPORTED);

        btnToggleAirplane.setOnClickListener(v -> toggleAirplaneReceiver());
        btnSendCustom.setOnClickListener(v -> sendCustomBroadcast());
    }

    private void toggleAirplaneReceiver() {
        if (!isReceiverRegistered) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            ContextCompat.registerReceiver(this, airplaneReceiver, filter, ContextCompat.RECEIVER_EXPORTED);
            isReceiverRegistered = true;
            tvStatus.setText("Airplane Mode Receiver: ENABLED (dynamic)");
            btnToggleAirplane.setText("Disable Airplane Receiver");
        } else {
            unregisterReceiver(airplaneReceiver);
            isReceiverRegistered = false;
            tvStatus.setText("Airplane Mode Receiver: DISABLED");
            btnToggleAirplane.setText("Enable Airplane Receiver");
        }
    }

    private void sendCustomBroadcast() {
        Intent intent = new Intent("com.example.receiverdemo.CUSTOM_EVENT");
        intent.setPackage(getPackageName());
        intent.putExtra("message", "Hello from the custom broadcast!");
        sendBroadcast(intent);
        Toast.makeText(this, "Custom Broadcast sent!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (isReceiverRegistered) {
            unregisterReceiver(airplaneReceiver);
        }
        unregisterReceiver(customReceiver);
        super.onDestroy();
    }
}