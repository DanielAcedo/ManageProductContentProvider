package com.danielacedo.manageproductrecycler.interfaces;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;

import com.danielacedo.manageproductrecycler.model.Pharmacy;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.List;

/**
 * Created by usuario on 30/01/17.
 */

public interface PharmacyPresenter {

    void loadPharmacy();

    void deletePharmacy(Pharmacy pharmacy);

    void addPharmacy(Pharmacy pharmacy);

    void onDestroy();

    interface View{
        void showPharmacy(Cursor pharmacyCursor);

        void showEmptyState(boolean show);

        void showMessage(int message);

        Context getContext();

        void showMessageDelete(Pharmacy pharmacy);

        ProgressDialog getProgressDialog();
    }
}
