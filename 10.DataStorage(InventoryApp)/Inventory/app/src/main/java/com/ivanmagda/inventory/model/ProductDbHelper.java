package com.ivanmagda.inventory.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ivanmagda.inventory.model.ProductContract.ProductEntry;

public class ProductDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ProductDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    public static final String DATABASE_NAME = "inventory.db";

    /**
     * Database version.
     */
    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String NOT_NULL_ATR = " NOT NULL";
    private static final String DEFAULT_ATR = " DEFAULT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_PRODUCTS_TABLE =
            "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                    ProductEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    ProductEntry.COLUMN_PRODUCT_NAME + TEXT_TYPE + NOT_NULL_ATR + COMMA_SEP +
                    ProductEntry.COLUMN_PRODUCT_PRICE + " REAL" + NOT_NULL_ATR + COMMA_SEP +
                    ProductEntry.COLUMN_PRODUCT_QUANTITY + INTEGER_TYPE + NOT_NULL_ATR + COMMA_SEP +
                    ProductEntry.COLUMN_PRODUCT_SUPPLIER + TEXT_TYPE + NOT_NULL_ATR + COMMA_SEP +
                    ProductEntry.COLUMN_PRODUCT_PICTURE + " BLOB" + NOT_NULL_ATR + COMMA_SEP +
                    ProductEntry.COLUMN_PRODUCT_SOLD_QUANTITY + INTEGER_TYPE + NOT_NULL_ATR + DEFAULT_ATR + " 0" +
                    ");";

    /**
     * Constructs a new instance of {@link SQLiteOpenHelper}.
     *
     * @param context of the app
     */
    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

}
