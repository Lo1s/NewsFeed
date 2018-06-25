package cz.hydradev.newsfeed;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ji066375 on 03.10.2017.
 */

public class ArticleWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (view.getId() == R.id.webViewArticle || view.getId() == R.id.webViewArticle) {
            // This is my web site, so do not override; let my WebView load the page
            return false;
        } else {
            return true;
        }
    }
}
