package com.danielacedo.manageproductrecycler.presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.danielacedo.manageproductrecycler.db.DatabaseContract;
import com.danielacedo.manageproductrecycler.interfaces.InvoicePresenter;
import com.danielacedo.manageproductrecycler.interfaces.PharmacyPresenter;
import com.danielacedo.manageproductrecycler.model.Invoice;
import com.danielacedo.manageproductrecycler.model.Pharmacy;
import com.danielacedo.manageproductrecycler.provider.ManageProductContract;

import static com.danielacedo.manageproductrecycler.provider.ManageProductProvider.PHARMACY;

/**
 * Created by usuario on 30/01/17.
 */

public class InvoicePresenterImpl implements InvoicePresenter, LoaderManager.LoaderCallbacks<Cursor> {
    private InvoicePresenter.View view;


    private Context context;
    private final static int INVOICE = 4;

    public InvoicePresenterImpl(View view){
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void loadInvoice() {
        Loader loader = ((Activity)context).getLoaderManager().getLoader(INVOICE);

        ProgressDialog dialog = view.getProgressDialog();
        dialog.setTitle("Cargando farmacias...");
        dialog.show();

        if(loader == null){
            ((Activity)context).getLoaderManager().initLoader(INVOICE, null, this);
        }else{
            ((Activity)context).getLoaderManager().restartLoader(INVOICE, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;

        switch (id){
            case INVOICE:
                loader = new CursorLoader(context, ManageProductContract.InvoiceEntry.CONTENT_URI,
                       ManageProductContract.InvoiceEntry.PROJECTION, null, null, null);
                break;
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.setNotificationUri(context.getContentResolver(), ManageProductContract.PharmacyEntry.CONTENT_URI);
        view.showInvoice(data);

        view.getProgressDialog().dismiss();
    }

    private ContentValues getContentInvoice(Invoice invoice){
        ContentValues contentValues = new ContentValues();

        contentValues.put(ManageProductContract.InvoiceEntry._ID, invoice.get_id());
        contentValues.put(ManageProductContract.InvoiceEntry.PHARMACY_ID, invoice.getId_pharmacy());
        contentValues.put(ManageProductContract.InvoiceEntry.STATUS_ID, invoice.getId_status());
        contentValues.put(ManageProductContract.InvoiceEntry.DATE, invoice.getDate().toString());

        return contentValues;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        view.showInvoice(null);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void deleteInvoice(Invoice invoice) {
        int result = context.getContentResolver().delete(ManageProductContract.InvoiceEntry.CONTENT_URI,
                ManageProductContract.InvoiceEntry._ID+"="+invoice.get_id(), null);

        if(result != 0){
            loadInvoice();
        }
    }

    @Override
    public void addInvoice(Invoice invoice) {
        context.getContentResolver().insert(ManageProductContract.InvoiceEntry.CONTENT_URI, getContentInvoice(invoice));
    }


}
