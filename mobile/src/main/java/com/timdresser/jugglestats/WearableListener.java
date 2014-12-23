package com.timdresser.jugglestats;

import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class WearableListener extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v(MainActivity.TAG, "MESSAGE RECEIVED");
        Log.v(MainActivity.TAG, messageEvent.getPath());
        Log.v(MainActivity.TAG, new String(messageEvent.getData()));
        super.onMessageReceived(messageEvent);
    }
}
