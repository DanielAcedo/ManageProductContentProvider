package com.danielacedo.manageproductrecycler.cursor;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import com.danielacedo.manageproductrecycler.database.DatabaseManager;

/**
 * Created by usuario on 30/01/17.
 */

public class PharmacyCursorLoader extends CursorLoader{

    public PharmacyCursorLoader(Context context){
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return DatabaseManager.getInstance().getAllPharmacy();
    }
}
