package com.duwamish.radio.nearby_wifi_positioning.util;

import android.util.Log;
import com.duwamish.radio.nearby_wifi_positioning.view.SplashViewController;

import java.io.IOException;
import java.io.InputStream;

public class FileManager {
    public String[] readFileStrings(String fileName) {
        try {
            InputStream in;
            in = SplashViewController.splashActivity.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            in.close();
            String txtString = new String(buffer);
            String[] strarray = txtString.split(" ");
            return strarray;
        } catch (IOException e) {
            Log.e("FileManager", "error reading " + fileName + ": " + e.getMessage());
        }
        return null;
    }
}
