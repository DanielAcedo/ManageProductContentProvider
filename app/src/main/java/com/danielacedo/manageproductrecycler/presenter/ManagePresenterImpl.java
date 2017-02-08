package com.danielacedo.manageproductrecycler.presenter;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.db.DatabaseManager;
import com.danielacedo.manageproductrecycler.interfaces.ManageProductPresenter;
import com.danielacedo.manageproductrecycler.model.Product;

/**
 * Created by usuario on 12/12/16.
 */

public class ManagePresenterImpl implements ManageProductPresenter {

    private ManageProductPresenter.View view;

    public ManagePresenterImpl(ManageProductPresenter.View view){
        this.view = view;
    }

    @Override
    public void addProduct(Product product) {
        DatabaseManager.getInstance().addProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        DatabaseManager.getInstance().updateProduct(product);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public boolean validateProductFields(String name, String description, String brand, String dosage, String price, String stock, String image) {

        boolean ok = true;
        int message = 0;

        if(name.isEmpty()){ //If it's empty send error message
            message = R.string.err_productName_empty;
            ok = false;
        }
        else if(description.isEmpty()){
            message = R.string.err_productDescription_empty;
            ok = false;
        }
        else if(brand.isEmpty()){
            message = R.string.err_productBrand_empty;
            ok = false;
        }
        else if(dosage.isEmpty()){
            message = R.string.err_productDosage_empty;
            ok = false;
        }
        else if(price.isEmpty()){
            message = R.string.err_productPrice_empty;
            ok = false;
        }
        else if(stock.isEmpty()){
            message = R.string.err_productStock_empty;
            ok = false;
        }
        else if(image.isEmpty()){
            message = R.string.err_productImage_empty;
            ok = false;
        }

        if(!ok){
            view.showMessage(message);
        }

        return ok;

    }
}
