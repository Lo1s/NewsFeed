package cz.hydradev.newsfeed.sources;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.hydradev.newsfeed.R;
import cz.hydradev.newsfeed.archive.ArchiveActivity;
import cz.hydradev.newsfeed.constants.GlobalConstants;
import cz.hydradev.newsfeed.general.RxApplication;
import cz.hydradev.newsfeed.newsfeed.NewsFeedActivity;
import cz.hydradev.newsfeed.utils.RecyclerItemClickListener;
import cz.hydradev.newsfeed.sources.recyclerview.SourcesRecyclerAdapter;

public class SourcesActivity extends TiActivity<SourcesPresenter, SourcesView> implements SourcesView {

    @BindView(R.id.recyclerViewSources) RecyclerView recyclerViewSources;
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private boolean useCachedObservable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);
        ButterKnife.bind(this);
        initBottomNavigationView();
        setUpTransitionAnimation();
        if (savedInstanceState != null) {
            useCachedObservable = savedInstanceState.getBoolean(
                    GlobalConstants.SAVED_INSTANCE_RX_IN_WORKS, false);
        } else {
            useCachedObservable = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getSources(null, null, null, !useCachedObservable, useCachedObservable);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(GlobalConstants.SAVED_INSTANCE_RX_IN_WORKS, useCachedObservable);
    }

    private void setUpTransitionAnimation() {
        int animIn = getIntent().getIntExtra(GlobalConstants.ANIM_IN, R.anim.enter_from_right);
        int animOut = getIntent().getIntExtra(GlobalConstants.ANIM_OUT, R.anim.exit_to_left);
        overridePendingTransition(animIn, animOut);
    }

    @NonNull
    @Override
    public SourcesPresenter providePresenter() {
        return new SourcesPresenter(
                ((RxApplication) getApplication()).getNewsRetrofitClient());
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_news:
                                goToNewsFeedActivity();
                                break;
                            case R.id.action_sources:

                                break;

                            case R.id.action_archive:
                                goToArchiveActivity();

                                break;
                        }
                        return true;
                    }
                });
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }

    @Override
    public void initRecyclerView() {
        adapter = new SourcesRecyclerAdapter(getPresenter());
        layoutManager = new LinearLayoutManager(this);
        recyclerViewSources.setLayoutManager(layoutManager);
        recyclerViewSources.setAdapter(adapter);
        recyclerViewSources.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerViewSources,
                        new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getPresenter().selectSource(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void saveSelectedSourceId(String sourceId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GlobalConstants.SELECTED_SOURCE_ID, sourceId);
        editor.commit();
    }

    @Override
    public void goToNewsFeedActivity() {
        Intent intent = new Intent(this, NewsFeedActivity.class);
        intent.putExtra(GlobalConstants.ANIM_IN, R.anim.enter_from_left);
        intent.putExtra(GlobalConstants.ANIM_OUT, R.anim.exit_to_right);
        startActivity(intent);
    }

    public void goToArchiveActivity() {
        Intent intent = new Intent(this, ArchiveActivity.class);
        intent.putExtra(GlobalConstants.ANIM_IN, R.anim.enter_from_right);
        intent.putExtra(GlobalConstants.ANIM_OUT, R.anim.exit_to_left);
        startActivity(intent);
    }

    @Override
    public void showSnackbar(int resId) {
        Snackbar.make(recyclerViewSources, resId, Snackbar.LENGTH_LONG)
                .show();
    }

}
