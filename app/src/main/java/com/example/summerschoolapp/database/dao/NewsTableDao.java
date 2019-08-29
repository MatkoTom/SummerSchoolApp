package com.example.summerschoolapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.summerschoolapp.database.entity.NewsArticle;

import java.util.List;

@Dao
public interface NewsTableDao {

    @Query("SELECT * FROM NewsArticle ORDER BY article_id DESC")
    LiveData<List<NewsArticle>> selectAllNews();

    @Insert
    void insert (NewsArticle article);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long[] insertList(List<NewsArticle> articleList);

    @Delete
    void delete (NewsArticle article);

    @Update
    void update (NewsArticle article);
}
