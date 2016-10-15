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
package com.ivanmagda.inventory.ui;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ivanmagda.inventory.R;
import com.ivanmagda.inventory.model.adapter.ProductCursorAdapter;
import com.ivanmagda.inventory.model.data.ProductContract.ProductEntry;
import com.ivanmagda.inventory.model.object.Product;


public class ProductsListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = ProductsListActivity.class.getSimpleName();

    // Identifies a particular Loader being used in this component.
    private static final int INVENTORY_LOADER = 0;

    ProductCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        configureProductList();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "Fab pressed");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertProduct();
                return true;
            case R.id.action_delete_all_entries:
                showDeleteConfirmationDialog();
                return true;
            default:
                Log.e(LOG_TAG, "Unresolved action with id: " + item.getItemId());
                return false;
        }
    }

    /**
     * Implement LoaderManager.LoaderCallbacks<Cursor>.
     */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case INVENTORY_LOADER:
                final String[] projection = {
                        ProductEntry._ID,
                        ProductEntry.COLUMN_PRODUCT_NAME,
                        ProductEntry.COLUMN_PRODUCT_PRICE,
                        ProductEntry.COLUMN_PRODUCT_QUANTITY,
                        ProductEntry.COLUMN_PRODUCT_SOLD_QUANTITY
                };
                String sort = ProductEntry._ID + " DESC";
                return new CursorLoader(this, ProductEntry.CONTENT_URI, projection, null, null, sort);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    /*
     * Invoked when the CursorLoader is being reset. For example, this is
     * called if the data in the provider changes and the Cursor becomes stale.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mCursorAdapter.swapCursor(null);
    }

    /**
     * Helper methods.
     */

    private void configureProductList() {
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty_view));
        mCursorAdapter = new ProductCursorAdapter(this, null);
        listView.setAdapter(mCursorAdapter);

        mCursorAdapter.setOnSaleButtonClickListener(new ProductCursorAdapter.OnSaleButtonClickListener() {
            @Override
            public void didPressSaleButtonForProduct(Product product) {
                sellProduct(product);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "Did select item at index " + position);
            }
        });

        getLoaderManager().initLoader(INVENTORY_LOADER, null, this);
    }

    /**
     * Helper method to insert hardcoded product data into the database. For debugging purposes only.
     */
    private void insertProduct() {
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, "Shovel");
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, 10.99);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, 7);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER, "vanyaland@mail.ru");
        values.put(ProductEntry.COLUMN_PRODUCT_SOLD_QUANTITY, 0);

        // Insert a new row for Shovel into the provider using the ContentResolver.
        // Use the {@link ProductContract.ProductEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Shovel's data in the future.
        Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
        Log.v(LOG_TAG, "New row uri " + newUri);
        Toast.makeText(this, R.string.insert_success_msg, Toast.LENGTH_SHORT).show();
    }

    private void showDeleteConfirmationDialog() {
        if (mCursorAdapter.getCount() == 0) {
            Toast.makeText(this, R.string.delete_empty_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_all_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteAllProducts();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteAllProducts() {
        int rowsDeleted = getContentResolver().delete(ProductEntry.CONTENT_URI, null, null);
        Log.v(LOG_TAG, rowsDeleted + " rows deleted from inventory database");
        Toast.makeText(this, R.string.delete_success_msg, Toast.LENGTH_SHORT).show();
    }

    private void sellProduct(Product product) {
        if (product.getQuantity() == 0) {
            Toast.makeText(this, R.string.sale_product_empty_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, product.getId());
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, product.getQuantity() - 1);
        values.put(ProductEntry.COLUMN_PRODUCT_SOLD_QUANTITY, product.getSoldQuantity() + 1);

        int updatedRows = getContentResolver().update(uri, values, null, null);
        Toast.makeText(this,
                (updatedRows != 0 ? R.string.sale_product_success : R.string.sale_product_fail_msg),
                Toast.LENGTH_SHORT).show();
    }

}
