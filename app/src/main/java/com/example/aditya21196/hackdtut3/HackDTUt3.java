package com.example.aditya21196.hackdtut3;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by aditya21196 on 11/2/17.
 */

public class HackDTUt3 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }

}
