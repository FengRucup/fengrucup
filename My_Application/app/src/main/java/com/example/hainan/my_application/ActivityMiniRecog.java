package com.example.hainan.my_application;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityMiniRecog extends AppCompatActivity implements EventListener {
    protected String txtResult = "";
    ImageButton btnspeak;
    ImageButton btnspeak2;
    ImageButton btnresult;

    Chronometer chronometer;

    private EventManager asr;
    private boolean enableOffline = false; // 测试离线命令词，需要改成true
    private void start() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event
        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.NLU, "enable");
        params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0); // 长语音
        // params.put(SpeechConstant.IN_FILE, "res:///com/baidu/android/voicedemo/16k_test.pcm");
        //params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
        //params.put(SpeechConstant.PROP ,20000);
        params.put(SpeechConstant.PID, 1537); // 中文输入法模型，有逗号
        String json = null; // 可以替换成自己的json
        json = new JSONObject(params).toString();
        asr.send(event, json, null, 0, 0);
    }

    private void stop() {
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }

    private void loadOfflineEngine() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
    }

    private void unloadOfflineEngine() {
        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0); //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initPermission();
        asr = EventManagerFactory.create(this, "asr");
        asr.registerListener(this); //  EventListener 中 onEvent方法
        btnspeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtResult = "";


                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                start();

            }
        });
        btnspeak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                stop();
            }
        });
        btnresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMiniRecog.this,ActivityResult.class);
                intent.putExtra("r",txtResult);
                startActivity(intent);
            }
        });
        if (enableOffline) {
            loadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        if (enableOffline) {
            unloadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
    }

    //   EventListener  回调方法
    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String resTxt = "";
        String reg = "\"best_result\":\".*\"";
        Pattern p = Pattern.compile(reg);
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            if (params.contains("\"final_result\"")) {
                Matcher m = p.matcher(params);
                while (m.find()){
                    String[] temp = m.group().split(":");
                    temp = temp[1].split("\"");

                    txtResult += temp[1]+"\n";
                }
            }

        }

    }

    private void initView() {
        btnspeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnspeak2 = (ImageButton)findViewById(R.id.btnSpeak2);
        btnresult = (ImageButton)findViewById(R.id.btnResult);
        chronometer= (Chronometer)findViewById(R.id.chronometer);
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

}