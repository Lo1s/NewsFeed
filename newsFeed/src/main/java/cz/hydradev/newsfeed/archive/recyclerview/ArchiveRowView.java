package cz.hydradev.newsfeed.archive.recyclerview;

import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.sqldelight.data.model.ArticleSQLite;

public interface ArchiveRowView {

    void setUpArticleCardView(Article article);

}
