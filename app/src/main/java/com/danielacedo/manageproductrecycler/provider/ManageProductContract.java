package com.danielacedo.manageproductrecycler.provider;

/**
 * Created by usuario on 20/01/17.
 */

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Class that stores database's schema containing all table fields
 */
public final class ManageProductContract {

    public static final String AUTHORITY = "com.danielacedo.manageproductrecycler";
    public static final Uri AUTHORITY_URI = Uri.parse("content://"+AUTHORITY);

    private ManageProductContract(){}

    public static class CategoryEntry implements BaseColumns{
        public static final String CONTENT_PATH = "category";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String NAME = "name";
        public static final String[] PROJECTION = new String[] {BaseColumns._ID, NAME};
    }

    public static class ProductEntry implements BaseColumns{
        public static final String CONTENT_PATH = "product";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String BRAND = "brand";
        public static final String DOSAGE = "dosage";
        public static final String PRICE = "price";
        public static final String STOCK = "stock";
        public static final String IMAGE = "image";
        public static final String CATEGORY_ID = "category_id";
        public static final String[] PROJECTION = new String[] {BaseColumns._ID, NAME, DESCRIPTION, BRAND, DOSAGE, PRICE, STOCK, IMAGE, CATEGORY_ID};
    }

    public static class PharmacyEntry implements BaseColumns{
        public static final String CONTENT_PATH = "pharmacy";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String CIF= "cif";
        public static final String ADDRESS = "address";
        public static final String TELEPHONENUMBER = "telephone_number";
        public static final String EMAIL = "email";
        public static final String[] PROJECTION = new String[] {BaseColumns._ID, CIF, ADDRESS, TELEPHONENUMBER, EMAIL};
    }

    public static class InvoiceStatusEntry implements BaseColumns{
        public static final String CONTENT_PATH = "invoicestatus";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String NAME = "name";

        public static final String[] PROJECTION = new String[] {BaseColumns._ID, NAME};
    }

    public static class InvoiceEntry implements BaseColumns{
        public static final String CONTENT_PATH = "invoice";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String PHARMACY_ID = "id_pharmacy";
        public static final String STATUS_ID = "id_status";
        public static final String DATE = "date";

        public static final String[] PROJECTION = new String[] {BaseColumns._ID, PHARMACY_ID, STATUS_ID, DATE};
    }

    public static class InvoiceLineEntry implements BaseColumns{
        public static final String CONTENT_PATH = "invoiceline";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String IDINVOICE = "id_invoice";
        public static final String ORDERPRODUCT = "orderproduct";
        public static final String PRODUCT_ID = "product_id";
        public static final String AMOUNT = "amount";
        public static final String PRICE = "id_price";

        public static final String[] PROJECTION = new String[] {BaseColumns._ID, IDINVOICE, ORDERPRODUCT, PRODUCT_ID, AMOUNT, PRICE};
    }



}
