package com.factory.smart.http;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangxin
 * @date 2022/9/12
 */
public class HostInitializer implements Initializer<Void> {
    private static final String TAG = "HostInitializer";

    @Override
    public Void create(@NonNull Context context) {
        Log.d(TAG, "create: host init");
        try {
            InputStream inputStream = context.getAssets().open("grs_config.json");
            InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                sb.append(lineStr);
            }
            br.close();
            isr.close();
            inputStream.close();
            JSONObject jsonObject = JSON.parseObject(sb.toString());
            HashMap<String, String> map = new HashMap<>();
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                map.put(entry.getKey(), (String) entry.getValue());
            }
            HostsManger.getInstance().loadHosts(map);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
