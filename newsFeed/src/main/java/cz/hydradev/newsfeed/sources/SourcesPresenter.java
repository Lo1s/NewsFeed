package cz.hydradev.newsfeed.sources;

import android.support.annotation.NonNull;
import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;
import java.util.List;

import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.networking.RetrofitClient;
import cz.hydradev.newsfeed.networking.responsepojo.ResponseSources;
import cz.hydradev.newsfeed.networking.responsepojo.Source;
import cz.hydradev.newsfeed.sources.recyclerview.SourcesRowView;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SourcesPresenter extends TiPresenter<SourcesView> {

    private RetrofitClient newsRetrofitClient;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Source> sources = new ArrayList<>();

    public SourcesPresenter(RetrofitClient newsRetrofitClient) {
        this.newsRetrofitClient = newsRetrofitClient;
    }

    @Override
    protected void onAttachView(@NonNull SourcesView view) {
        super.onAttachView(view);
    }



    @Override
    protected void onDetachView() {
        super.onDetachView();
        rxUnsubscribe();
    }

    public void getSources(String category, String language, String country, boolean cacheObservable , boolean useCache) {
        Observable<ResponseSources> responseSourcesObservable = (Observable<ResponseSources>)
                newsRetrofitClient.getPreparedObservable(
                        newsRetrofitClient.getNewsApi().getSources(category, language, country),
                        ResponseSources.class,
                        cacheObservable, useCache);

        Disposable disposable = responseSourcesObservable.subscribeWith(new DisposableObserver<ResponseSources>() {
            @Override
            public void onNext(ResponseSources value) {
                sources = value.getSources();
                getView().initRecyclerView();
            }

            @Override
            public void onError(Throwable e) {
                Log.i("onError()", e.getMessage());
                if (getView() != null) {
                    getView().showSnackbar(R.string.sources_rx_failure);
                }
            }

            @Override
            public void onComplete() {
                Log.i("onComplete()", "getSources() Completed !");
            }
        });

        compositeDisposable.add(disposable);
    }

    public void rxUnsubscribe() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    public void onBindRecyclerViewArticleViewAtPosition(int position, SourcesRowView viewHolder) {
        Source source = sources.get(position);
        viewHolder.setUpSourceCardView(source);
    }

    public int getSourcesCount() {
        return sources.size();
    }

    public void selectSource(int position) {
        if (sources != null && sources.size() > position) {
            Source source = sources.get(position);
            getView().saveSelectedSourceId(source.getId());
            getView().goToNewsFeedActivity();
        }
    }
}
