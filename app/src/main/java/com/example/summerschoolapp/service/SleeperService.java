package com.example.summerschoolapp.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import static com.example.summerschoolapp.utils.Const.SLEEP_DONE;

public class SleeperService extends IntentService {

    public SleeperService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent i = new Intent(SLEEP_DONE);
        sendBroadcast(i);
    }
}
