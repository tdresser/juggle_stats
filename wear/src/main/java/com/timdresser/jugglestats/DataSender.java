package com.timdresser.jugglestats;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class DataSender implements GoogleApiClient.ConnectionCallbacks{
    private static final String SEND_DATA = "/send_data";

    private Context mContext;
    private GoogleApiClient mApiClient;

    DataSender(Context context) {
        mContext = context;
        mApiClient = new GoogleApiClient.Builder(context)
                .addApi( Wearable.API )
                .build();

        mApiClient.connect();
    }

    public void cleanup() {
        mApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("JUGGLING", "On connected");
        sendMessage(SEND_DATA, "This is a test.");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void sendMessage( final String path, final String text ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                }
            }
        }).start();
    }
}
