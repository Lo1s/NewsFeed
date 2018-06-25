package cz.hydradev.newsfeed.archive.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.archive.ArchivePresenter;

public class ArchiveRecyclerAdapter extends RecyclerView.Adapter<ArchiveRecyclerViewHolder> {

    private final ArchivePresenter presenter;

    public ArchiveRecyclerAdapter(ArchivePresenter archivePresenter) {
        this.presenter = archivePresenter;
    }

    @Override
    public ArchiveRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_article_row, parent, false);
        return new ArchiveRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArchiveRecyclerViewHolder holder, int position) {
        presenter.onBindRecyclerViewArticleViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getNewsArticlesCount();
    }
}