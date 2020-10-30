package com.bksx.mobile.baidutrace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetLocationAddress f = GetLocationAddress.getInstance(mContext);
        f.getAdd();
        Log.i("TAG", "===onCreate: " + f.getX());
        Log.i("TAG", "===onCreate: " + f.getY());
        Log.i("TAG", "===onCreate: " + f.justGetAddress());
    }
}
