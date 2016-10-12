package com.ivanmagda.news.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ivanmagda.news.R;
import com.ivanmagda.news.api.guardian.GuardianLoader;
import com.ivanmagda.news.model.adapter.NewsItemAdapter;
import com.ivanmagda.news.model.object.NewsItem;
import com.ivanmagda.news.util.ConnectivityUtils;

import java.util.ArrayList;

public class NewsFeed extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>> {

    private static final String LOG_TAG = NewsFeed.class.getSimpleName();
    private static final int GUARDIAN_LOADER_ID = 1;

    private NewsItemAdapter mNewsItemAdapter;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        configure();
        fetchData();
    }

    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int id, Bundle args) {
        return new GuardianLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsItem>> loader, ArrayList<NewsItem> data) {
        mEmptyTextView.setText(R.string.list_view_empty_message);
        hideProgressBar();

        mNewsItemAdapter.updateData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {
        mNewsItemAdapter.updateData(null);
    }

    private void configure() {
        setTitle(R.string.news_feed_activity_title);

        mNewsItemAdapter = new NewsItemAdapter(this);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mNewsItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNewsItemInTheBrowser(mNewsItemAdapter.getItem(position));
            }
        });

        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyTextView);

        if (!ConnectivityUtils.isOnline(this)) {
            hideProgressBar();
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
    }

    private void fetchData() {
        if (ConnectivityUtils.isOnline(this)) {
            getLoaderManager().initLoader(GUARDIAN_LOADER_ID, null, this);
        }
    }

    private void showNewsItemInTheBrowser(NewsItem newsItem) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(newsItem.getUrl()));
        startActivity(intent);
    }

    private void hideProgressBar() {
        findViewById(R.id.loading_spinner).setVisibility(View.GONE);
    }

}
