package com.duwamish.radio.nearby_wifi_positioning.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import com.duwamish.radio.nearby_wifi_positioning.handlers.RadioMapModel;

public class SplashViewController extends Activity {
    public static SplashViewController splashActivity;
    private final long SPLASH_LENGTH = 400;
    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.splash);
        splashActivity = this;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RadioMapModel.getInstance().init();
                Intent intent = new Intent(SplashViewController.this,
                        NearbyViewController.class);
                SplashViewController.this.startActivity(intent);
                SplashViewController.this.finish();
            }
        }, SPLASH_LENGTH);
    }
}
