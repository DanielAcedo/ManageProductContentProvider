package com.danielacedo.manageproductrecycler.interfaces;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;

import com.danielacedo.manageproductrecycler.model.Invoice;
import com.danielacedo.manageproductrecycler.model.Pharmacy;

/**
 * Created by usuario on 30/01/17.
 */

public interface InvoicePresenter {

    void loadInvoice();

    void deleteInvoice(Invoice pharmacy);

    void addInvoice(Invoice pharmacy);

    void onDestroy();

    interface View{
        void showInvoice(Cursor invoiceCursor);

        void showEmptyState(boolean show);

        void showMessage(int message);

        Context getContext();

        void showMessageDelete(Invoice invoice);

        ProgressDialog getProgressDialog();
    }
}
