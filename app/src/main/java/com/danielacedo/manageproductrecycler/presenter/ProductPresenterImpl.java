package com.danielacedo.manageproductrecycler.presenter;

import android.content.Context;

import com.danielacedo.manageproductrecycler.ProductRepository;
import com.danielacedo.manageproductrecycler.database.DatabaseManager;
import com.danielacedo.manageproductrecycler.interfaces.ProductPresenter;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.List;

/**
 * Created by usuario on 9/12/16.
 */

public class ProductPresenterImpl implements ProductPresenter {
    private ProductPresenter.View view;
    private ProductRepository repository;

    public ProductPresenterImpl(ProductPresenter.View view){
        this.view = view;
        this.repository = ProductRepository.getInstance();
    }

    @Override
    public void addProduct(Product product) {
        DatabaseManager.getInstance().addProduct(product);
        loadProducts();
    }

    public List<Product> getAllProducts(){
        return DatabaseManager.getInstance().getAllProducts();
    }

    @Override
    public void loadProducts() {
        List<Product> products = getAllProducts();

        if(products.isEmpty()){
            view.showEmptyState(true);
        }else{
            view.showProducts(products);
        }
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
    public void onDestroy() {
        this.view = null;
    }
}
