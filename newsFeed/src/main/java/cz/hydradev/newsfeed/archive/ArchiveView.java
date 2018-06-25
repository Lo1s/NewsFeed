package cz.hydradev.newsfeed.archive;

import net.grandcentrix.thirtyinch.TiView;

import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.sqldelight.data.model.ArticleSQLite;

public interface ArchiveView extends TiView {

    void initRecyclerView();
    void goToArticleDetailActivity(Article article);
    void showEmptyArchive();
}
