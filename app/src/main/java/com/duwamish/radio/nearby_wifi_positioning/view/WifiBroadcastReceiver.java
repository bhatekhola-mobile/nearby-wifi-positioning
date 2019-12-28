package com.duwamish.radio.nearby_wifi_positioning.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class WifiBroadcastReceiver extends BroadcastReceiver {
    WifiManager wifiManager;
    StringBuilder sb;
    ListView wifiDeviceList;

    public WifiBroadcastReceiver(WifiManager wifiManager, ListView wifiDeviceList) {
        this.wifiManager = wifiManager;
        this.wifiDeviceList = wifiDeviceList;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            sb = new StringBuilder();
            List<ScanResult> wifiList = wifiManager.getScanResults();
            ArrayList<Wifi> deviceList = new ArrayList<>();
            for (ScanResult scanResult : wifiList) {
                sb.append("\n")
                        .append(scanResult.SSID)
                        .append(" - ")
                        .append(scanResult.capabilities);

                deviceList.add(new Wifi(scanResult.SSID, scanResult.level, 0));
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                    deviceList.stream().map($ -> $.toString()).collect(Collectors.toList()).toArray()
            );
            wifiDeviceList.setAdapter(arrayAdapter);
        }
    }

    public static class Wifi {
        private String name;
        private int rssi;
        private int tranmittedStrength;

        public Wifi(String name, int rssi, int tranmittedStrength) {
            this.name = name;
            this.rssi = rssi;
            this.tranmittedStrength = tranmittedStrength;
        }

        @Override
        public String toString() {
            return "Wifi: " + name + "\n" +
                    "Transmitted Strength: " + tranmittedStrength + "dbm \n" +
                    "RSSI: " + rssi + " dbm";
        }
    }
}
