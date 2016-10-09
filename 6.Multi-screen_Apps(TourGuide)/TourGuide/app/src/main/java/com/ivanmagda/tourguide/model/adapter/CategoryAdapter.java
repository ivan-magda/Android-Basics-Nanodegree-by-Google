package com.ivanmagda.tourguide.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivanmagda.tourguide.R;
import com.ivanmagda.tourguide.model.place.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(Context context, Category[] categories) {
        super(context, 0, categories);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.category_list_item, parent, false);
        }

        Category category = getItem(position);
        assert category != null;

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.category_image_view);
        imageView.setImageResource(category.getImageResourceId());

        TextView textView = (TextView) listItemView.findViewById(R.id.category_text_view);
        textView.setText(category.getName());

        return listItemView;
    }

}
