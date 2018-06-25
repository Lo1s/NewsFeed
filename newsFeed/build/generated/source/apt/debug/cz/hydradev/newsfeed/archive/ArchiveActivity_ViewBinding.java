// Generated code from Butter Knife. Do not modify!
package cz.hydradev.newsfeed.archive;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import cz.hydradev.newsfeed.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArchiveActivity_ViewBinding implements Unbinder {
  private ArchiveActivity target;

  @UiThread
  public ArchiveActivity_ViewBinding(ArchiveActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ArchiveActivity_ViewBinding(ArchiveActivity target, View source) {
    this.target = target;

    target.bottomNavigationView = Utils.findRequiredViewAsType(source, R.id.bottom_navigation, "field 'bottomNavigationView'", BottomNavigationView.class);
    target.recyclerViewArticles = Utils.findRequiredViewAsType(source, R.id.recyclerViewArticles, "field 'recyclerViewArticles'", RecyclerView.class);
    target.textViewEmpty = Utils.findRequiredViewAsType(source, R.id.textViewEmpty, "field 'textViewEmpty'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ArchiveActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.bottomNavigationView = null;
    target.recyclerViewArticles = null;
    target.textViewEmpty = null;
  }
}
