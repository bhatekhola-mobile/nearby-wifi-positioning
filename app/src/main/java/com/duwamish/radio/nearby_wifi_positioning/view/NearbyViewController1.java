package com.duwamish.radio.nearby_wifi_positioning.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.duwamish.radio.nearby_wifi_positioning.R;

public class NearbyViewController1 extends Activity {
    private ListView wifiList;
    private WifiManager wifiManager;
    private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
    WifiBroadcastReceiver receiverWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_nearby);
        wifiList = findViewById(R.id.wifiList);
        Button buttonScan = findViewById(R.id.scanBtn);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(), "Turning WiFi ON...", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(
                        NearbyViewController1.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            NearbyViewController1.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_ACCESS_COARSE_LOCATION
                    );
                } else {
                    wifiManager.startScan();
                }
            }
        });
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        receiverWifi = new WifiBroadcastReceiver(wifiManager, wifiList);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(receiverWifi, intentFilter);
        getWifi();
    }

    private void getWifi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Toast.makeText(
                    NearbyViewController1.this, "version is >= marshmallow", Toast.LENGTH_SHORT
            ).show();

            if (ContextCompat.checkSelfPermission(
                    NearbyViewController1.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        NearbyViewController1.this, "location is turned off",
                        Toast.LENGTH_SHORT
                ).show();
                ActivityCompat.requestPermissions(
                        NearbyViewController1.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
            } else {
                Toast.makeText(
                        NearbyViewController1.this, "location turned on", Toast.LENGTH_SHORT
                ).show();
                wifiManager.startScan();
            }
        } else {
            Toast.makeText(
                    NearbyViewController1.this, "scanning", Toast.LENGTH_SHORT).show();
            wifiManager.startScan();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiverWifi);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(NearbyViewController1.this,
                        "permission is granted", Toast.LENGTH_LONG
                ).show();
                wifiManager.startScan();
            } else {
                Toast.makeText(NearbyViewController1.this,
                        "permission is not granted",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }
            break;
        }
    }
}
