package com.example.android.quakereport.model;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.R;
import com.example.android.quakereport.util.DateFormatUtils;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    private static class ViewHolder {
        TextView magnitudeTextView;
        TextView primaryLocationTextView;
        TextView locationOffsetTextView;
        TextView dateTextView;
        TextView timeTextView;
    }

    public EarthquakeAdapter(Context context, List<Earthquake> objects) {
        super(context, 0, objects);
    }

    public EarthquakeAdapter(Context context) {
        super(context, 0, new ArrayList<Earthquake>());
    }

    public void updateData(ArrayList<Earthquake> earthquakes) {
        clear();
        if (earthquakes != null && !earthquakes.isEmpty()) addAll(earthquakes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View listView = convertView;

        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,
                    parent, false);

            viewHolder = new ViewHolder();
            viewHolder.magnitudeTextView = (TextView) listView.findViewById(R.id.magnitude_text_view);
            viewHolder.primaryLocationTextView = (TextView) listView.findViewById(R.id.primary_location_text_view);
            viewHolder.locationOffsetTextView = (TextView) listView.findViewById(R.id.location_offset_text_view);
            viewHolder.dateTextView = (TextView) listView.findViewById(R.id.date_text_view);
            viewHolder.timeTextView = (TextView) listView.findViewById(R.id.time_text_view);

            listView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listView.getTag();
        }

        Earthquake earthquake = getItem(position);
        assert earthquake != null;

        TextView magnitudeTextView = viewHolder.magnitudeTextView;
        magnitudeTextView.setText(String.format("%.1f", earthquake.getMagnitude()));

        TextView primaryLocationTextView = viewHolder.primaryLocationTextView;
        primaryLocationTextView.setText(earthquake.getPrimaryLocation());

        TextView locationOffsetTextView = viewHolder.locationOffsetTextView;
        locationOffsetTextView.setText(earthquake.getLocationOffset());

        TextView dateTextView = viewHolder.dateTextView;
        dateTextView.setText(DateFormatUtils.shortStringFromEarthquake(earthquake));

        TextView timeTextView = viewHolder.timeTextView;
        timeTextView.setText(DateFormatUtils.timeStringFromEarthquake(earthquake));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}
