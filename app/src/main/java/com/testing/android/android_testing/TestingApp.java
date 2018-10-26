package com.testing.android.android_testing;

import android.app.Application;

import com.testing.android.android_testing.db.AppDatabase;

public class TestingApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public AppExecutors getExecutors() {
        return mAppExecutors;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
