package cz.hydradev.newsfeed.articledetail;

import net.grandcentrix.thirtyinch.TiView;

import cz.hydradev.newsfeed.sqldelight.data.helpers.SQLiteHelper;

public interface ArticleDetailView extends TiView {

    SQLiteHelper getSQLHelper();

}
