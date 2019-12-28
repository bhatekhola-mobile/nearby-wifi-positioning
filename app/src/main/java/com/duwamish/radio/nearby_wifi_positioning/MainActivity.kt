//package com.duwamish.radio.nearby_wifi_positioning
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.net.wifi.WifiManager
//import android.util.Log
//
//class MainActivity : AppCompatActivity() {
//
//    lateinit var wifiManager: WifiManager
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        this.wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//        this.wifiManager.isWifiEnabled = true
//
//        val wifiScanReceiver = object : BroadcastReceiver() {
//
//            override fun onReceive(context: Context, intent: Intent) {
//                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
//                if (success) {
//                    val results = wifiManager.scanResults
//                    Log.i("MainActivity", "wifis " + results.size)
//                } else {
//                    val results = wifiManager.scanResults
//                    Log.i("MainActivity", "wifis error " + results.size)
//                }
//            }
//        }
//
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
//        this.applicationContext.registerReceiver(wifiScanReceiver, intentFilter)
//
//        val scanned = wifiManager.startScan()
//        if(!scanned) {
//            wifiManager.scanResults
//        }
//
//        Log.i("MainActivity", "scan: " + scanned)
//        setContentView(R.layout.activity_main)
//    }
//}
