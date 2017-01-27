package com.danielacedo.manageproductrecycler.presenter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.app.LoaderManager;
import android.content.Loader;
import android.widget.CursorAdapter;
import android.widget.AdapterView;


import com.danielacedo.manageproductrecycler.cursor.CategoryCursorLoader;
import com.danielacedo.manageproductrecycler.database.DatabaseManager;
import com.danielacedo.manageproductrecycler.interfaces.CategoryPresenter;

/**
 * Created by usuario on 26/01/17.
 */

public class CategoryPresenterImpl implements CategoryPresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private CategoryPresenter.View view;
    private Context context;
    private final static int CATEGORY = 1;

    public CategoryPresenterImpl(CategoryPresenter.View view){
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void getAllCategory() {
        ((Activity)context).getLoaderManager().initLoader(CATEGORY, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;

        switch(id){
            case CATEGORY:
                loader = new CategoryCursorLoader(context);
                break;
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        view.setCursorCategory(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        view.setCursorCategory(null);
    }
}
