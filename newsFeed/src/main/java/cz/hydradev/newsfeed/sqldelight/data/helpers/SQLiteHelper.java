package cz.hydradev.newsfeed.sqldelight.data.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.squareup.sqldelight.SqlDelightStatement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.hydradev.newsfeed.data.model.ArticleSQLiteModel;
import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.sqldelight.data.model.ArticleSQLite;


public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "sqldelight.db";
    public static final int DB_VERSION = 1;
    private static SQLiteHelper instance;

    private SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static SQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ArticleSQLite.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS articles");
    }

    public void insertArticle(Article article, String filename) {
        ArticleSQLite.InsertArticle insertArticle = new ArticleSQLiteModel.InsertArticle(getWritableDatabase());
        insertArticle.bind(
                article.getAuthor(),
                article.getTitle(),
                article.getDescription(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getPublishedAt(),
                filename);
        insertArticle.program.execute();
        insertArticle.program.close();
    }

    public List<Article> selectAllArticles() {
        List<Article> articles = new ArrayList<>();
        SqlDelightStatement query = ArticleSQLite.FACTORY.SelectAllArticles();
        Cursor cursor = getReadableDatabase().rawQuery(query.statement, query.args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            articles.add(getArticleFromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return articles;
    }

    private Article getArticleFromCursor(Cursor cursor) {
        Article article = new Article();
        article.setId(cursor.getLong(0));
        article.setAuthor(cursor.getString(1));
        article.setTitle(cursor.getString(2));
        article.setDescription(cursor.getString(3));
        article.setUrl(cursor.getString(4));
        article.setUrlToImage(cursor.getString(5));
        article.setPublishedAt(cursor.getString(6));
        return article;
    }

    public void deleteArticleById(long id) {
        ArticleSQLite.DeleteArticleById deleteArticleById= new ArticleSQLiteModel.DeleteArticleById(getWritableDatabase());
        deleteArticleById.bind(id);
        deleteArticleById.program.execute();
        deleteArticleById.program.close();
    }
}
