package com.danielacedo.manageproductrecycler.interfaces;

import com.danielacedo.manageproductrecycler.model.Product;

import java.util.List;

/**
 * Created by usuario on 9/12/16.
 */

public interface ProductPresenter {

    void loadProducts();

    void deleteProduct(Product product);

    Product getProduct(String id);

    void onDestroy();

    interface View{
        void showProducts(List<Product> products);

        void showEmptyState(boolean show);

        void showMessage(String message);
    }
}
