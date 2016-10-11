package com.ivanmagda.booklisting.ui;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ivanmagda.booklisting.R;
import com.ivanmagda.booklisting.api.BookLoader;
import com.ivanmagda.booklisting.model.Book;
import com.ivanmagda.booklisting.model.BookAdapter;
import com.ivanmagda.booklisting.util.ConnectivityUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int BOOK_LOADER_ID = 1;

    BookAdapter mAdapter;
    private TextView mEmptyTextView;
    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);

        mAdapter = new BookAdapter(this);
        listView.setAdapter(mAdapter);

        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyTextView);

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            mQuery = searchIntent.getStringExtra(SearchManager.QUERY);
        }

        if (mQuery == null) {
            hideProgressBar();
            mEmptyTextView.setText(R.string.search_for_books_message);
        } else {
            if (ConnectivityUtils.isOnline(this)) {
                getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
            } else {
                hideProgressBar();
                mEmptyTextView.setText(R.string.no_internet_connection);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, buildRequestUrl());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        mEmptyTextView.setText(R.string.list_view_empty_message);
        hideProgressBar();
        mAdapter.updateData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        mAdapter.updateData(null);
    }

    private void hideProgressBar() {
        findViewById(R.id.loading_spinner).setVisibility(View.GONE);
    }

    private String buildRequestUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.googleapis.com")
                .appendPath("books")
                .appendPath("v1")
                .appendPath("volumes")
                .appendQueryParameter("maxResults", "40")
                .appendQueryParameter("q", mQuery);

        return builder.build().toString();
    }

}
