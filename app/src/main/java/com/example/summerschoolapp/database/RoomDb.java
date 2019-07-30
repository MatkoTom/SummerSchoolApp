package com.example.summerschoolapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.summerschoolapp.database.dao.NewsTableDao;
import com.example.summerschoolapp.database.entity.NewsArticle;

@Database(entities = {NewsArticle.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {
    public abstract NewsTableDao newsTableDao();
}
