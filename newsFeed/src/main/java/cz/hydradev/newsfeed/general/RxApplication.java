package cz.hydradev.newsfeed.general;

import android.app.Application;

import com.facebook.stetho.Stetho;

import cz.hydradev.newsfeed.constants.GlobalConstants;
import cz.hydradev.newsfeed.networking.RetrofitClient;
import cz.hydradev.newsfeed.sqldelight.data.helpers.SQLiteHelper;

public class RxApplication extends Application {

    private RetrofitClient newsRetrofitClient;

    @Override
    public void onCreate() {
        super.onCreate();
        newsRetrofitClient = new RetrofitClient();
        //Stetho.initializeWithDefaults(this);
    }

    public RetrofitClient getNewsRetrofitClient() {
        return this.newsRetrofitClient;
    }

}
