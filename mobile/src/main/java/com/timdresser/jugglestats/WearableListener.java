package com.timdresser.jugglestats;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class WearableListener extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        Log.v(MainActivity.TAG, "MESSAGE RECEIVED");
        Log.v(MainActivity.TAG, messageEvent.getPath());
        Log.v(MainActivity.TAG, new String(messageEvent.getData()));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
        //intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, new String(messageEvent.getData()));

        Intent chooserIntent = Intent.createChooser(intent, "Email Juggling Statistics.");
        chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(chooserIntent);
    }
}
