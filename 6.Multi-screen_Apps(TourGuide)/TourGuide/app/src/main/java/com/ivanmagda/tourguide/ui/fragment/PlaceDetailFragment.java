package com.ivanmagda.tourguide.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivanmagda.tourguide.R;
import com.ivanmagda.tourguide.model.place.Place;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceDetailFragment extends Fragment {

    public PlaceDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_place_detail, container, false);

        Intent intent = getActivity().getIntent();
        Place place = (Place) intent.getSerializableExtra(CategoryDetailFragment.EXTRA_PLACE);

        getActivity().setTitle(place.getName());

        ImageView imageView = (ImageView) rootView.findViewById(R.id.place_image_view);
        imageView.setImageResource(place.getImageResourceId());

        TextView textView = (TextView) rootView.findViewById(R.id.place_description_text_view);
        textView.setText(place.getDescription());

        return rootView;
    }

}
