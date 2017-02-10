package com.danielacedo.manageproductrecycler.presenter;

import android.content.ContentValues;
import android.content.Context;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.interfaces.ManagePharmacyPresenter;
import com.danielacedo.manageproductrecycler.model.Pharmacy;
import com.danielacedo.manageproductrecycler.provider.ManageProductContract;

/**
 * Created by Daniel on 01/02/2017.
 */

public class ManagePharmacyPresenterImpl implements ManagePharmacyPresenter {
    private ManagePharmacyPresenter.View view;
    private Context context;

    public ManagePharmacyPresenterImpl(ManagePharmacyPresenter.View view)
    {
        this.view = view;
        this.context = view.getContext();
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

    private ContentValues getContentPharmacy(Pharmacy pharmacy){
        ContentValues contentValues = new ContentValues();

        contentValues.put(ManageProductContract.PharmacyEntry._ID, pharmacy.getId());
        contentValues.put(ManageProductContract.PharmacyEntry.CIF, pharmacy.getCif());
        contentValues.put(ManageProductContract.PharmacyEntry.ADDRESS, pharmacy.getAddress());
        contentValues.put(ManageProductContract.PharmacyEntry.EMAIL, pharmacy.getEmail());
        contentValues.put(ManageProductContract.PharmacyEntry.TELEPHONENUMBER, pharmacy.getTelephone_number());

        return contentValues;
    }

    @Override
    public void addPharmacy(Pharmacy pharmacy) {
        context.getContentResolver().insert(ManageProductContract.PharmacyEntry.CONTENT_URI, getContentPharmacy(pharmacy));
    }

    @Override
    public void updatePharmacy(Pharmacy pharmacy) {
        context.getContentResolver().update(ManageProductContract.PharmacyEntry.CONTENT_URI,
                getContentPharmacy(pharmacy),
                ManageProductContract.PharmacyEntry._ID+"="+pharmacy.getId(),
                null);
    }
}
