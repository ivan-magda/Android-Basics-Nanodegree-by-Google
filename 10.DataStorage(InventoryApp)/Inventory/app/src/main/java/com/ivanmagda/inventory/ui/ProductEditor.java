package com.ivanmagda.inventory.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.ivanmagda.inventory.R;

import static com.ivanmagda.inventory.ui.ProductEditor.EditorActivityMode.CREATE_NEW;
import static com.ivanmagda.inventory.ui.ProductEditor.EditorActivityMode.EDIT;

public class ProductEditor extends AppCompatActivity {

    enum EditorActivityMode {EDIT, CREATE_NEW}

    /**
     * Helps to understand in what mode activity started.
     */
    private EditorActivityMode mActivityMode = CREATE_NEW;

    /**
     * Uri of the current editing product or null if add one.
     */
    private Uri mCurrentProductUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);

        mCurrentProductUri = getIntent().getData();
        mActivityMode = (mCurrentProductUri == null ? CREATE_NEW : EDIT);

        configureUI();
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

    private void configureUI() {
        if (mActivityMode == CREATE_NEW) {
            setTitle(R.string.editor_activity_title_new_product);
            findViewById(R.id.container_track_sale).setVisibility(View.GONE);
            findViewById(R.id.container_receive_shipment).setVisibility(View.GONE);
        } else {
            setTitle(R.string.editor_activity_title_edit_product);
        }
    }

}
