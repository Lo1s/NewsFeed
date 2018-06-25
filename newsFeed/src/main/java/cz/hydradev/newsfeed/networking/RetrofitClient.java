package cz.hydradev.newsfeed.networking;

import android.util.LruCache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.hydradev.newsfeed.constants.GlobalConstants;
import cz.hydradev.newsfeed.networking.responsepojo.ResponseArticles;
import cz.hydradev.newsfeed.networking.responsepojo.ResponseSources;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RetrofitClient {

    private final String BASE_URL = "https://newsapi.org/v1/";
    private Retrofit retrofit;
    private Gson gson;
    private NewsApiEndPointInterface newsApi;
    private LruCache<Class<?>, Observable<?>> apiObservables;

    public RetrofitClient() {
        initNewsApi(BASE_URL);
    }

    public RetrofitClient(String baseUrl) {
        initNewsApi(baseUrl);
    }

    private void initNewsApi(String url) {
        apiObservables = new LruCache<>(10);

        initClient(url);
        newsApi = retrofit.create(NewsApiEndPointInterface.class);
    }

    private void initClient(String url) {
        apiObservables = new LruCache<>(10);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(logging);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build();
    }


    public Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz,
                                               boolean cacheObservable, boolean useCache) {

        Observable<?> preparedObservable = null;

        if (useCache) {
            preparedObservable = apiObservables.get(clazz);
        }

        if (preparedObservable != null) {
            return preparedObservable;
        }

        preparedObservable = unPreparedObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        if (cacheObservable) {
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }

        return preparedObservable;
    }

    public NewsApiEndPointInterface getNewsApi() {
        return newsApi;
    }

    public interface NewsApiEndPointInterface {

        @GET("sources")
        Observable<ResponseSources> getSources(@Query("category") String category,
                                               @Query("language") String language,
                                               @Query("country") String country);

        @GET("articles")
        Observable<ResponseArticles> getArticles(@Query("apiKey") String apiKey,
                                           @Query("source") String source,
                                           @Query("sortBy") String sortBy);
    }
}
