package com.danielacedo.manageproductrecycler.cursor;

import android.content.Context;
import android.database.Cursor;
import android.content.CursorLoader;

import com.danielacedo.manageproductrecycler.database.DatabaseManager;

/**
 * Created by usuario on 27/01/17.
 */

public class CategoryCursorLoader extends CursorLoader {
    public CategoryCursorLoader(Context context){
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return DatabaseManager.getInstance().getAllCategories();
    }
}
