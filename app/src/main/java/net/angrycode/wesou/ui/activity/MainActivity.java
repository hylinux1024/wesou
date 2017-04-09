package net.angrycode.wesou.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import net.angrycode.wesou.R;
import net.angrycode.wesou.bean.TorrentProject;
import net.angrycode.wesou.component.LoadMoreWrapper;
import net.angrycode.wesou.event.ThemeChangeEvent;
import net.angrycode.wesou.mvp.SearchContract;
import net.angrycode.wesou.mvp.SearchPresenter;
import net.angrycode.wesou.ui.adapter.TorrentAdapter;
import net.angrycode.wesou.utils.AppUtils;
import net.angrycode.wesou.utils.IntentUtils;
import net.angrycode.wesou.utils.ToastUtils;
import net.angrycode.wesou.utils.Utils;
import net.angrycode.wesou.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SearchContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    TorrentAdapter mAdapter;
    LoadMoreWrapper<TorrentProject> mLoadMoreWrapper;

    SearchPresenter mPresenter;

    int mStart = 0;
    String mKeywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mPresenter = new SearchPresenter(this);
    }

    private void initView() {
        ViewUtils.setDefaultRecyclerView(this, mRecyclerView);
        mAdapter = new TorrentAdapter();
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        mLoadMoreWrapper.setOnLoadMoreListener(() -> {
            mStart++;
            loadMore(mStart);
        });
        mLoadMoreWrapper.setOnItemClickListener((itemView, position) -> {
            onItemClick(position);
        });
        mRecyclerView.setAdapter(mLoadMoreWrapper);

        mRefreshLayout.setOnRefreshListener(() -> {
            mStart = 0;
            if (TextUtils.isEmpty(mKeywords)) {
                mRefreshLayout.setRefreshing(false);
            } else {
                mPresenter.search(mKeywords, mStart);
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                mStart = 0;
                mKeywords = currentQuery;
                mPresenter.search(currentQuery, mStart);
                showLoading();
            }
        });
    }

    private void loadMore(int start) {
        mLoadMoreWrapper.setLoading();
        mPresenter.search(mKeywords, start);
    }

    private void onItemClick(int position) {

        TorrentProject torrentProject = mLoadMoreWrapper.getItem(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.more);
        builder.setItems(R.array.item_search, (dialog, which) -> {
            switch (which) {
                case 0://下载
                    AppUtils.openMagnetDownload(this, torrentProject.getMagnetLink());
                    break;
                case 1://复制
                    Utils.copyText(this, torrentProject.getMagnetLink());
                    break;
                case 2://share
                    AppUtils.shareText(this, torrentProject.getMagnetLink());
                    break;
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_theme:
                IntentUtils.launch(this, ThemeActivity.class);
                break;
            case R.id.nav_about:
                IntentUtils.launch(this, AboutActivity.class);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected boolean isShowHomeAsUpIndicator() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onStartLoading() {
        showLoading();
    }

    @Override
    public void onFinished() {
        mLoadMoreWrapper.setLoadFinished();
        mRefreshLayout.setRefreshing(false);
        dismissLoading();
    }

    @Override
    public void onError(int code, String message) {
        ToastUtils.show(this, message);
        mLoadMoreWrapper.setLoadFinished();
        mRefreshLayout.setRefreshing(false);
        dismissLoading();
    }

    @Override
    public void onGetTorrentProjectListFinish(List<TorrentProject> projectList) {
        if (mStart == 0) {
            mLoadMoreWrapper.clear();
        }
        mLoadMoreWrapper.addData(projectList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThemeChangedEvent(ThemeChangeEvent event) {
        recreate();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        mDrawerLayout.removeDrawerListener(mToggle);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }
}
