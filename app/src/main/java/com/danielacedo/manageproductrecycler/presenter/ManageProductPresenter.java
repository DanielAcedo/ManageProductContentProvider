package com.danielacedo.manageproductrecycler.presenter;

/**
 * Created by Daniel on 20/10/16.
 */

import android.content.Context;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.interfaces.IAddProductMvp;

/**
 * Presenter for the AddProductView
 * @author Daniel Acedo Calderón
 */
public class ManageProductPresenter implements IAddProductMvp.Presenter{

    IAddProductMvp.View view;

    public ManageProductPresenter(IAddProductMvp.View view){
        this.view = view;
    }

    @Override
    public boolean validateProductFields(String name, String description, String brand, String dosage, String price, String stock, String image) {

        boolean ok = true;

        if(name.isEmpty()){ //If it's empty send error message
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productName_empty), R.id.edt_Name);
            ok = false;
        }
        else if(description.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productDescription_empty), R.id.edt_Description);
            ok = false;
        }
        else if(brand.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productBrand_empty), R.id.edt_Brand);
            ok = false;
        }
        else if(dosage.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productDosage_empty), R.id.edt_Dosage);
            ok = false;
        }
        else if(price.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productPrice_empty), R.id.edt_Price);
            ok = false;
        }
        else if(stock.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productStock_empty), R.id.edt_Stock);
            ok = false;
        }
        else if(image.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productImage_empty), R.id.edt_Image);
            ok = false;
        }

        else{
            ok = true;
        }

        return ok;

    }



}