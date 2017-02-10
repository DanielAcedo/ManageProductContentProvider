package com.danielacedo.manageproductrecycler.interfaces;

import android.content.Context;

import com.danielacedo.manageproductrecycler.model.Product;

/**
 * Created by usuario on 12/12/16.
 */

public interface ManageProductPresenter {
    void addProduct(Product product);

    void updateProduct(Product product);

    void onDestroy();

    boolean validateProductFields(String name, String description, String brand, String dosage, String price, String stock);

    interface View{
        void showMessage(int message);
        Context getContext();
    }
}
