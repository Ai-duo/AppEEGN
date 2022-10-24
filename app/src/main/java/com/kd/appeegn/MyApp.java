package com.kd.appeegn;

import android.app.Application;

import androidx.annotation.NonNull;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
            ///    throwable.printStackTrace();
                System.exit(0);
            }
        });
    }
}
