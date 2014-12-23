package com.timdresser.jugglestats;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RecordButtonFragment extends Fragment {
    private Button mStartRecordingButton;
    private Button mStopRecordingButton;
    private Button mSaveRecordingButton;

    private DataCollector mDataCollector;
    private DataSender mDataSender;

    public RecordButtonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_record_button, container, false);

        mDataCollector = new DataCollector(getActivity());
        mDataSender = new DataSender(getActivity());
        mStartRecordingButton = (Button) fragment.findViewById((R.id.button_start_recording));
        mStopRecordingButton = (Button) fragment.findViewById((R.id.button_stop_recording));
        mSaveRecordingButton = (Button) fragment.findViewById((R.id.button_save_recording));

        mStartRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartRecordingButton.setVisibility(View.GONE);
                mStopRecordingButton.setVisibility(View.VISIBLE);
                mSaveRecordingButton.setVisibility(View.GONE);

                mDataCollector.startRecording();
            }
        });

        mStopRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartRecordingButton.setVisibility(View.VISIBLE);
                mStopRecordingButton.setVisibility(View.GONE);
                mSaveRecordingButton.setVisibility(View.VISIBLE);

                mDataCollector.stopRecording();
            }
        });

        mSaveRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartRecordingButton.setVisibility(View.VISIBLE);
                mStopRecordingButton.setVisibility(View.GONE);
                mSaveRecordingButton.setVisibility(View.GONE);
                mDataSender.sendMessage("/send_data", mDataCollector.getRecordedDataAsString());
                mDataCollector.clearRecording();
            }
        });
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataSender.cleanup();
    }
}
