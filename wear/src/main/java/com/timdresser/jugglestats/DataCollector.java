package com.timdresser.jugglestats;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class DataCollector implements SensorEventListener {
    private class Entry {
        long mTimeStamp;
        float mAx;
        float mAy;
        float mAz;
        float mGx;
        float mGy;
        float mGz;

        Entry () {
            mTimeStamp = 0;
            mAx = 0;
            mAy = 0;
            mAz = 0;
            mGx = 0;
            mGy = 0;
            mGz = 0;
        }

        @Override
        public String toString() {
            return String.format("%d: %f, %f, %f, %f, %f, %f", mTimeStamp, mAx, mAy, mAz, mGx, mGy, mGz);
        }
    }


    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorGyro;

    DataCollector(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mEntries = new HashMap<Long, Entry>();
    }

    HashMap<Long, Entry> mEntries;

    @Override
    public void onSensorChanged(SensorEvent event) {
        Entry entry = mEntries.get(event.timestamp);
        if (entry == null) {
            entry = new Entry();
            entry.mTimeStamp = event.timestamp;
            mEntries.put(event.timestamp, entry);
        }
        if (event.sensor == mSensorAccelerometer) {
          entry.mAx = event.values[0];
          entry.mAy = event.values[1];
          entry.mAz = event.values[2];
        } else if (event.sensor == mSensorGyro) {
          entry.mGx = event.values[0];
          entry.mGy = event.values[1];
          entry.mGz = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void startRecording() {
        mEntries.clear();
        mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorGyro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopRecording() {
        mSensorManager.unregisterListener(this);
    }

    public void clearRecording() {
        mEntries.clear();
    }

    public String getRecordedDataAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Long, Entry> mapEntry : mEntries.entrySet()) {
            stringBuilder.append(mapEntry.getValue().toString() + "\n");
        }
        return stringBuilder.toString();
    }
}
