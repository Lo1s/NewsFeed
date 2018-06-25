package cz.hydradev.newsfeed.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightCompiledStatement;
import com.squareup.sqldelight.SqlDelightStatement;
import java.lang.Deprecated;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ArticleSQLiteModel {
  String TABLE_NAME = "articles";

  String ID = "id";

  String AUTHOR = "author";

  String TITLE = "title";

  String DESCRIPTION = "description";

  String URL = "url";

  String URLTOIMAGE = "urlToImage";

  String PUBLISHEDAT = "publishedAt";

  String CONTENT = "content";

  String CREATE_TABLE = ""
      + "CREATE TABLE articles (\n"
      + "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
      + "    author TEXT,\n"
      + "    title TEXT,\n"
      + "    description TEXT,\n"
      + "    url TEXT,\n"
      + "    urlToImage TEXT,\n"
      + "    publishedAt TEXT,\n"
      + "    content TEXT\n"
      + ")";

  long id();

  @Nullable
  String author();

  @Nullable
  String title();

  @Nullable
  String description();

  @Nullable
  String url();

  @Nullable
  String urlToImage();

  @Nullable
  String publishedAt();

  @Nullable
  String content();

  interface Creator<T extends ArticleSQLiteModel> {
    T create(long id, @Nullable String author, @Nullable String title, @Nullable String description,
        @Nullable String url, @Nullable String urlToImage, @Nullable String publishedAt,
        @Nullable String content);
  }

  final class Mapper<T extends ArticleSQLiteModel> implements RowMapper<T> {
    private final Factory<T> articleSQLiteModelFactory;

    public Mapper(Factory<T> articleSQLiteModelFactory) {
      this.articleSQLiteModelFactory = articleSQLiteModelFactory;
    }

    @Override
    public T map(@NonNull Cursor cursor) {
      return articleSQLiteModelFactory.creator.create(
          cursor.getLong(0),
          cursor.isNull(1) ? null : cursor.getString(1),
          cursor.isNull(2) ? null : cursor.getString(2),
          cursor.isNull(3) ? null : cursor.getString(3),
          cursor.isNull(4) ? null : cursor.getString(4),
          cursor.isNull(5) ? null : cursor.getString(5),
          cursor.isNull(6) ? null : cursor.getString(6),
          cursor.isNull(7) ? null : cursor.getString(7)
      );
    }
  }

  final class Marshal {
    protected final ContentValues contentValues = new ContentValues();

    Marshal(@Nullable ArticleSQLiteModel copy) {
      if (copy != null) {
        this.id(copy.id());
        this.author(copy.author());
        this.title(copy.title());
        this.description(copy.description());
        this.url(copy.url());
        this.urlToImage(copy.urlToImage());
        this.publishedAt(copy.publishedAt());
        this.content(copy.content());
      }
    }

    public ContentValues asContentValues() {
      return contentValues;
    }

    public Marshal id(long id) {
      contentValues.put("id", id);
      return this;
    }

    public Marshal author(String author) {
      contentValues.put("author", author);
      return this;
    }

    public Marshal title(String title) {
      contentValues.put("title", title);
      return this;
    }

    public Marshal description(String description) {
      contentValues.put("description", description);
      return this;
    }

    public Marshal url(String url) {
      contentValues.put("url", url);
      return this;
    }

    public Marshal urlToImage(String urlToImage) {
      contentValues.put("urlToImage", urlToImage);
      return this;
    }

    public Marshal publishedAt(String publishedAt) {
      contentValues.put("publishedAt", publishedAt);
      return this;
    }

    public Marshal content(String content) {
      contentValues.put("content", content);
      return this;
    }
  }

  final class Factory<T extends ArticleSQLiteModel> {
    public final Creator<T> creator;

    public Factory(Creator<T> creator) {
      this.creator = creator;
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal() {
      return new Marshal(null);
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal(ArticleSQLiteModel copy) {
      return new Marshal(copy);
    }

    /**
     * @deprecated Use {@link InsertArticle}
     */
    @Deprecated
    public SqlDelightStatement InsertArticle(@Nullable String author, @Nullable String title,
        @Nullable String description, @Nullable String url, @Nullable String urlToImage,
        @Nullable String publishedAt, @Nullable String content) {
      List<String> args = new ArrayList<String>();
      int currentIndex = 1;
      StringBuilder query = new StringBuilder();
      query.append("INSERT INTO articles (\n"
              + "    author,\n"
              + "    title,\n"
              + "    description,\n"
              + "    url,\n"
              + "    urlToImage,\n"
              + "    publishedAt,\n"
              + "    content\n"
              + ")\n"
              + "VALUES (");
      if (author == null) {
        query.append("null");
      } else {
        query.append('?').append(currentIndex++);
        args.add(author);
      }
      query.append(", ");
      if (title == null) {
        query.append("null");
      } else {
        query.append('?').append(currentIndex++);
        args.add(title);
      }
      query.append(", ");
      if (description == null) {
        query.append("null");
      } else {
        query.append('?').append(currentIndex++);
        args.add(description);
      }
      query.append(", ");
      if (url == null) {
        query.append("null");
      } else {
        query.append('?').append(currentIndex++);
        args.add(url);
      }
      query.append(", ");
      if (urlToImage == null) {
        query.append("null");
      } else {
        query.append('?').append(currentIndex++);
        args.add(urlToImage);
      }
      query.append(", ");
      if (publishedAt == null) {
        query.append("null");
      } else {
        query.append('?').append(currentIndex++);
        args.add(publishedAt);
      }
      query.append(", ");
      if (content == null) {
        query.append("null");
      } else {
        query.append('?').append(currentIndex++);
        args.add(content);
      }
      query.append(")");
      return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("articles"));
    }

    public SqlDelightStatement SelectArticleById(long id) {
      List<String> args = new ArrayList<String>();
      int currentIndex = 1;
      StringBuilder query = new StringBuilder();
      query.append("SELECT * FROM articles WHERE id = ");
      query.append(id);
      return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("articles"));
    }

    /**
     * @deprecated Use {@link DeleteArticleById}
     */
    @Deprecated
    public SqlDelightStatement DeleteArticleById(long id) {
      List<String> args = new ArrayList<String>();
      int currentIndex = 1;
      StringBuilder query = new StringBuilder();
      query.append("DELETE FROM articles WHERE id = ");
      query.append(id);
      return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("articles"));
    }

    public SqlDelightStatement SelectAllArticles() {
      return new SqlDelightStatement(""
          + "SELECT * FROM articles ORDER BY id DESC",
          new String[0], Collections.<String>singleton("articles"));
    }

    public Mapper<T> selectArticleByIdMapper() {
      return new Mapper<T>(this);
    }

    public Mapper<T> selectAllArticlesMapper() {
      return new Mapper<T>(this);
    }
  }

  final class InsertArticle extends SqlDelightCompiledStatement.Insert {
    public InsertArticle(SQLiteDatabase database) {
      super("articles", database.compileStatement(""
              + "INSERT INTO articles (\n"
              + "    author,\n"
              + "    title,\n"
              + "    description,\n"
              + "    url,\n"
              + "    urlToImage,\n"
              + "    publishedAt,\n"
              + "    content\n"
              + ")\n"
              + "VALUES (?, ?, ?, ?, ?, ?, ?)"));
    }

    public void bind(@Nullable String author, @Nullable String title, @Nullable String description,
        @Nullable String url, @Nullable String urlToImage, @Nullable String publishedAt,
        @Nullable String content) {
      if (author == null) {
        program.bindNull(1);
      } else {
        program.bindString(1, author);
      }
      if (title == null) {
        program.bindNull(2);
      } else {
        program.bindString(2, title);
      }
      if (description == null) {
        program.bindNull(3);
      } else {
        program.bindString(3, description);
      }
      if (url == null) {
        program.bindNull(4);
      } else {
        program.bindString(4, url);
      }
      if (urlToImage == null) {
        program.bindNull(5);
      } else {
        program.bindString(5, urlToImage);
      }
      if (publishedAt == null) {
        program.bindNull(6);
      } else {
        program.bindString(6, publishedAt);
      }
      if (content == null) {
        program.bindNull(7);
      } else {
        program.bindString(7, content);
      }
    }
  }

  final class DeleteArticleById extends SqlDelightCompiledStatement.Delete {
    public DeleteArticleById(SQLiteDatabase database) {
      super("articles", database.compileStatement(""
              + "DELETE FROM articles WHERE id = ?"));
    }

    public void bind(long id) {
      program.bindLong(1, id);
    }
  }
}
