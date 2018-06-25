// Generated code from Butter Knife. Do not modify!
package cz.hydradev.newsfeed.articledetail;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.webkit.WebView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import cz.hydradev.newsfeed.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArticleDetailActivity_ViewBinding implements Unbinder {
  private ArticleDetailActivity target;

  private View view2131558530;

  @UiThread
  public ArticleDetailActivity_ViewBinding(ArticleDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ArticleDetailActivity_ViewBinding(final ArticleDetailActivity target, View source) {
    this.target = target;

    View view;
    target.webView = Utils.findRequiredViewAsType(source, R.id.webViewArticle, "field 'webView'", WebView.class);
    view = Utils.findRequiredView(source, R.id.fabSaveArticle, "field 'fabSave' and method 'askUserToSaveArticle'");
    target.fabSave = Utils.castView(view, R.id.fabSaveArticle, "field 'fabSave'", FloatingActionButton.class);
    view2131558530 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.askUserToSaveArticle(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ArticleDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.webView = null;
    target.fabSave = null;

    view2131558530.setOnClickListener(null);
    view2131558530 = null;
  }
}
