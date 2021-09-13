package com.example.gs20119service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";
    public MyService(){
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate() 호출됨");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){ // 다른곳에서 startService 호출되었을때
        Log.d(TAG, "onStartCommand() 호출됨");
        if(intent != null) processCommand(intent);
        else return Service.START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void processCommand(Intent intent){
        String command = intent.getStringExtra("command");
        String name = intent.getStringExtra("name");
        Log.d(TAG, "command : " + command + ", name : " + name); // 서버의 로그에 기록 및 처리할수 있음

        for(int i=0; i<5; i++){
            try{ Thread.sleep(1000); }
            catch (Exception e){};
            Log.d(TAG, "Waiting " + i + " seconds");
        }

        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class); // 수신자 : MainActivity
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        showIntent.putExtra("command", "show");
        showIntent.putExtra("name", name); // 신호에 들어가는 내용 추가
        startActivity(showIntent); // 신호 보내기 (Activity 시작)
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}