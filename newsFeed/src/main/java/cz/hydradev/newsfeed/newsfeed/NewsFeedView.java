package cz.hydradev.newsfeed.newsfeed;

import net.grandcentrix.thirtyinch.TiView;

import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.sqldelight.data.helpers.SQLiteHelper;

public interface NewsFeedView extends TiView {

    void initRecyclerView();
    void showSnackbar(int resId);
}
