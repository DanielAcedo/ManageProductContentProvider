package com.danielacedo.manageproductrecycler.presenter;

import android.content.Context;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.database.DatabaseManager;
import com.danielacedo.manageproductrecycler.interfaces.ManagePharmacyPresenter;
import com.danielacedo.manageproductrecycler.interfaces.PharmacyPresenter;
import com.danielacedo.manageproductrecycler.model.Pharmacy;

/**
 * Created by Daniel on 01/02/2017.
 */

public class ManagePharmacyPresenterImpl implements ManagePharmacyPresenter {
    private ManagePharmacyPresenter.View view;

    public ManagePharmacyPresenterImpl(ManagePharmacyPresenter.View view){
        this.view = view;
    }

    @Override
    public boolean validateFields(String cif, String address, String phoneNumber, String email) {
        boolean result = true;

        if(cif.isEmpty()){
            view.showMessage(R.string.err_pharmacyCif_empty);
            result = false;
        }
        else if (address.isEmpty()){
            view.showMessage(R.string.err_pharmacyAddress_empty);
            result = false;
        }
        else if (phoneNumber.isEmpty()){
            view.showMessage(R.string.err_pharmacyPhone_empty);
            result = false;
        }
        else if (email.isEmpty()){
            view.showMessage(R.string.err_pharmacyEmail_empty);
            result = false;
        }

        return result;
    }

    @Override
    public void addPharmacy(Pharmacy pharmacy) {
        DatabaseManager.getInstance().addPharmacy(pharmacy);
    }

    @Override
    public void updatePharmacy(Pharmacy pharmacy) {
        DatabaseManager.getInstance().updatePharmacy(pharmacy);
    }
}
