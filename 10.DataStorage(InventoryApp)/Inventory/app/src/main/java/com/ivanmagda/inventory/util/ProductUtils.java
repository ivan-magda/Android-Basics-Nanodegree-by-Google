/**
 * Copyright (c) 2016 Ivan Magda
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ivanmagda.inventory.util;

import android.database.Cursor;

import com.ivanmagda.inventory.model.data.ProductContract;
import com.ivanmagda.inventory.model.data.ProductContract.ProductEntry;
import com.ivanmagda.inventory.model.object.Product;

public class ProductUtils {

    private static final int COLUMN_NOT_EXIST = -1;

    private ProductUtils() {
    }

    public static Product productFromCursor(Cursor cursor) {
        int id = -1;
        if (isColumnExist(cursor, ProductEntry._ID))
            id = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry._ID));

        String name = null;
        if (isColumnExist(cursor, ProductEntry.COLUMN_PRODUCT_NAME))
            name = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME));

        double price = -1;
        if (isColumnExist(cursor, ProductEntry.COLUMN_PRODUCT_PRICE))
            price = cursor.getDouble(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE));

        int quantity = -1;
        if (isColumnExist(cursor, ProductEntry.COLUMN_PRODUCT_QUANTITY))
            quantity = cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY));

        int soldQuantity = -1;
        if (isColumnExist(cursor, ProductEntry.COLUMN_PRODUCT_SOLD_QUANTITY))
            soldQuantity = cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SOLD_QUANTITY));

        String supplier = null;
        if (isColumnExist(cursor, ProductEntry.COLUMN_PRODUCT_SUPPLIER))
            supplier = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER));

        byte[] picture = null;
        if (isColumnExist(cursor, ProductEntry.COLUMN_PRODUCT_PICTURE))
            picture = cursor.getBlob(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PICTURE));

        return new Product(id, name, price, quantity, soldQuantity, supplier, picture);
    }

    private static boolean isColumnExist(Cursor cursor, String columnName) {
        return cursor.getColumnIndex(columnName) != COLUMN_NOT_EXIST;
    }

}
