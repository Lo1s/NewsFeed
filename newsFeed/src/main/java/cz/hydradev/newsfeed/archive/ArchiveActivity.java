package cz.hydradev.newsfeed.archive;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.archive.recyclerview.ArchiveRecyclerAdapter;
import cz.hydradev.newsfeed.articledetail.ArticleDetailActivity;
import cz.hydradev.newsfeed.constants.GlobalConstants;
import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.newsfeed.NewsFeedActivity;
import cz.hydradev.newsfeed.sources.SourcesActivity;
import cz.hydradev.newsfeed.sqldelight.data.helpers.SQLiteHelper;
import cz.hydradev.newsfeed.utils.RecyclerItemClickListener;

public class ArchiveActivity extends TiActivity<ArchivePresenter, ArchiveView> implements ArchiveView {

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.recyclerViewArticles) RecyclerView recyclerViewArticles;
    @BindView(R.id.textViewEmpty) TextView textViewEmpty;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        ButterKnife.bind(this);
        initBottomNavigationView();
        setUpTransitionAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getArticles();
    }

    private void setUpTransitionAnimation() {
        int animIn = getIntent().getIntExtra(GlobalConstants.ANIM_IN, R.anim.enter_from_right);
        int animOut = getIntent().getIntExtra(GlobalConstants.ANIM_OUT, R.anim.exit_to_left);
        overridePendingTransition(animIn, animOut);
    }

    @NonNull
    @Override
    public ArchivePresenter providePresenter() {
        return new ArchivePresenter(SQLiteHelper.getInstance(this));
    }

    @Override
    public void initRecyclerView() {
        textViewEmpty.setVisibility(View.GONE);
        recyclerViewArticles.setVisibility(View.VISIBLE);
        adapter = new ArchiveRecyclerAdapter(getPresenter());
        layoutManager = new LinearLayoutManager(this);
        recyclerViewArticles.setLayoutManager(layoutManager);
        recyclerViewArticles.setAdapter(adapter);
        recyclerViewArticles.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerViewArticles,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                viewOrDeleteArticle(position);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
    }

    private void viewOrDeleteArticle(int positon) {
        AlertDialog viewOrDeleteDialog = createAlertDialog(positon);
        viewOrDeleteDialog.show();
    }

    private AlertDialog createAlertDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.archive_article_dialog_message)
                .setTitle(R.string.archive_article_dialog_title);
        builder.setPositiveButton(R.string.archive_article_dialog_view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().selectArticle(position);
            }
        });
        builder.setNegativeButton(R.string.archive_article_dialog_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().deleteArticle(position, adapter);
                Snackbar.make(recyclerViewArticles,
                        R.string.archive_article_snackbar_delete, Snackbar.LENGTH_LONG)
                        .show();
            }
        });
        return builder.create();
    }

    @Override
    public void showEmptyArchive() {
        recyclerViewArticles.setVisibility(View.GONE);
        textViewEmpty.setVisibility(View.VISIBLE);
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_news:
                                goToNewsFeedActivity();
                                break;
                            case R.id.action_sources:
                                goToSourcesActivity();

                                break;

                            case R.id.action_archive:

                                break;
                        }
                        return true;
                    }
                });
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }

    private void goToSourcesActivity() {
        Intent intent = new Intent(this, SourcesActivity.class);
        intent.putExtra(GlobalConstants.ANIM_IN, R.anim.enter_from_left);
        intent.putExtra(GlobalConstants.ANIM_OUT, R.anim.exit_to_right);
        startActivity(intent);
    }

    private void goToNewsFeedActivity() {
        Intent intent = new Intent(this, NewsFeedActivity.class);
        intent.putExtra(GlobalConstants.ANIM_IN, R.anim.enter_from_left);
        intent.putExtra(GlobalConstants.ANIM_OUT, R.anim.exit_to_right);
        startActivity(intent);
    }

    @Override
    public void goToArticleDetailActivity(Article article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra(GlobalConstants.SELECTED_ARTICLE, article);
        intent.putExtra(GlobalConstants.IS_OFFLINE_ARTICLE, true);
        startActivity(intent);
    }

}
