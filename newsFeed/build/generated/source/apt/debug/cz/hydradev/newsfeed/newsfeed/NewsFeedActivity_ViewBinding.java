// Generated code from Butter Knife. Do not modify!
package cz.hydradev.newsfeed.newsfeed;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import cz.hydradev.newsfeed.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsFeedActivity_ViewBinding implements Unbinder {
  private NewsFeedActivity target;

  private View view2131558532;

  @UiThread
  public NewsFeedActivity_ViewBinding(NewsFeedActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewsFeedActivity_ViewBinding(final NewsFeedActivity target, View source) {
    this.target = target;

    View view;
    target.recyclerViewArticles = Utils.findRequiredViewAsType(source, R.id.recyclerViewArticles, "field 'recyclerViewArticles'", RecyclerView.class);
    target.bottomNavigationView = Utils.findRequiredViewAsType(source, R.id.bottom_navigation, "field 'bottomNavigationView'", BottomNavigationView.class);
    target.webViewArticlePreview = Utils.findOptionalViewAsType(source, R.id.webViewArticlePreview, "field 'webViewArticlePreview'", WebView.class);
    view = source.findViewById(R.id.fabSaveArticlePreview);
    target.fabSaveArticlePreview = Utils.castView(view, R.id.fabSaveArticlePreview, "field 'fabSaveArticlePreview'", FloatingActionButton.class);
    if (view != null) {
      view2131558532 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.askUserToSaveArticle(p0);
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsFeedActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerViewArticles = null;
    target.bottomNavigationView = null;
    target.webViewArticlePreview = null;
    target.fabSaveArticlePreview = null;

    if (view2131558532 != null) {
      view2131558532.setOnClickListener(null);
      view2131558532 = null;
    }
  }
}
