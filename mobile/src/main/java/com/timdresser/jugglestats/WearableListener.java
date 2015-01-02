package com.timdresser.jugglestats;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WearableListener extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        String text = new String(messageEvent.getData());
        File file = new File(((Context)this).getExternalFilesDir(null), "juggle_stats.csv");

        try {
            if (!file.exists())
                file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false /*append*/));
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        Uri uri = Uri.parse("file://" + file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        Intent chooserIntent = Intent.createChooser(intent, "Email Juggling Statistics.");
        chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(chooserIntent);
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
