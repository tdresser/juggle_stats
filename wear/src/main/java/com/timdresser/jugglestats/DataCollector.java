package com.timdresser.jugglestats;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;

public class DataCollector implements SensorEventListener {
    private class Entry {
        long mTimeStamp;
        float mAx;
        float mAy;
        float mAz;

        Entry (long timestamp, float ax, float ay, float az) {
            mTimeStamp = timestamp;
            mAx = ax;
            mAy = ay;
            mAz = az;
        }

        @Override
        public String toString() {
            return String.format("%d: %f, %f, %f", mTimeStamp, mAx, mAy, mAz);
        }
    }


    private SensorManager mSensorManager;
    private Sensor mSensor;

    DataCollector(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mEntries = new ArrayList<Entry>();
    }

    ArrayList<Entry> mEntries;

    @Override
    public void onSensorChanged(SensorEvent event) {
        mEntries.add(new Entry(event.timestamp, event.values[0], event.values[1], event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void startRecording() {
        mEntries.clear();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopRecording() {
        mSensorManager.unregisterListener(this);
    }

    public void clearRecording() {
        mEntries.clear();
    }

    public void logRecording() {
        for (Entry entry : mEntries) {
            Log.v("TAG", entry.toString());
        }
    }

    public String getRecordedDataAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : mEntries) {
            stringBuilder.append(entry.toString() + "\n");
        }
        return stringBuilder.toString();
    }
}
