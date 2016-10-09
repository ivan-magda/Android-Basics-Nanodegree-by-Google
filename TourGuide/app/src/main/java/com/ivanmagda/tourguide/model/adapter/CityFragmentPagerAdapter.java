package com.ivanmagda.tourguide.model.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ivanmagda.tourguide.R;
import com.ivanmagda.tourguide.ui.fragment.OverviewFragment;
import com.ivanmagda.tourguide.ui.fragment.CategoriesFragment;

public class CityFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGES_COUNT = 2;
    private static final int PLACES_PAGE = 0;
    private static final int OVERVIEW_PAGE = 1;

    private Context mContext;

    public CityFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case OVERVIEW_PAGE:
                return new OverviewFragment();
            case PLACES_PAGE:
                return new CategoriesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case OVERVIEW_PAGE:
                return mContext.getString(R.string.overview_page_title);
            case PLACES_PAGE:
                return mContext.getString(R.string.places_page_title);
            default:
                return null;
        }
    }

}
