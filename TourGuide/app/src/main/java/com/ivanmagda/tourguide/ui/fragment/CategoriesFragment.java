package com.ivanmagda.tourguide.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ivanmagda.tourguide.R;
import com.ivanmagda.tourguide.model.adapter.CategoryAdapter;
import com.ivanmagda.tourguide.model.place.Category;
import com.ivanmagda.tourguide.ui.activity.PlacesListActivity;


public class CategoriesFragment extends Fragment {

    public final static String EXTRA_CATEGORY = "com.ivanmagda.tourguide.ui.fragment.CATEGORY";
    private static final String LOG_TAG = CategoriesFragment.class.getSimpleName();

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView listView = (ListView) inflater.inflate(R.layout.list_view, container, false);

        final Category[] categories = Category.allCategories(getActivity());

        listView.setAdapter(new CategoryAdapter(getActivity(), categories));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showCategory(categories[position]);
            }
        });

        return listView;
    }

    private void showCategory(Category category) {
        Intent intent = new Intent(getActivity(), PlacesListActivity.class);
        intent.putExtra(EXTRA_CATEGORY, category);
        getActivity().startActivity(intent);
    }

}
