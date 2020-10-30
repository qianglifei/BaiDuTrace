package com.bksx.mobile.baidutrace;

import android.app.Application;
/**
 *
 * @author :qlf
 *
 */
public class MyApplicaiton extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
