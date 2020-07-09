package com.opus_bd.myapplication.Activity.VoiceCall;

import android.app.Application;
import android.os.Build;

import com.opus_bd.myapplication.Utils.Voicecall;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.CallClient;

public class Apps extends Application {

    public static String USER_ID;
    public static SinchClient sinchClient;
    public static CallClient callClient;

    @Override
    public void onCreate() {
        super.onCreate();
        USER_ID=(""+(Build.FINGERPRINT+ Build.MODEL).hashCode()).replace("-","");
        sinchClient = Sinch.getSinchClientBuilder().context(this)
                .applicationKey(Voicecall.KEY)
                .applicationSecret(Voicecall.SECERET)
                .environmentHost(Voicecall.HOSTNAME)
                .userId(USER_ID)
                .build();
        sinchClient.setSupportActiveConnectionInBackground(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.setSupportCalling(true);
    }
}
