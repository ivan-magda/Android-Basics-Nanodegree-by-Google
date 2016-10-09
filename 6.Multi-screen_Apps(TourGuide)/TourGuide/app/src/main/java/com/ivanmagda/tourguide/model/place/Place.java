package com.ivanmagda.tourguide.model.place;

import android.content.Context;

import com.ivanmagda.tourguide.R;

import java.io.Serializable;

public class Place implements Serializable {

    private String mName;
    private String mDescription;
    private int mImageResourceId;

    public Place(String name, String description, int imageResourceId) {
        this.mName = name;
        this.mDescription = description;
        this.mImageResourceId = imageResourceId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.mImageResourceId = imageResourceId;
    }

    public static Place[] foodPlaces(Context context) {
        return new Place[]{
                new Place(context.getString(R.string.beletazh_name), context.getString(R.string.beletazh_description),
                        R.drawable.beletazh),
                new Place(context.getString(R.string.yaposha_name), context.getString(R.string.yaposha_description),
                        R.drawable.yaposha),
                new Place(context.getString(R.string.ani_name), context.getString(R.string.ani_description),
                        R.drawable.ani),
                new Place(context.getString(R.string.boulangerie_name), context.getString(R.string.boulangerie_description),
                        R.drawable.boulangerie)
        };
    }

    public static Place[] coffeePlaces(Context context) {
        return new Place[]{
                new Place(context.getString(R.string.coffee_shop_on_the_big_name),
                        context.getString(R.string.coffee_shop_on_the_big_description),
                        R.drawable.coffee_shop_on_the_big),
                new Place(context.getString(R.string.chikofsky_name),
                        context.getString(R.string.chikofsky_description),
                        R.drawable.chikofsky),
                new Place(context.getString(R.string.bocado_name),
                        context.getString(R.string.bocado_description),
                        R.drawable.bocado)
        };
    }

    public static Place[] nightlifePlaces(Context context) {
        return new Place[]{
                new Place(context.getString(R.string.c2_name),
                        context.getString(R.string.c2_description),
                        R.drawable.c2),
                new Place(context.getString(R.string.schweik_name),
                        context.getString(R.string.schweik_description),
                        R.drawable.schweik),
                new Place(context.getString(R.string.billini_name),
                        context.getString(R.string.billini_description),
                        R.drawable.billini)
        };
    }

    public static Place[] funPlaces(Context context) {
        return new Place[]{
                new Place(context.getString(R.string.stadium_amur_name),
                        context.getString(R.string.stadium_amur_description),
                        R.drawable.stadium_amur),
                new Place(context.getString(R.string.cinema_blg_name),
                        context.getString(R.string.cinema_blg_description),
                        R.drawable.cinema_blg),
                new Place(context.getString(R.string.museum_name),
                        context.getString(R.string.museum_description),
                        R.drawable.museum),
                new Place(context.getString(R.string.pervomayskiy_park_name),
                        context.getString(R.string.pervomayskiy_park_description),
                        R.drawable.p_park),
                new Place(context.getString(R.string.theatre_name),
                        context.getString(R.string.theatre_description),
                        R.drawable.theather)
        };
    }

    public static Place[] shoppingPlaces(Context context) {
        return new Place[]{
                new Place(context.getString(R.string.benetton_name),
                        context.getString(R.string.benetton_description),
                        R.drawable.benetton),
                new Place("Shopping Mall Ostrova",
                        "Decent Shopping Mall, great shopping. The presence of a food court and entertainment happy.",
                        R.drawable.ostrova)
        };
    }

    public static Place[] breakfastPlaces(Context context) {
        return new Place[]{
                new Place(context.getString(R.string.chocolate_name),
                        context.getString(R.string.chocolate_description),
                        R.drawable.chocolate),
                new Place(context.getString(R.string.free_time_name),
                        context.getString(R.string.free_time_description),
                        R.drawable.free_time)
        };
    }

}
