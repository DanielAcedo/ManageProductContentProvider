package com.danielacedo.manageproductrecycler.presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.danielacedo.manageproductrecycler.db.DatabaseContract;
import com.danielacedo.manageproductrecycler.db.DatabaseManager;
import com.danielacedo.manageproductrecycler.interfaces.PharmacyPresenter;
import com.danielacedo.manageproductrecycler.model.Pharmacy;
import com.danielacedo.manageproductrecycler.provider.ManageProductContract;

/**
 * Created by usuario on 30/01/17.
 */

public class PharmacyPresenterImpl implements PharmacyPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    private PharmacyPresenter.View view;


    private Context context;
    private final static int PHARMACY = 2;

    public PharmacyPresenterImpl(PharmacyPresenter.View view){
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void loadPharmacy() {
        Loader loader = ((Activity)context).getLoaderManager().getLoader(PHARMACY);

        ProgressDialog dialog = view.getProgressDialog();
        dialog.setTitle("Cargando farmacias...");
        dialog.show();

        if(loader == null){
            ((Activity)context).getLoaderManager().initLoader(PHARMACY, null, this);
        }else{
            ((Activity)context).getLoaderManager().restartLoader(PHARMACY, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;

        switch (id){
            case PHARMACY:
                loader = new CursorLoader(context, ManageProductContract.PharmacyEntry.CONTENT_URI,
                       ManageProductContract.PharmacyEntry.PROJECTION, null, null, DatabaseContract.PharmacyEntry.DEFAULT_SORT);
                break;
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.setNotificationUri(context.getContentResolver(), ManageProductContract.PharmacyEntry.CONTENT_URI);
        view.showPharmacy(data);

        view.getProgressDialog().dismiss();
    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        view.showPharmacy(null);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void deletePharmacy(Pharmacy pharmacy) {
        DatabaseManager.getInstance().deletePharmacy(pharmacy);
        loadPharmacy();
    }

    @Override
    public void addPharmacy(Pharmacy pharmacy) {
        DatabaseManager.getInstance().addPharmacy(pharmacy);
        loadPharmacy();
    }
}
