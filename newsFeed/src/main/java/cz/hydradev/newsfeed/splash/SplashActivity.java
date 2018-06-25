package cz.hydradev.newsfeed.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.newsfeed.NewsFeedActivity;

public class SplashActivity extends AppCompatActivity {

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            goToNewsFeedActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(runnable, 1500);
    }

    private void goToNewsFeedActivity() {
        Intent intent = new Intent(this, NewsFeedActivity.class);
        startActivity(intent);
    }
}
