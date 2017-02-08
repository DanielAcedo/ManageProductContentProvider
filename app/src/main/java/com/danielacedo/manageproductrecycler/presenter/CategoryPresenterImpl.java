package com.danielacedo.manageproductrecycler.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;

import com.danielacedo.manageproductrecycler.db.DatabaseContract;
import com.danielacedo.manageproductrecycler.interfaces.CategoryPresenter;
import com.danielacedo.manageproductrecycler.provider.ManageProductContract;
import com.danielacedo.manageproductrecycler.provider.ManageProductProvider;

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
                loader = new CursorLoader(context, ManageProductContract.CategoryEntry.CONTENT_URI,
                        ManageProductContract.CategoryEntry.PROJECTION,
                        null, null, DatabaseContract.CategoryEntry.DEFAULT_SORT);
                break;
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.setNotificationUri(context.getContentResolver(), ManageProductContract.CategoryEntry.CONTENT_URI);
        view.setCursorCategory(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        view.setCursorCategory(null);
    }
}
