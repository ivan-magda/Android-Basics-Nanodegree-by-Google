package com.ivanmagda.inventory.model;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ProductContract {

    // Prevent someone from accidentally instantiating the contract class.
    private ProductContract() {
    }

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website. A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.ivanmagda.inventory";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.ivanmagda.inventory/products/ is a valid path for
     * looking at inventory data. content://com.ivanmagda.inventory/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_PRODUCTS = "products";

    /**
     * Inner class that defines constant values for the products database table.
     * Each entry in the table represents a single product.
     */
    public static abstract class ProductEntry implements BaseColumns {

        /**
         * The content URI to access the inventory data in the provider.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        /**
         * Name of database table for products.
         */
        public final static String TABLE_NAME = "products";

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        /**
         * Name of the product.
         * <p/>
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME = "name";

        /**
         * Price of the product.
         * <p/>
         * Type: REAL
         */
        public final static String COLUMN_PRODUCT_PRICE = "price";

        /**
         * Quantity of the product.
         * <p/>
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";

        /**
         * Supplier email of the product.
         * <p/>
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_SUPPLIER = "supplier";

        /**
         * Picture of the product.
         * <p/>
         * Type: BLOB
         */
        public final static String COLUMN_PRODUCT_PICTURE = "picture";

        /**
         * Sold quantity of the product.
         * <p/>
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_SOLD_QUANTITY = "sold_quantity";
    }

}
