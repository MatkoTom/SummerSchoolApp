package com.example.summerschoolapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.summerschoolapp.database.dao.NewsTableDao;
import com.example.summerschoolapp.database.entity.NewsArticle;

// TODO @Matko
// when you start to implement this call me :)
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
}
