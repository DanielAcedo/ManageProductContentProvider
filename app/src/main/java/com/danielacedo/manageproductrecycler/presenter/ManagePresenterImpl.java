package com.danielacedo.manageproductrecycler.presenter;

import android.content.Context;

import com.danielacedo.manageproductrecycler.ProductRepository;
import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.interfaces.ManagePresenter;
import com.danielacedo.manageproductrecycler.model.Product;

/**
 * Created by usuario on 12/12/16.
 */

public class ManagePresenterImpl implements ManagePresenter {

    private ManagePresenter.View view;

    public ManagePresenterImpl(ManagePresenter.View view){
        this.view = view;
    }

    @Override
    public void addProduct(Product product) {
        ProductRepository.getInstance().addProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        ProductRepository.getInstance().updateProduct(product);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public boolean validateProductFields(String name, String description, String brand, String dosage, String price, String stock, String image) {

        boolean ok = true;
        String message = "";

        if(name.isEmpty()){ //If it's empty send error message
            message = ((Context)view).getResources().getString(R.string.err_productName_empty);
            ok = false;
        }
        else if(description.isEmpty()){
            message = ((Context)view).getResources().getString(R.string.err_productDescription_empty);
            ok = false;
        }
        else if(brand.isEmpty()){
            message = ((Context)view).getResources().getString(R.string.err_productBrand_empty);
            ok = false;
        }
        else if(dosage.isEmpty()){
            message = ((Context)view).getResources().getString(R.string.err_productDosage_empty);
            ok = false;
        }
        else if(price.isEmpty()){
            message = ((Context)view).getResources().getString(R.string.err_productPrice_empty);
            ok = false;
        }
        else if(stock.isEmpty()){
            message = ((Context)view).getResources().getString(R.string.err_productStock_empty);
            ok = false;
        }
        else if(image.isEmpty()){
            message = ((Context)view).getResources().getString(R.string.err_productImage_empty);
            ok = false;
        }

        if(!ok){
            view.showMessage(message);
        }

        return ok;

    }
}
