package com.danielacedo.manageproductrecycler.interfaces;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;

import com.danielacedo.manageproductrecycler.model.Product;

import java.util.List;

/**
 * Created by usuario on 9/12/16.
 */

public interface ProductPresenter {

    void loadProducts();

    void deleteProduct(Product product);

    void addProduct(Product product);

    void addSelection(int id);

    void removeSelection(int id);

    void deleteSelected();

    void onDestroy();

    interface View{
        void showProducts(Cursor productCursor);

        void showEmptyState(boolean show);

        void showMessage(int message);

        void showMessageDelete(Product product);

        Context getContext();

        Product getProduct(int position);

        ProgressDialog getProgressDialog();
    }
}
