package com.duwamish.radio.nearby_wifi_positioning.util;


import android.content.Context;
import android.net.wifi.ScanResult;
import com.duwamish.radio.nearby_wifi_positioning.handlers.WiFiDataManager;
import com.duwamish.radio.nearby_wifi_positioning.view.NearbyViewController;

import java.util.HashMap;
import java.util.List;

public class TransformUtil {
    public float[] scanResults2vector(List<ScanResult> scanResults,
                                      HashMap<String, Integer> bssids) {

        float rssScan[] = new float[229];
        int bsscount = 0;
        if (scanResults != null) {

            for (int i = 0; i < rssScan.length; i++) {
                rssScan[i] = -200;
            }
            for (int j = 0; j < scanResults.size(); j++) {
                String bssid = scanResults.get(j).BSSID;
                if (bssids.containsKey(bssid)) {
                    int idx = bssids.get(bssid);
                    rssScan[idx] = scanResults.get(j).level;
                    bsscount++;
                }
            }
        }
        if (bsscount < NearbyViewController.mainactivity.Wifi_Num_Min) {
            WiFiDataManager.getInstance().isNormal = false;
        } else {
            WiFiDataManager.getInstance().isNormal = true;
        }
        return rssScan;
    }

    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }
}
