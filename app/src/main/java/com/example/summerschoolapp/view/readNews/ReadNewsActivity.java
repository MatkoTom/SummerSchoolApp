package com.example.summerschoolapp.view.readNews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.database.entity.NewsArticle;
import com.example.summerschoolapp.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadNewsActivity extends BaseActivity {

    @BindView(R.id.tv_read_news_title)
    TextView tvReadNewsTitle;

    @BindView(R.id.tv_read_news_message)
    TextView tvReadNewsMessage;

    @BindView(R.id.tv_read_news_author)
    TextView tvReadNewsAuthor;

    @BindView(R.id.tv_read_news_date)
    TextView tvReadNewsDate;

    @BindView(R.id.iv_read_news)
    ImageView ivReadNews;

    public static void StartActivity(Context context, NewsArticle article) {
        Intent intent = new Intent(context, ReadNewsActivity.class);
        intent.putExtra(Const.Intent.NEWS_DATA, article);
        context.startActivity(intent);
    }

    NewsArticle newsForEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_read);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().getExtras() != null) {
            newsForEditing = getIntent().getParcelableExtra(Const.Intent.NEWS_DATA);
        }
        setData();
    }

    private void setData() {

        String image = newsForEditing.getImages().substring(newsForEditing.getImages().indexOf("[") + 1, newsForEditing.getImages().indexOf("]"));
        image = image.replace("\"", "");

        Glide.with(this)
                .asBitmap()
                .load(Const.Api.API_GET_IMAGE + image)
                .into(ivReadNews);

        tvReadNewsTitle.setText(newsForEditing.getTitle_log());
        tvReadNewsMessage.setText(newsForEditing.getMessage_log());
        tvReadNewsAuthor.setText(String.format("%s %s", newsForEditing.getFirst_name(), newsForEditing.getLast_name()));
        tvReadNewsDate.setText(newsForEditing.getCreated_at());

    }

    @OnClick(R.id.ibtn_back)
    public void ibtnGoBack() {
        finish();
    }

    @OnClick(R.id.tv_back)
    public void tvGoBack() {
        finish();
    }
}
