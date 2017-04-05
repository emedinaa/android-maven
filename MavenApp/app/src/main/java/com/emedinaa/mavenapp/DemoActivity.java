package com.emedinaa.mavenapp;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class DemoActivity extends AppCompatActivity {

    private static final String TAG = "DemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        app();
    }

    private void app() {
        String message= "Code fun ! ";
        /*
        StringHelper stringHelper= new StringHelper();
        message= stringHelper.checkNotNull(message);

        ScreenHelper screenHelper= new ScreenHelper();
        Point point= screenHelper.getDeviceDimention(this);

        Log.d(TAG, "app: "+message);
        Log.d(TAG, "screen X "+point.x+ " screen Y "+point.y);*/
    }
}
