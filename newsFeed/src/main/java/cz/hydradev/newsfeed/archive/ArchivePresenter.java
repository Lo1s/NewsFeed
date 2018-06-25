package cz.hydradev.newsfeed.archive;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;
import java.util.List;

import cz.hydradev.newsfeed.archive.recyclerview.ArchiveRowView;
import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.sqldelight.data.helpers.SQLiteHelper;
import cz.hydradev.newsfeed.sqldelight.data.model.ArticleSQLite;


public class ArchivePresenter extends TiPresenter<ArchiveView> {

    private List<Article> articles = new ArrayList<>();
    private SQLiteHelper sqLiteHelper;

    public ArchivePresenter(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
    }

    @Override
    protected void onAttachView(@NonNull ArchiveView view) {
        super.onAttachView(view);
    }

    public void onBindRecyclerViewArticleViewAtPosition(int position, ArchiveRowView viewHolder) {
        Article article = articles.get(position);
        viewHolder.setUpArticleCardView(article);
    }

    public void getArticles() {
        articles = sqLiteHelper.selectAllArticles();
        if (articles != null && articles.size() > 0) {
            getView().initRecyclerView();
        } else {
            getView().showEmptyArchive();
        }
        Log.i("Articles", (articles == null ? "Articles are null!" : articles.toString()));
    }

    public void selectArticle(int position) {
        Log.i("Selected Article", position + "");
        Article article = articles.get(position);
        getView().goToArticleDetailActivity(article);
    }

    public void deleteArticle(int position, RecyclerView.Adapter adapter) {
        Log.i("Delete Article", position + "");
        Article article = articles.get(position);
        articles.remove(position);
        sqLiteHelper.deleteArticleById(article.getId());
        adapter.notifyDataSetChanged();
    }

    public int getNewsArticlesCount() {
        if (articles.size() == 0) {
            getView().showEmptyArchive();
        }
        return articles.size();
    }
}
