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
    public void loadProducts() {
        if(repository.getProducts().isEmpty()){
            view.showEmptyState(true);
        }else{
            view.showProducts(repository.getProducts());
        }
    }

    @Override
    public void deleteProduct(Product product) {
        repository.deleteProduct(product);

        loadProducts();
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
