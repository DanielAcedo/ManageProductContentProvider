package com.danielacedo.manageproductrecycler.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.danielacedo.manageproductrecycler.ListProduct_Application;
import com.danielacedo.manageproductrecycler.ProductRepository;
import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.database.DatabaseHelper;
import com.danielacedo.manageproductrecycler.database.DatabaseManager;
import com.danielacedo.manageproductrecycler.interfaces.ProductPresenter;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 9/12/16.
 */

public class ProductPresenterImpl implements ProductPresenter {
    private ProductPresenter.View view;

    private List<Integer> selectedItemsPosition;

    public ProductPresenterImpl(ProductPresenter.View view){
        this.view = view;

        selectedItemsPosition = new ArrayList<>();
    }

    @Override
    public void addProduct(Product product) {
        DatabaseManager.getInstance().addProduct(product);
        loadProducts();
    }

    public List<Product> getAllProducts(){
        return DatabaseManager.getInstance().getAllProducts();
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
        new AsyncTask<Void, Integer, List<Product>>(){
            ProgressDialog progressDialog = view.getProgressDialog();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.showMessage(R.string.message_loading_products);
                progressDialog.show();
            }

            @Override
            protected List<Product> doInBackground(Void... params) {
                return DatabaseManager.getInstance().getAllProducts();
            }

            @Override
            protected void onPostExecute(List<Product> productList) {
                if(productList.isEmpty()){
                    view.showEmptyState(true);
                }else{
                    view.showProducts(productList);
                }

                progressDialog.dismiss();
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();

                view.showMessage(R.string.message_loading_cancelled);
                progressDialog.dismiss();
            }
        }.execute();
    }

    /* Example method for removing the object once the snackbar was dimissed
    @Override
    public void deleteTemp(Product product) {
        view.showMessageDelete(product);
    }*/

    @Override
    public void deleteProduct(Product product) {
        DatabaseManager.getInstance().deleteProduct(product);
        loadProducts();
        view.showMessageDelete(product);
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
        List<Product> products = new ArrayList<>();

        for (int position: selectedItemsPosition) {
            products.add(view.getProduct(position));
        }

        if(DatabaseManager.getInstance().deleteProducts(products)){
            loadProducts();
        }else{
            view.showMessage(R.string.err_product_remove_error);
        }
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
