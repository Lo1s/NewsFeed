package cz.hydradev.newsfeed.newsfeed;

import android.support.annotation.NonNull;
import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;
import java.util.List;

import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.constants.GlobalConstants;
import cz.hydradev.newsfeed.newsfeed.recyclerview.NewsFeedRowView;
import cz.hydradev.newsfeed.networking.RetrofitClient;
import cz.hydradev.newsfeed.networking.responsepojo.Article;
import cz.hydradev.newsfeed.networking.responsepojo.ResponseArticles;
import cz.hydradev.newsfeed.sqldelight.data.helpers.SQLiteHelper;
import cz.hydradev.newsfeed.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NewsFeedPresenter extends TiPresenter<NewsFeedView> {

    private RetrofitClient retrofitClient;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Article> articles = new ArrayList<>();

    public NewsFeedPresenter(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }

    @Override
    protected void onAttachView(@NonNull NewsFeedView view) {
        super.onAttachView(view);
    }

    public void saveArticle(Article article, String filename, SQLiteHelper sqliteHelper) {
        Log.i("Article Content", filename);
        sqliteHelper.insertArticle(article, filename);
    }

    public String getFileName(String url) {
        return Utils.getFileName(url);
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
        rxUnsubscribe();
    }

    public void onBindRecyclerViewArticleViewAtPosition(int position, NewsFeedRowView viewHolder) {
        Article article = articles.get(position);
        viewHolder.setUpArticleCardView(article);
    }

    public void getArticles(String sourceId, boolean cacheObservable, boolean useCache) {
        Observable<ResponseArticles> responseArticleObservable = (Observable<ResponseArticles>)
                retrofitClient.getPreparedObservable(
                        retrofitClient.getNewsApi().getArticles(GlobalConstants.API_KEY, sourceId, null),
                        ResponseArticles.class,
                        cacheObservable, useCache);

        final Disposable disposable = responseArticleObservable.subscribeWith(new DisposableObserver<ResponseArticles>() {
            @Override
            public void onNext(ResponseArticles value) {
                Log.i("onNext()", value.getArticles() + "");
                articles = value.getArticles();
                if (articles != null && articles.size() > 0) {
                    getView().initRecyclerView();
                } else {

                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("onError()", e.getMessage());
                if (getView() != null) {
                    getView().showSnackbar(R.string.articles_rx_failure);
                }
            }

            @Override
            public void onComplete() {
                Log.i("onComplete()", "getSources() Completed !");
            }
        });

        compositeDisposable.add(disposable);
    }

    public int getNewsArticlesCount() {
        return articles.size();
    }

    public Article getArticle(int position) {
        Log.i("Selected Article", position + "");
        Article article = articles.get(position);
        return article;
    }

    public void rxUnsubscribe() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

}
