package cz.hydradev.newsfeed.newsfeed.recyclerview;

import cz.hydradev.newsfeed.networking.responsepojo.Article;

public interface NewsFeedRowView {
    void setUpArticleCardView(Article article);
}
