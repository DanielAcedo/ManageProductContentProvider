package com.danielacedo.manageproductrecycler.presenter;

import android.database.Cursor;
import android.os.AsyncTask;

import com.danielacedo.manageproductrecycler.database.DatabaseManager;
import com.danielacedo.manageproductrecycler.interfaces.PharmacyPresenter;
import com.danielacedo.manageproductrecycler.model.Pharmacy;

/**
 * Created by usuario on 30/01/17.
 */

public class PharmacyPresenterImpl implements PharmacyPresenter {
    private PharmacyPresenter.View view;

    public PharmacyPresenterImpl(PharmacyPresenter.View view){
        this.view = view;
    }

    @Override
    public void loadPharmacy() {
        new AsyncTask<Void, Void, Cursor>(){
            @Override
            protected Cursor doInBackground(Void... params) {
                return DatabaseManager.getInstance().getAllPharmacy();
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);
                view.showPharmacy(cursor);
            }
        }.execute();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void addPharmacy(Pharmacy product) {

    }

    @Override
    public void deletePharmacy(Pharmacy pharmacy) {

    }
}
