package com.example.summerschoolapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.summerschoolapp.database.dao.NewsTableDao;
import com.example.summerschoolapp.database.entity.NewsArticle;

@Database(entities = {NewsArticle.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {
    public abstract NewsTableDao newsTableDao();

    private static volatile RoomDb instance;

    public static RoomDb getInstance(final Context context) {
        if (instance == null) {
            synchronized (RoomDb.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDb.class, "news_database")
                            .build();
                }
            }
        }
        return instance;
    }

    public static RoomDatabase create(Context context) {
        return Room.databaseBuilder(context, RoomDb.class, "RoomDb")
                .build();
    }
}
