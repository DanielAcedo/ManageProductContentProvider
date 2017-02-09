package com.danielacedo.manageproductrecycler.presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.db.DatabaseContract;
import com.danielacedo.manageproductrecycler.interfaces.ProductPresenter;
import com.danielacedo.manageproductrecycler.model.Product;
import com.danielacedo.manageproductrecycler.provider.ManageProductContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 9/12/16.
 */

public class ProductPresenterImpl implements ProductPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    private ProductPresenter.View view;

    private final static int PRODUCT = 3;

    private List<Integer> selectedItemsPosition;
    private Context context;

    public ProductPresenterImpl(ProductPresenter.View view){
        this.view = view;
        this.context = view.getContext();

        selectedItemsPosition = new ArrayList<>();
    }

    @Override
    public void addProduct(Product product) {
        ContentValues values = getContentProduct(product);

        context.getContentResolver().insert(ManageProductContract.ProductEntry.CONTENT_URI, values);
    }

    private ContentValues getContentProduct(Product product){
        ContentValues contentValues = new ContentValues();

        contentValues.put(ManageProductContract.ProductEntry.NAME, product.getName());
        contentValues.put(ManageProductContract.ProductEntry.DESCRIPTION, product.getDescription());
        contentValues.put(ManageProductContract.ProductEntry.BRAND, product.getBrand());
        contentValues.put(ManageProductContract.ProductEntry.DOSAGE, product.getDosage());
        contentValues.put(ManageProductContract.ProductEntry.PRICE, product.getPrice());
        contentValues.put(ManageProductContract.ProductEntry.STOCK, product.getStock());
        contentValues.put(ManageProductContract.ProductEntry.IMAGE, product.getImage());
        contentValues.put(ManageProductContract.ProductEntry.CATEGORY_ID, product.getId_category());

        return contentValues;
    }

    /*@Override
    public void loadProducts() {
        List<Product> products = getAllProducts();

        if(products.isEmpty()){
            view.showEmptyState(true);
        }else{
            view.showProducts(products);
        }
    }*/

    public void loadProducts(){
        Loader loader = ((Activity)context).getLoaderManager().getLoader(PRODUCT);

        ProgressDialog dialog = view.getProgressDialog();
        dialog.setTitle("Cargando productos...");
        dialog.show();

        if(loader == null){
            ((Activity)context).getLoaderManager().initLoader(PRODUCT, null, this);
        }else{
            ((Activity)context).getLoaderManager().restartLoader(PRODUCT, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;

        switch (id){
            case PRODUCT:
                loader = new CursorLoader(context, ManageProductContract.ProductEntry.CONTENT_URI,
                        ManageProductContract.ProductEntry.PROJECTION, null, null, DatabaseContract.ProductEntry.DEFAULT_SORT);
                break;
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.setNotificationUri(context.getContentResolver(), ManageProductContract.ProductEntry.CONTENT_URI);

        view.showProducts(data);

        view.getProgressDialog().dismiss();
    }

    /* Example method for removing the object once the snackbar was dimissed
    @Override
    public void deleteTemp(Product product) {
        view.showMessageDelete(product);
    }*/

    @Override
    public void deleteProduct(Product product) {
        int result = context.getContentResolver().delete(ManageProductContract.ProductEntry.CONTENT_URI,
                ManageProductContract.ProductEntry._ID+"="+product.getId(), null);


        if(result != 0){
            loadProducts();
        }else{
            view.showMessage("No se ha eliminado");
        }
    }

    @Override
    public void addSelection(int position) {
        selectedItemsPosition.add(position);
    }

    @Override
    public void removeSelection(int position) {
        selectedItemsPosition.remove((Integer)position);
    }

    @Override
    public void deleteSelected() {

        for (int position: selectedItemsPosition) {
            deleteProduct(view.getProduct(position));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        view.showProducts(null);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
