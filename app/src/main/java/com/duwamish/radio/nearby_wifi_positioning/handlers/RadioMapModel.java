package com.duwamish.radio.nearby_wifi_positioning.handlers;

import com.duwamish.radio.nearby_wifi_positioning.util.FileManager;

import java.util.HashMap;

public class RadioMapModel {
    public float radioMap1[][];
    public float radioMap2[][];
    public HashMap<String, Integer> bssids;
    public int N_fp = 0;
    public int M_fp = 0;

    private volatile static RadioMapModel radioMapModel = null;

    public static RadioMapModel getInstance() {
        if (radioMapModel == null) {
            synchronized (RadioMapModel.class) {
                if (radioMapModel == null) {
                    radioMapModel = new RadioMapModel();
                }
            }
        }
        return radioMapModel;
    }

    public void init() {
        radioMap1 = getRadioMap("map1.txt");// 第一个Radio map
        radioMap2 = getRadioMap("map2.txt");// 第二个Radio map
        bssids = getBssids("bssid.txt");// 对应的bssid
    }

    private float[][] getRadioMap(String fileName) {
        float radioMap[][] = null;
        String[] strarray = new FileManager().readFileStrings(fileName);

        N_fp = Integer.parseInt(strarray[0]);
        M_fp = Integer.parseInt(strarray[1]);
        radioMap = new float[M_fp][N_fp];
        int k = 2;
        for (int i = 0; i < M_fp; i++) {
            for (int j = 0; j < N_fp; j++) {
                try {
                    radioMap[i][j] = Float.parseFloat(strarray[k]);
                    k++;
                } catch (Exception e) {
                }
            }
        }
        return radioMap;
    }

    private HashMap<String, Integer> getBssids(String fileName) {
        HashMap<String, Integer> bssids = new HashMap<String, Integer>();
        String[] strarray = new FileManager().readFileStrings(fileName);
        for (int i = 0; i < strarray.length; i++) {
            bssids.put(strarray[i], i);
        }
        return bssids;
    }
}
