package com.danielacedo.manageproductrecycler.presenter;

import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.widget.AdapterView;


import com.danielacedo.manageproductrecycler.database.DatabaseManager;
import com.danielacedo.manageproductrecycler.interfaces.CategoryPresenter;

/**
 * Created by usuario on 26/01/17.
 */

public class CategoryPresenterImpl implements CategoryPresenter {

    private CategoryPresenter.View view;

    @Override
    public void getAllCategory(CursorAdapter adapter) {
        Cursor cursor = DatabaseManager.getInstance().getAllCategories();
        adapter.swapCursor(cursor);
    }


}
