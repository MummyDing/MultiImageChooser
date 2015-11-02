package com.demo.mummyding.photowall;

import android.app.Application;
import android.content.Context;

/**
 * Created by mummyding on 15-11-2.
 */
public class App extends Application {
    public static  Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
