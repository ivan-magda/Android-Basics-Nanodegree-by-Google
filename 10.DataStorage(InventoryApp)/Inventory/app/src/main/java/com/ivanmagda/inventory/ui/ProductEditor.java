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

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanmagda.inventory.R;
import com.ivanmagda.inventory.model.data.ProductContract.ProductEntry;
import com.ivanmagda.inventory.model.object.Product;
import com.ivanmagda.inventory.util.ImageUtils;
import com.ivanmagda.inventory.util.ProductUtils;

import java.io.File;

import static com.ivanmagda.inventory.ui.ProductEditor.EditorActivityMode.CREATE_NEW;
import static com.ivanmagda.inventory.ui.ProductEditor.EditorActivityMode.EDIT;

public class ProductEditor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    enum EditorActivityMode {EDIT, CREATE_NEW}

    private static final String LOG_TAG = ProductEditor.class.getSimpleName();

    private static final int PRODUCT_LOADER = 1;
    private static final int CHOOSE_PICTURE_RESULT = 2;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 3;

    /**
     * Helps to understand in what mode activity started.
     */
    private EditorActivityMode mActivityMode = CREATE_NEW;

    /**
     * Uri of the current editing product or null if add one.
     */
    private Uri mCurrentProductUri;

    /**
     * ImageView for presenting picture of the product.
     */
    private ImageView mProductImageView;

    /**
     * EditText field to enter the products's name.
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the products's price.
     */
    private EditText mPriceEditText;

    /**
     * EditText field to enter the products's quantity.
     */
    private EditText mQuantityEditText;

    /**
     * EditText field to enter the products's supplier email address.
     */
    private EditText mSupplierEmailEditText;

    /**
     * TextView to show the current products's sale info.
     */
    private TextView mSoldQuantityTextView;

    /**
     * TextView to show the current products's receive shipment info.
     */
    private TextView mReceiveQuantityTextView;

    private boolean mProductHasChanged = false;
    private View.OnTouchListener mProductChangesTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };

    /**
     * Currently editing product.
     * Used only when {@link ProductEditor} in the edit mode {@link #mActivityMode}.
     */
    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);
        configure();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PICTURE_RESULT)
            didFinishChooseImageFromGallery(resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startPickImageIntent();
            } else {
                Snackbar.make(findViewById(android.R.id.content),
                        R.string.enable_persmission_from_settings,
                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.enable,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(intent);
                            }
                        }).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mActivityMode == CREATE_NEW) {
            menu.findItem(R.id.action_delete).setVisible(false);
            menu.findItem(R.id.action_order).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (saveProduct()) finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case R.id.action_order:
                placeOrder();
                return true;
            case android.R.id.home:
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(ProductEditor.this);
                    return true;
                }

                showUnsavedChangesDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(ProductEditor.this);
                    }
                });

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        }
        showUnsavedChangesDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    /**
     * Private configure helper methods.
     */

    private void configure() {
        mCurrentProductUri = getIntent().getData();
        mActivityMode = (mCurrentProductUri == null ? CREATE_NEW : EDIT);

        findViews();
        setListeners();

        if (mActivityMode == CREATE_NEW) {
            setTitle(R.string.editor_activity_title_new_product);
            findViewById(R.id.container_track_sale).setVisibility(View.GONE);
            findViewById(R.id.container_receive_shipment).setVisibility(View.GONE);
            invalidateOptionsMenu();
        } else {
            setTitle(R.string.editor_activity_title_edit_product);
            mQuantityEditText.setKeyListener(null);
            mQuantityEditText.setEnabled(false);

            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
        }
    }

    private void findViews() {
        mProductImageView = (ImageView) findViewById(R.id.product_image_view);
        mNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mPriceEditText = (EditText) findViewById(R.id.edit_product_price);
        mQuantityEditText = (EditText) findViewById(R.id.edit_product_quantity);
        mSupplierEmailEditText = (EditText) findViewById(R.id.edit_product_supplier);
        mSoldQuantityTextView = (TextView) findViewById(R.id.sold_quantity_text_view);
        mReceiveQuantityTextView = (TextView) findViewById(R.id.receive_quantity_text_view);
    }

    private void setListeners() {
        mNameEditText.setOnTouchListener(mProductChangesTouchListener);
        mPriceEditText.setOnTouchListener(mProductChangesTouchListener);
        mQuantityEditText.setOnTouchListener(mProductChangesTouchListener);
        mSupplierEmailEditText.setOnTouchListener(mProductChangesTouchListener);
        mSoldQuantityTextView.setOnTouchListener(mProductChangesTouchListener);
        mReceiveQuantityTextView.setOnTouchListener(mProductChangesTouchListener);

        findViewById(R.id.decrement_sale_button).setOnTouchListener(mProductChangesTouchListener);
        findViewById(R.id.increment_sale_button).setOnTouchListener(mProductChangesTouchListener);
        findViewById(R.id.decrement_receive_button).setOnTouchListener(mProductChangesTouchListener);
        findViewById(R.id.increment_receive_button).setOnTouchListener(mProductChangesTouchListener);
        findViewById(R.id.container_product_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductHasChanged = true;
                if (mProductImageView.getDrawable() == null)
                    choosePictureFromGallery();
                else
                    showChangePictureConfirmationDialog();
            }
        });
    }

    /**
     * Implement LoaderManager.LoaderCallbacks<Cursor>.
     */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, mCurrentProductUri, ProductEntry.PROJECTION_ALL, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (!cursor.moveToFirst()) return;

        mProduct = ProductUtils.fromCursor(cursor);
        updateUiWithProduct(mProduct);
    }

    private void updateUiWithProduct(Product product) {
        mNameEditText.setText(product.getName());
        mPriceEditText.setText(String.valueOf(product.getPrice()));
        mQuantityEditText.setText(String.valueOf(product.getQuantity()));
        mSupplierEmailEditText.setText(product.getSupplier());
        mSoldQuantityTextView.setText(String.valueOf(product.getSoldQuantity()));

        byte[] bytes = mProduct.getPicture();
        if (bytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            mProductImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProduct = null;
    }

    /**
     * Private helper method for choosing image from the gallery.
     */
    private void choosePictureFromGallery() {
        if (ContextCompat.checkSelfPermission(ProductEditor.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ProductEditor.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.enable,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestReadExternalStoragePermission();
                            }
                        }).show();
            } else {
                requestReadExternalStoragePermission();
            }
        } else {
            startPickImageIntent();
        }
    }

    private void startPickImageIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, CHOOSE_PICTURE_RESULT);
    }

    private void requestReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(ProductEditor.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_READ_EXTERNAL_STORAGE
        );
    }

    private void didFinishChooseImageFromGallery(int resultCode, Intent data) {
        Toast failedToast = Toast.makeText(this, R.string.choose_image_failed_msg, Toast.LENGTH_LONG);
        try {
            if (resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, projection, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    cursor.moveToFirst();
                    File imageFile = new File(cursor.getString(columnIndex));
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    mProductImageView.setImageBitmap(bitmap);
                } else {
                    failedToast.show();
                }
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to choose image", e);
            failedToast.show();
        }
    }

    /**
     * Helper public method for tracking product sale and receive quantities.
     *
     * @param button pressed button.
     */
    public void trackQuantityButtonPressed(View button) {
        int id = button.getId();
        switch (id) {
            case R.id.decrement_sale_button:
                updateSaleQuantityTextView(mProduct.decrementSaleQuantity());
                break;
            case R.id.increment_sale_button:
                updateSaleQuantityTextView(mProduct.incrementSaleQuantity());
                break;
            case R.id.decrement_receive_button:
                updateReceiveQuantityTextView(mProduct.decrementReceiveQuantity());
                break;
            case R.id.increment_receive_button:
                updateReceiveQuantityTextView(mProduct.incrementReceiveQuantity());
                break;
            default:
                throw new IllegalArgumentException("Unsupported button pressed with id: " + id);
        }

        mQuantityEditText.setText(String.valueOf(mProduct.getQuantity()));
    }

    private void updateSaleQuantityTextView(int value) {
        mSoldQuantityTextView.setText(String.valueOf(value));
    }

    private void updateReceiveQuantityTextView(int value) {
        mReceiveQuantityTextView.setText(String.valueOf(value));
    }

    /**
     * Private helper methods for working with the database.
     */

    private boolean saveProduct() {
        ContentValues values = readFromInputs();
        if (values == null) return false;

        switch (mActivityMode) {
            case CREATE_NEW:
                Uri newRowUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
                if (newRowUri == null) {
                    Toast.makeText(this, R.string.editor_insert_product_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.editor_insert_product_successful, Toast.LENGTH_SHORT).show();
                    return true;
                }
                break;
            case EDIT:
                int updatedRows = getContentResolver().update(mCurrentProductUri, values, null, null);
                if (updatedRows == 1) {
                    Toast.makeText(this, R.string.editor_edit_product_successful, Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(this, R.string.editor_edit_product_failed, Toast.LENGTH_SHORT).show();
                }
        }
        return false;
    }

    private ContentValues readFromInputs() {
        byte[] picture = ImageUtils.bytesFromImageView(mProductImageView);
        if (picture == null) {
            showToastWithMessage(getString(R.string.choose_picture_msg));
            return null;
        }

        String name = mNameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToastWithMessage(getString(R.string.enter_product_name_msg));
            return null;
        }

        String priceString = mPriceEditText.getText().toString().trim();
        if (TextUtils.isEmpty(priceString)) {
            showToastWithMessage(getString(R.string.enter_product_price_msg));
            return null;
        }
        double price = Double.parseDouble(priceString);

        String quantityString = mQuantityEditText.getText().toString().trim();
        if (TextUtils.isEmpty(quantityString)) {
            showToastWithMessage(getString(R.string.enter_quantity_msg));
            return null;
        }
        int quantity = Integer.parseInt(quantityString);

        String supplier = mSupplierEmailEditText.getText().toString().trim();
        if (TextUtils.isEmpty(supplier)) {
            showToastWithMessage(getString(R.string.enter_supplier_email_msg));
            return null;
        }

        int soldQuantity;
        if (mActivityMode == EDIT) {
            assert mProduct != null;
            soldQuantity = mProduct.getSoldQuantity();
        } else {
            String soldString = mSoldQuantityTextView.getText().toString();
            if (TextUtils.isEmpty(soldString)) {
                showToastWithMessage(getString(R.string.enter_sold_quantity_msg));
                return null;
            }
            soldQuantity = Integer.parseInt(soldString);
        }

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, name);
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(ProductEntry.COLUMN_PRODUCT_SOLD_QUANTITY, soldQuantity);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER, supplier);
        values.put(ProductEntry.COLUMN_PRODUCT_PICTURE, picture);

        return values;
    }

    /**
     * Perform the deletion of the product in the database.
     */
    private void deleteProduct() {
        if (mActivityMode == CREATE_NEW) {
            Toast.makeText(this, R.string.editor_delete_product_failed, Toast.LENGTH_SHORT).show();
        } else {
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);
            if (rowsDeleted == 0)
                Toast.makeText(this, R.string.editor_delete_product_failed, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, R.string.editor_delete_product_successful, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    /**
     * Build and show confirmation dialogs.
     */

    private void showChangePictureConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.change_picture_dialog_msg);
        builder.setPositiveButton(getString(R.string.change), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choosePictureFromGallery();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, listener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteProduct();
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

    /**
     * Helper method for placing an order with the specified supplier.
     */
    private void placeOrder() {
        assert mProduct != null;

        if (mProduct.getReceiveQuantity() == 0) {
            Toast.makeText(this, R.string.place_order_failed_msg, Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mProduct.getSupplier()});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.place_order_subject) + mProduct.getName());
        intent.putExtra(Intent.EXTRA_TEXT, generateOrderSummary());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.send_email_failed_msg, Toast.LENGTH_LONG).show();
        }
    }

    private String generateOrderSummary() {
        return "We need more " + mProduct.getName() +
                " Please ship it with quantity of " + mProduct.getReceiveQuantity() + ".";
    }

    private void showToastWithMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
