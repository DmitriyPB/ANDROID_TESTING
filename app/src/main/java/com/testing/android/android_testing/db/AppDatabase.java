package com.testing.android.android_testing.db;

import android.content.Context;

import com.testing.android.android_testing.db.dao.AppDao;
import com.testing.android.android_testing.db.entity.SpecialtyEntity;
import com.testing.android.android_testing.db.entity.WorkerEntity;
import com.testing.android.android_testing.db.entity.WorkerSpecialtyRelationEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({Converters.class})
@Database(entities = {WorkerEntity.class, SpecialtyEntity.class, WorkerSpecialtyRelationEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "theory";

    public abstract AppDao appDao();

    private static AppDatabase sInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }
}