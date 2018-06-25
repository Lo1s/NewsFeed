package cz.hydradev.newsfeed.sources.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.sources.SourcesPresenter;

public class SourcesRecyclerAdapter extends RecyclerView.Adapter<SourcesRecyclerViewHolder> {

    private final SourcesPresenter presenter;

    public SourcesRecyclerAdapter(SourcesPresenter sourcesPresenter) {
        this.presenter = sourcesPresenter;
    }

    @Override
    public SourcesRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_source_row, parent, false);
        return new SourcesRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SourcesRecyclerViewHolder holder, int position) {
        presenter.onBindRecyclerViewArticleViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getSourcesCount();
    }

}
