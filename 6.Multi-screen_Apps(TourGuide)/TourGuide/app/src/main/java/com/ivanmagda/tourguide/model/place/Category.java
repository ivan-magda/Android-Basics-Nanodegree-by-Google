package com.ivanmagda.tourguide.model.place;

import android.content.Context;

import com.ivanmagda.tourguide.R;

import java.io.Serializable;

public class Category implements Serializable {

    private String name;
    private Place[] mPlaces;
    private int mImageResourceId;

    public Category(String name, Place[] places, int imageResourceId) {
        this.name = name;
        this.mPlaces = places;
        this.mImageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Place[] getPlaces() {
        return mPlaces;
    }

    public void setPlaces(Place[] places) {
        this.mPlaces = places;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public void setImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name +
                '}';
    }

    public static Category[] allCategories(Context context) {
        return new Category[]{
                new Category(context.getString(R.string.food_category_name),
                        Place.foodPlaces(context), R.drawable.food),
                new Category(context.getString(R.string.coffee_category_name),
                        Place.coffeePlaces(context), R.drawable.coffee),
                new Category(context.getString(R.string.nightlife_category_name),
                        Place.nightlifePlaces(context), R.drawable.nightlife),
                new Category(context.getString(R.string.fun_category_name),
                        Place.funPlaces(context), R.drawable.fun),
                new Category(context.getString(R.string.shopping_category_name),
                        Place.shoppingPlaces(context), R.drawable.shopping),
                new Category(context.getString(R.string.breakfast_category_name),
                        Place.breakfastPlaces(context), R.drawable.pizza)
        };
    }

}
