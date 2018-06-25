package cz.hydradev.newsfeed.newsfeed.recyclerview;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.utils.Utils;

public class NewsFeedRecyclerViewHolder extends RecyclerView.ViewHolder implements NewsFeedRowView {

    private CardView cardView;

    public NewsFeedRecyclerViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cardViewArticle);
    }

    @Override
    public void setUpArticleCardView(Article article) {
        setUpTextViews(article);
        setUpImageViews(article);
    }

    private void setUpTextViews(Article article) {
        TextView textViewPublishedAt = (TextView) cardView.findViewById(R.id.textViewPublishedAt);
        TextView textViewTitle = (TextView) cardView.findViewById(R.id.textViewTitle);
        TextView textViewDescription = (TextView) cardView.findViewById(R.id.textViewDescription);

        if (article.getPublishedAt() != null && article.getPublishedAt().length() > 0 ) {
            textViewPublishedAt.setText(Utils.reformatPublishedAt(article.getPublishedAt()));
        } else {
            textViewPublishedAt.setText(article.getPublishedAt());
        }

        textViewTitle.setText(article.getTitle());
        textViewDescription.setText(article.getDescription());
    }

    private void setUpImageViews(Article article) {
        ImageView imageView = (ImageView) cardView.findViewById(R.id.imageViewArticleCover);

        if (article.getUrlToImage().isEmpty()) {
            imageView.setImageResource(R.drawable.news_placeholder);
        } else {
            Picasso.with(imageView.getContext())
                    .load(article.getUrlToImage())
                    .placeholder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.news_placeholder))
                    .into(imageView);
        }
    }
}
