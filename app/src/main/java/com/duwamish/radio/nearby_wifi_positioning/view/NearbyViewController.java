package com.duwamish.radio.nearby_wifi_positioning.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.duwamish.radio.nearby_wifi_positioning.R;
import com.duwamish.radio.nearby_wifi_positioning.handlers.SensorsDataManager;
import com.duwamish.radio.nearby_wifi_positioning.handlers.WiFiDataManager;

public class NearbyViewController extends Activity {

    public static NearbyViewController mainactivity;
    private TextView StatusTextView;
    private TextView resultsTextView;
    private ImageButton myPositionImageButton;
    private ImageView planMapImageView;
    public float result_x0 = 0f;
    public float result_y0 = 0f;
    public int result_num0 = 0;
    int width; // 屏幕宽度（像素）
    int height; // 屏幕高度（像素）
    float density; // 屏幕密度（0.75 / 1.0 / 1.5）
    int densityDpi; // 屏幕密度DPI（120 / 160 / 240）
    public int Wifi_Num_Min = 3;// 定位时wifi热点的最小数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mainactivity = this;
        intiResources();
        SensorsDataManager.getInstance().initSensors();
        WiFiDataManager.getInstance().initWifi();
        WiFiDataManager.getInstance().startScanWifi();
    }

    protected void onStop() {
        SensorsDataManager.getInstance().unregist();
        super.onStop();
    }

    private void intiResources() {
        StatusTextView = (TextView) findViewById(R.id.textView1);
        resultsTextView = (TextView) findViewById(R.id.textView2);
        myPositionImageButton = (ImageButton) findViewById(R.id.imageButton1);
        planMapImageView = (ImageView) findViewById(R.id.imageView1);
        // 点击定位点图标的监听程序
        myPositionImageButton.setOnClickListener(new NewImageOnClickListener());
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels; // 屏幕宽度（像素）
        height = metric.heightPixels; // 屏幕高度（像素）
        density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
    }

    // 创建点击菜单键对应的菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 点击菜单项响应
    public boolean onOptionsItemSelected(MenuItem item) {
        // Log.i("MenuTest:", "ItemSelected");
        switch (item.getItemId()) {
            case R.id.action_abouts:
                // Log.i("MenuTest:", "ItemSelected:1");
                // String outString = R.string.about;
                new AlertDialog.Builder(this)
                        .setTitle(R.string.action_abouts)
                        .setMessage(
                                this.getString(R.string.about) + "\n"
                                        + this.getString(R.string.version))
                        .setPositiveButton(R.string.ok, null).show();
                break;
            default:
                break;
        }
        return true;
    }

    // 两次返回退出
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "click back again to exit",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void uiUpdate() {
        String outString = "Positioning results:\n";

        int l_left = planMapImageView.getLeft();
        int l_right = planMapImageView.getRight();
        int l_bottom = planMapImageView.getBottom();
        int l_top = planMapImageView.getTop();

        outString = outString + "num: " + result_num0 + " x: " + result_x0
                + " y: " + result_y0;
        outString = outString + "\nwidth： " + width + " height: " + height
                + " density: " + density + " densityDip: " + densityDpi;
        outString = outString + "\nUDLR： " + l_top + " " + l_bottom + " "
                + l_left + " " + l_right;
        // TipsTextView.setText(outString);

        float map_width = 63;
        float map_height = 85;
        float offest_x = 3;
        float offset_y = 3;
        float ui_x = l_left + (l_right - l_left) * (57 - result_y0 + offest_x)
                / map_width;
        float ui_y = l_top + (l_bottom - l_top) * (79 - result_x0 + offset_y)
                / map_height;

        myPositionImageButton.setX(ui_x - myPositionImageButton.getWidth() / 2);
        myPositionImageButton.setY(ui_y - myPositionImageButton.getHeight());

        String outString2 = "Positioning coordinates x:  " + result_x0 + "  y: " + result_y0;
        resultsTextView.setText(outString2);

        String tips;
        if (WiFiDataManager.getInstance().isNormal) {
            tips = "normal";
        } else {
            tips = "Not enough matches in the database ";
        }
        outString2 = "current Wifi number:"
                + WiFiDataManager.getInstance().scanResults.size() + " " + tips;
        StatusTextView.setText(outString2);

    }

    private class NewImageOnClickListener implements View.OnClickListener {
        @Override
        // 在当前onClick方法中监听点击Button的动作
        public void onClick(View v) {
            String outString = " x: " + result_x0 + "\n" + " y: " + result_y0;
            new AlertDialog.Builder(mainactivity).setTitle("Position coordinates")
                    .setMessage(outString).setPositiveButton("determine", null).show();
        }
    }


}
