package com.danielacedo.manageproductrecycler.interfaces;

import com.danielacedo.manageproductrecycler.model.Product;

/**
 * Created by usuario on 9/12/16.
 */

public interface IRepository {
    Product getProductById(String id);

    void deleteProduct(Product product);

    void addProduct(Product product);

    void updateProduct(Product product);
}
