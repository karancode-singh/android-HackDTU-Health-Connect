package com.example.aditya21196.hackdtut3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static int TIME_OUT = 1500; //Time to launch the another activity


    public static String _email;
    public static String _bloodChoice;
    public static String _Age;
    public static String _sex;
    public static String _name;
    public static String _PhnNo;
    public static String _state;
    public static Boolean _rBool;
    public static Boolean _tBlood=false;
    public static Boolean _tSperm=false;
    public static Boolean _tMarrow=false;
    public static Boolean _tHair=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        _rBool=false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }


        }, TIME_OUT);


    }
}
//change
