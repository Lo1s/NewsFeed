package cz.hydradev.newsfeed.articledetail;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.io.File;

import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.sqldelight.data.helpers.SQLiteHelper;
import cz.hydradev.newsfeed.utils.Utils;

public class ArticleDetailPresenter extends TiPresenter<ArticleDetailView> {

    private SQLiteHelper sqliteHelper;

    @Override
    protected void onAttachView(@NonNull ArticleDetailView view) {
        super.onAttachView(view);
        sqliteHelper = view.getSQLHelper();
    }

    public void saveArticle(Article article, String filename) {
        Log.i("Article Content", filename);
        sqliteHelper.insertArticle(article, filename);
    }

    public String getFileName(String url) {
        return Utils.getFileName(url);
    }
}
