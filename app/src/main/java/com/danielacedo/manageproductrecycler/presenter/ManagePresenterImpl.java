package com.danielacedo.manageproductrecycler.presenter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.db.DatabaseContract;
import com.danielacedo.manageproductrecycler.interfaces.ManageProductPresenter;
import com.danielacedo.manageproductrecycler.model.Product;
import com.danielacedo.manageproductrecycler.provider.ManageProductContract;

/**
 * Created by usuario on 12/12/16.
 */

public class ManagePresenterImpl implements ManageProductPresenter {
    //TODO REPLACE WITH CONTENTPROVIDER

    private ManageProductPresenter.View view;
    private Context context;

    public ManagePresenterImpl(ManageProductPresenter.View view){
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void addProduct(Product product) {
        ContentValues values = getContentProduct(product);

        context.getContentResolver().insert(ManageProductContract.ProductEntry.CONTENT_URI, values);
    }

    private ContentValues getContentProduct(Product product){
        ContentValues contentValues = new ContentValues();

        contentValues.put(ManageProductContract.ProductEntry._ID, product.getId());
        contentValues.put(ManageProductContract.ProductEntry.NAME, product.getName());
        contentValues.put(ManageProductContract.ProductEntry.DESCRIPTION, product.getDescription());
        contentValues.put(ManageProductContract.ProductEntry.BRAND, product.getBrand());
        contentValues.put(ManageProductContract.ProductEntry.DOSAGE, product.getDosage());
        contentValues.put(ManageProductContract.ProductEntry.PRICE, product.getPrice());
        contentValues.put(ManageProductContract.ProductEntry.STOCK, product.getStock());
        contentValues.put(ManageProductContract.ProductEntry.IMAGE, product.getImage());
        contentValues.put(ManageProductContract.ProductEntry.sProductProjectionMap.get(ManageProductContract.ProductEntry.CATEGORY_ID),
                product.getId_category());

        return contentValues;
    }

    @Override
    public void updateProduct(Product product) {
        context.getContentResolver().update(
                ContentUris.withAppendedId(ManageProductContract.ProductEntry.CONTENT_URI, product.getId()),
                getContentProduct(product),
                ManageProductContract.ProductEntry._ID+"="+product.getId(), null);
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
