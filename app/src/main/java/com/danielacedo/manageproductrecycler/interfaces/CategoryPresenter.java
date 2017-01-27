package com.danielacedo.manageproductrecycler.interfaces;


import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * Created by usuario on 26/01/17.
 */

public interface CategoryPresenter {
    void getAllCategory();

    interface View{
        void setCursorCategory(Cursor cursor);
        Context getContext();
    }
}
