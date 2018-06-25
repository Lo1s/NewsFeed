package cz.hydradev.newsfeed.sources.recyclerview;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.networking.responsepojo.Source;

public class SourcesRecyclerViewHolder extends RecyclerView.ViewHolder implements SourcesRowView {

    private CardView cardView;

    public SourcesRecyclerViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cardViewSource);
    }

    @Override
    public void setUpSourceCardView(Source source) {
        setUpTextViews(source);
        setUpImageViews(source);
    }


    private void setUpTextViews(Source source) {
        TextView textViewName = (TextView) cardView.findViewById(R.id.textViewName);
        TextView textViewCategory = (TextView) cardView.findViewById(R.id.textViewCategory);
        textViewName.setText(source.getName());
        textViewCategory.setText(source.getCategory());
    }

    private void setUpImageViews(Source source) {
        ImageView imageView = (ImageView) cardView.findViewById(R.id.imageViewSourceCover);

        Picasso.with(imageView.getContext())
                .load("https://icons.better-idea.org/icon?url=" + source.getUrl() + "&size=" + "70..120..200")
                .resize(150, 150)
                .centerInside()
                .placeholder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.source_placeholder))
                .into(imageView);
    }



}
