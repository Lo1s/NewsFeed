package cz.hydradev.newsfeed.sqldelight.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import cz.hydradev.newsfeed.data.model.ArticleSQLiteModel;


@AutoValue
public abstract class ArticleSQLite implements ArticleSQLiteModel {

    public static final Creator<ArticleSQLite> CREATOR = new Creator<ArticleSQLite>() {

        @Override
        public ArticleSQLite create(long id,
                                         @Nullable String author,
                                         @Nullable String title,
                                         @Nullable String description,
                                         @Nullable String url,
                                         @Nullable String urlToImage,
                                         @Nullable String publishedAt,
                                         @Nullable String content) {
            return null;
        }
    };

    public static final Factory<ArticleSQLite> FACTORY = new Factory<>(CREATOR);
    public static final Mapper<ArticleSQLite> MAPPER = new Mapper<>(FACTORY);
}
