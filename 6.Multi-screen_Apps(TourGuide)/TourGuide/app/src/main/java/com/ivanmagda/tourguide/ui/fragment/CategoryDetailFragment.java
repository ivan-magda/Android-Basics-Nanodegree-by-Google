package com.ivanmagda.tourguide.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ivanmagda.tourguide.R;
import com.ivanmagda.tourguide.model.place.Category;
import com.ivanmagda.tourguide.model.place.Place;
import com.ivanmagda.tourguide.ui.activity.PlaceDetailActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDetailFragment extends Fragment {

    public final static String EXTRA_PLACE = "com.ivanmagda.tourguide.ui.fragment.PLACE";

    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView listView = (ListView) inflater.inflate(R.layout.list_view, container, false);

        Intent intent = getActivity().getIntent();
        final Category category = (Category) intent.getSerializableExtra(CategoriesFragment.EXTRA_CATEGORY);

        getActivity().setTitle(category.getName());

        ArrayList<String> listItems = new ArrayList<>(category.getPlaces().length);
        for (Place place : category.getPlaces()) {
            listItems.add(place.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place selectedPlace = category.getPlaces()[position];
                showPlaceDetails(selectedPlace);
            }
        });

        return listView;
    }

    private void showPlaceDetails(Place place) {
        Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
        intent.putExtra(EXTRA_PLACE, place);
        getActivity().startActivity(intent);
    }

}
