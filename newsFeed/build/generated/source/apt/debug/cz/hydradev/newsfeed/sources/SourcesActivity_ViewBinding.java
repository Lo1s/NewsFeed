// Generated code from Butter Knife. Do not modify!
package cz.hydradev.newsfeed.sources;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import cz.hydradev.newsfeed.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SourcesActivity_ViewBinding implements Unbinder {
  private SourcesActivity target;

  @UiThread
  public SourcesActivity_ViewBinding(SourcesActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SourcesActivity_ViewBinding(SourcesActivity target, View source) {
    this.target = target;

    target.recyclerViewSources = Utils.findRequiredViewAsType(source, R.id.recyclerViewSources, "field 'recyclerViewSources'", RecyclerView.class);
    target.bottomNavigationView = Utils.findRequiredViewAsType(source, R.id.bottom_navigation, "field 'bottomNavigationView'", BottomNavigationView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SourcesActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerViewSources = null;
    target.bottomNavigationView = null;
  }
}
