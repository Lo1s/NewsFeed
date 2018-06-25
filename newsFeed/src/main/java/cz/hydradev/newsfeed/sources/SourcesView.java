package cz.hydradev.newsfeed.sources;

import net.grandcentrix.thirtyinch.TiView;

public interface SourcesView extends TiView {

    void initRecyclerView();
    void saveSelectedSourceId(String sourceId);
    void goToNewsFeedActivity();
    void showSnackbar(int resId);
}
