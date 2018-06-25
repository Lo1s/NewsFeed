package cz.hydradev.newsfeed.newsfeed.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.newsfeed.NewsFeedPresenter;

public class NewsFeedRecyclerAdapter extends RecyclerView.Adapter<NewsFeedRecyclerViewHolder> {

    private final NewsFeedPresenter presenter;

    public NewsFeedRecyclerAdapter(NewsFeedPresenter newsFeedPresenter) {
        this.presenter = newsFeedPresenter;
    }

    @Override
    public NewsFeedRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_article_row, parent, false);
        return new NewsFeedRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsFeedRecyclerViewHolder holder, int position) {
        presenter.onBindRecyclerViewArticleViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getNewsArticlesCount();
    }
}
