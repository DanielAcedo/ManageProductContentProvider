package com.danielacedo.manageproductrecycler.interfaces;

import android.content.Context;

import com.danielacedo.manageproductrecycler.model.Pharmacy;

/**
 * Created by Daniel on 01/02/2017.
 */

public interface ManagePharmacyPresenter {
    int ERR_OK = 0;
    int ERR_CIF_EMPTY = 1;
    int ERR_ADDR_EMPTY = 2;
    int ERR_PHONE_EMPTY = 3;
    int ERR_EMAIL_EMPTY = 4;

    boolean validateFields(String cif, String address, String phoneNumber, String email);

    void addPharmacy(Pharmacy pharmacy);

    void updatePharmacy(Pharmacy pharmacy);

    interface View{
        void showMessage(int msgId);
        void showMessage(String message);
        Context getContext();
    }
}
