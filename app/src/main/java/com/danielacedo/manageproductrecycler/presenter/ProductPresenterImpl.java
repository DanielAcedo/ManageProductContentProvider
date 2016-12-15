package com.danielacedo.manageproductrecycler.presenter;

import com.danielacedo.manageproductrecycler.ProductRepository;
import com.danielacedo.manageproductrecycler.interfaces.ProductPresenter;
import com.danielacedo.manageproductrecycler.model.Product;

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
        ProductRepository.getInstance().addProduct(product);
    }

    @Override
    public void loadProducts() {
        if(repository.getProducts().isEmpty()){
            view.showEmptyState(true);
        }else{
            view.showProducts(repository.getProducts());
        }
    }

    /* Example method for removing the object once the snackbar was dimissed
    @Override
    public void deleteTemp(Product product) {
        view.showMessageDelete(product);
    }*/

    @Override
    public void deleteProduct(Product product) {
        repository.deleteProduct(product);
        loadProducts();
        view.showMessageDelete(product);
    }

    @Override
    public Product getProduct(String id) {
        return repository.getProductById(id);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
