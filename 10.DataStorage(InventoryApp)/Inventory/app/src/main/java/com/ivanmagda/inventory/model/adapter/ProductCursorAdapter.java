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
package com.ivanmagda.inventory.model.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.ivanmagda.inventory.R;
import com.ivanmagda.inventory.model.object.Product;
import com.ivanmagda.inventory.util.CurrencyUtils;
import com.ivanmagda.inventory.util.ProductUtils;

public class ProductCursorAdapter extends CursorAdapter {

    public interface OnSaleButtonClickListener {
        public void didPressSaleButtonForProduct(Product product);
    }

    private static final String LOG_TAG = ProductCursorAdapter.class.getSimpleName();

    private OnSaleButtonClickListener mOnSaleButtonClickListener;

    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public void setOnSaleButtonClickListener(OnSaleButtonClickListener onSaleButtonClickListener) {
        this.mOnSaleButtonClickListener = onSaleButtonClickListener;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(final View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template.
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);

        // Extract properties from cursor.
        final Product product = ProductUtils.productFromCursor(cursor);
        String quantityString = String.valueOf(product.getQuantity());
        String priceString = CurrencyUtils.currencyString(product.getPrice());

        nameTextView.setText(product.getName());
        quantityTextView.setText(quantityString);
        priceTextView.setText(priceString);

        Button saleButton = (Button) view.findViewById(R.id.sale_button);
        saleButton.setTag(product.getId());
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                Integer productId = (Integer) button.getTag();
                Log.d(LOG_TAG, "Did press sale button with item id: " + productId);
                if (mOnSaleButtonClickListener != null)
                    mOnSaleButtonClickListener.didPressSaleButtonForProduct(product);
            }
        });
    }

}
