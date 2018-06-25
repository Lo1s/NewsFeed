package cz.hydradev.newsfeed.articledetail;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import net.grandcentrix.thirtyinch.TiActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.hydradev.newsfeed.ArticleWebViewClient;
import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.constants.GlobalConstants;
import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.sqldelight.data.helpers.SQLiteHelper;
import cz.hydradev.newsfeed.utils.Utils;

import static cz.hydradev.newsfeed.constants.GlobalConstants.READ_WRITE_PERMISSIONS;

public class ArticleDetailActivity extends TiActivity<ArticleDetailPresenter, ArticleDetailView>
    implements ArticleDetailView {

    @BindView(R.id.webViewArticle) WebView webView;
    @BindView(R.id.fabSaveArticle) FloatingActionButton fabSave;

    private Article article;
    private boolean isOfflineArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new ArticleWebViewClient());
        article = getIntent().getParcelableExtra(GlobalConstants.SELECTED_ARTICLE);
        isOfflineArticle = getIntent().getBooleanExtra(GlobalConstants.IS_OFFLINE_ARTICLE, false);

        if (article != null) {
            setUpWebView(article.getUrl());
        } else {
            Log.i("onCreate()", "Article URL not provided !");
        }

        if (isOfflineArticle) {
            fabSave.setVisibility(View.GONE);
        }
    }

    private void setUpWebView(String articleUrl) {
        Log.i("setUpWebView", "Loading article: " + articleUrl);
        if (isOfflineArticle) {
            loadArchive();
        } else {
            webView.loadUrl(articleUrl);
        }
    }

    @OnClick(R.id.fabSaveArticle)
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

    public void saveArchive(){
        if (!Utils.isSdAvailable()) {
            showSnackbar(R.string.sd_card_not_available);
            return;
        }

        try {
            String archiveFilePath = getPresenter().getFileName(article.getUrl());
            Log.i("saveArchive()", "Saving archive file to: " + archiveFilePath);
            webView.saveWebArchive(archiveFilePath);
            getPresenter().saveArticle(article, archiveFilePath);
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("saveArchive()", "Error while saving archive file.\n " + e);
        }

    }

    public void loadArchive() {
        String archiveFilePath = getPresenter().getFileName(article.getUrl());
        Uri archiveFileUri = Uri.fromFile(new File(archiveFilePath));
        Log.i("loadArchive()", "Archive File Uri: " + archiveFileUri);
        webView.loadUrl(archiveFileUri + "");
    }

    private void showSnackbar(int resId) {
        Snackbar.make(findViewById(R.id.coordinatorLayoutArticleDetail), resId, Snackbar.LENGTH_LONG)
                .show();
    }

    @NonNull
    @Override
    public ArticleDetailPresenter providePresenter() {
        return new ArticleDetailPresenter();
    }

    @Override
    public SQLiteHelper getSQLHelper() {
        return SQLiteHelper.getInstance(this);
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
