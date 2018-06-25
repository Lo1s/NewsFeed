package cz.hydradev.newsfeed.newsfeed;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import cz.hydradev.newsfeed.ArticleWebViewClient;
import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.archive.ArchiveActivity;
import cz.hydradev.newsfeed.articledetail.ArticleDetailActivity;
import cz.hydradev.newsfeed.constants.GlobalConstants;
import cz.hydradev.newsfeed.general.RxApplication;
import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.newsfeed.recyclerview.NewsFeedRecyclerAdapter;
import cz.hydradev.newsfeed.sources.SourcesActivity;
import cz.hydradev.newsfeed.sqldelight.data.helpers.SQLiteHelper;
import cz.hydradev.newsfeed.utils.RecyclerItemClickListener;
import cz.hydradev.newsfeed.utils.Utils;

import static cz.hydradev.newsfeed.constants.GlobalConstants.READ_WRITE_PERMISSIONS;

public class NewsFeedActivity extends TiActivity<NewsFeedPresenter, NewsFeedView>
        implements NewsFeedView {

    @BindView(R.id.recyclerViewArticles) RecyclerView recyclerViewArticles;
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @Nullable
    @BindView(R.id.webViewArticlePreview) WebView webViewArticlePreview;
    @Nullable
    @BindView(R.id.fabSaveArticlePreview) FloatingActionButton fabSaveArticlePreview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private boolean useCachedObservable;
    private int articlePreviewPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this);
        initBottomNavigationView();
        setUpTransitionAnimation();
        if (savedInstanceState != null) {
            useCachedObservable = savedInstanceState.getBoolean(
                    GlobalConstants.SAVED_INSTANCE_RX_IN_WORKS, false);
        } else {
            useCachedObservable = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String selectedSourceId = getSelectedSourceId();
        if (selectedSourceId.length() > 0) {
            Log.i("Selected Source", selectedSourceId);
            getPresenter().getArticles(selectedSourceId, !useCachedObservable, useCachedObservable);
        } else {
            goToSourcesActivity();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(GlobalConstants.SAVED_INSTANCE_RX_IN_WORKS, true);
    }

    private void setUpTransitionAnimation() {
        int animIn = getIntent().getIntExtra(GlobalConstants.ANIM_IN, R.anim.enter_from_left);
        int animOut = getIntent().getIntExtra(GlobalConstants.ANIM_OUT, R.anim.exit_to_right);
        overridePendingTransition(animIn, animOut);
    }

    @NonNull
    @Override
    public NewsFeedPresenter providePresenter() {
        return new NewsFeedPresenter(
                ((RxApplication) getApplication()).getNewsRetrofitClient());
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_news:

                        break;
                    case R.id.action_sources:
                        goToSourcesActivity();

                        break;

                    case R.id.action_archive:
                        goToArchiveActivity();
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void initRecyclerView() {
        adapter = new NewsFeedRecyclerAdapter(getPresenter());
        layoutManager = new LinearLayoutManager(this);
        recyclerViewArticles.setLayoutManager(layoutManager);
        recyclerViewArticles.setAdapter(adapter);
        recyclerViewArticles.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerViewArticles,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                articlePreviewPosition = position;
                                Article article = getPresenter().getArticle(position);
                                showArticleDetail(article);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
    }

    private String getSelectedSourceId() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(GlobalConstants.SELECTED_SOURCE_ID, "");
    }

    public void showArticleDetail(final Article article) {
        if (webViewArticlePreview != null) {
            webViewArticlePreview.getSettings().setJavaScriptEnabled(true);
            webViewArticlePreview.setWebViewClient(new ArticleWebViewClient());
            webViewArticlePreview.loadUrl(article.getUrl());
        } else {
            goToArticleDetailActivity(article);
        }
    }

    @Optional
    @OnClick(R.id.fabSaveArticlePreview)
    public void askUserToSaveArticle(View view) {
        if (arePermissionsEnabled()) {
            AlertDialog saveArticleDialog = createAlertDialog();
            saveArticleDialog.show();
        } else {
            showSnackbar(R.string.read_write_permissions_disabled);
        }
    }

    private AlertDialog createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.save_article_dialog_message)
                .setTitle(R.string.save_article_dialog_title);
        builder.setPositiveButton(R.string.save_article_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveArchive();
                showSnackbar(R.string.save_article_snackbar_ok_clicked);
            }
        });
        builder.setNegativeButton(R.string.save_article_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showSnackbar(R.string.save_article_snackbar_cancel_clicked);
            }
        });
        return builder.create();
    }

    public void saveArchive() {
        if (!Utils.isSdAvailable()) {
            showSnackbar(R.string.sd_card_not_available);
            return;
        }

        try {
            if (articlePreviewPosition == -1) {
                Log.i("saveArchive()", "Invalid position when saving article on tablet layout.");
                return;
            }
            Article article = getPresenter().getArticle(articlePreviewPosition);
            String archiveFilePath = getPresenter().getFileName(article.getUrl());
            Log.i("saveArchive()", "Saving archive file to: " + archiveFilePath);
            webViewArticlePreview.saveWebArchive(archiveFilePath);
            getPresenter().saveArticle(article, archiveFilePath, SQLiteHelper.getInstance(this));
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("saveArchive()", "Error while saving archive file.\n " + e);
        }

    }

    @Override
    public void showSnackbar(int resId) {
        Snackbar.make(findViewById(R.id.recyclerViewArticles), resId, Snackbar.LENGTH_LONG)
                .show();
    }

    private void goToSourcesActivity() {
        Intent intent = new Intent(this, SourcesActivity.class);
        intent.putExtra(GlobalConstants.ANIM_IN, R.anim.enter_from_right);
        intent.putExtra(GlobalConstants.ANIM_OUT, R.anim.exit_to_left);
        startActivity(intent);
    }

    private void goToArticleDetailActivity(Article article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra(GlobalConstants.SELECTED_ARTICLE, article);
        intent.putExtra(GlobalConstants.IS_OFFLINE_ARTICLE, false);
        startActivity(intent);
    }

    public void goToArchiveActivity() {
        Intent intent = new Intent(this, ArchiveActivity.class);
        intent.putExtra(GlobalConstants.ANIM_IN, R.anim.enter_from_right);
        intent.putExtra(GlobalConstants.ANIM_OUT, R.anim.exit_to_left);
        startActivity(intent);
    }

    private boolean arePermissionsEnabled() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    READ_WRITE_PERMISSIONS);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_WRITE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AlertDialog saveArticleDialog = createAlertDialog();
                    saveArticleDialog.show();
                } else {

                }
        }

    }
}
