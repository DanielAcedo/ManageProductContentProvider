package com.danielacedo.manageproductrecycler.db;

/**
 * Created by usuario on 20/01/17.
 */

import android.net.Uri;
import android.provider.BaseColumns;

import static android.graphics.BlurMaskFilter.Blur.INNER;
import static android.webkit.WebSettings.PluginState.ON;

/**
 * Class that stores database's schema containing all table fields
 */
public final class DatabaseContract {

    private DatabaseContract(){}

    public static class CategoryEntry implements BaseColumns{
        public static final String CONTENT_PATH = "category";
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME = "name";
        public static final String DEFAULT_SORT = COLUMN_NAME;
        public static final String SQL_CREATE_ENTRIES = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "%s TEXT NOT NULL)",
                TABLE_NAME, BaseColumns._ID, COLUMN_NAME);

        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);

        public static final String SQL_INSERT_ENTRIES = String.format("INSERT INTO %s (%s) VALUES ('%s'), ('%s'), ('%s'), ('%s')", TABLE_NAME, COLUMN_NAME, "Medicina", "Homeopatia", "Jarabes", "Infancia");

        public static final String[] SQL_ALL_COLUMN = {BaseColumns._ID, COLUMN_NAME};
    }

    public static class ProductEntry implements BaseColumns{
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_DOSAGE = "dosage";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_STOCK = "stock";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_IDCATEGORY = "id_category";
        public static final String REFERENCE_ID_CATEGORY = String.format("REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT", CategoryEntry.TABLE_NAME, BaseColumns._ID);

        public static final String DEFAULT_SORT = COLUMN_NAME;

        public static final String PRODUCT_JOIN_CATEGORY = String.format("%s INNER JOIN %s ON %s= %s", TABLE_NAME, CategoryEntry.TABLE_NAME,
                TABLE_NAME+"."+BaseColumns._ID,
                CategoryEntry.TABLE_NAME+"."+BaseColumns._ID);

        public static final String[] COLUMNS_PRODUCT_JOIN_CATEGORY = {
                TABLE_NAME+"."+COLUMN_NAME,
                COLUMN_DESCRIPTION,
                CategoryEntry.TABLE_NAME+"."+ CategoryEntry.COLUMN_NAME
        };


        public static final String SQL_CREATE_ENTRIES = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, "+
                        "%s TEXT NOT NULL, "+
                        "%s TEXT NOT NULL, "+
                        "%s REAL NOT NULL, "+
                        "%s INTEGER NOT NULL, "+
                        "%s BLOB NOT NULL, "+
                        "%s INTEGER NOT NULL %s)",
                TABLE_NAME, BaseColumns._ID,
                COLUMN_NAME,
                COLUMN_DESCRIPTION,
                COLUMN_BRAND,
                COLUMN_DOSAGE,
                COLUMN_PRICE,
                COLUMN_STOCK,
                COLUMN_IMAGE,
                COLUMN_IDCATEGORY,
                REFERENCE_ID_CATEGORY);

        public static final String[] SQL_ALLCOLUMNS = {BaseColumns._ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_BRAND,
                COLUMN_DOSAGE, COLUMN_PRICE, COLUMN_STOCK, COLUMN_IMAGE, COLUMN_IDCATEGORY};

        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }

    public static class PharmacyEntry implements BaseColumns{
        public static final String TABLE_NAME = "pharmacy";
        public static final String COLUMN_CIF = "cif";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_TELEPHONENUMBER = "telephone_number";
        public static final String COLUMN_EMAIL = "email";

        public static final String DEFAULT_SORT = COLUMN_CIF;


        public static final String SQL_CREATE_ENTRIES = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, "+
                        "%s TEXT NOT NULL, "+
                        "%s TEXT NOT NULL)",
                TABLE_NAME, BaseColumns._ID,
                COLUMN_CIF,
                COLUMN_ADDRESS,
                COLUMN_TELEPHONENUMBER,
                COLUMN_EMAIL);

        public static final String SQL_INSERT_ENTRIES = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', '%s')", TABLE_NAME,
                COLUMN_CIF, COLUMN_ADDRESS, COLUMN_TELEPHONENUMBER, COLUMN_EMAIL,
                "Umbrella Corporation", "Raccoon City", "666666666", "UmbrellaCorp@gmail.com");

        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        public static final String[] SQL_ALL_COLUMN = {BaseColumns._ID, COLUMN_CIF, COLUMN_ADDRESS, COLUMN_TELEPHONENUMBER, COLUMN_EMAIL};
    }

    public static class InvoiceStatusEntry implements BaseColumns{
        public static final String TABLE_NAME = "invoicestatus";
        public static final String COLUMN_NAME = "name";

        public static final String SQL_CREATE_ENTRIES = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        "%s TEXT NOT NULL)",
                TABLE_NAME, BaseColumns._ID,
                COLUMN_NAME);

        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }

    public static class InvoiceEntry implements BaseColumns{
        public static final String TABLE_NAME = "invoice";
        public static final String COLUMN_IDPHARMACY = "id_pharmacy";
        public static final String COLUMN_IDSTATUS = "id_status";
        public static final String COLUMN_DATE = "date";
        public static final String REFERENCE_ID_PHARMACY = String.format("REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT", PharmacyEntry.TABLE_NAME, BaseColumns._ID);
        public static final String REFERENCE_ID_STATUS = String.format("REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT", InvoiceStatusEntry.TABLE_NAME, BaseColumns._ID);

        public static final String INVOICE_JOIN_PHARMACY = String.format("INNER JOIN %s ON %s = %s",
                PharmacyEntry.TABLE_NAME,
                PharmacyEntry.TABLE_NAME+"."+PharmacyEntry._ID,
                TABLE_NAME+"."+_ID
                );

        public static final String INVOICE_JOIN_STATUS = String.format("INNER JOIN %s ON %s = %s",
                InvoiceStatusEntry.TABLE_NAME,
                InvoiceStatusEntry.TABLE_NAME+"."+InvoiceStatusEntry._ID,
                TABLE_NAME+"."+_ID
        );

        public static final String INNER_JOINS = TABLE_NAME+" "+INVOICE_JOIN_PHARMACY+" "+INVOICE_JOIN_STATUS;

        public static final String SQL_CREATE_ENTRIES = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "%s INT NOT NULL %s, " +
                        "%s INT NOT NULL %s, " +
                        "%s TEXT NOT NULL)",
                TABLE_NAME, BaseColumns._ID,
                COLUMN_IDPHARMACY, REFERENCE_ID_PHARMACY,
                COLUMN_IDSTATUS, REFERENCE_ID_STATUS,
                COLUMN_DATE);

        public static final String[] PROJECTION = {
                PharmacyEntry.TABLE_NAME+"."+PharmacyEntry.COLUMN_CIF,
                InvoiceStatusEntry.TABLE_NAME+"."+InvoiceStatusEntry.COLUMN_NAME,
                COLUMN_DATE
        };


        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }

    public static class InvoiceLineEntry implements BaseColumns{
        public static final String TABLE_NAME = "invoiceline";
        public static final String COLUMN_IDINVOICE = "id_invoice";
        public static final String COLUMN_ORDERPRODUCT = "orderproduct";
        public static final String COLUMN_IDPRODUCT = "id_product";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_PRICE = "id_price";
        public static final String REFERENCE_ID_INVOICE = String.format("REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT", InvoiceEntry.TABLE_NAME, BaseColumns._ID);
        public static final String REFERENCE_ID_PRODUCT = String.format("REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT", ProductEntry.TABLE_NAME, BaseColumns._ID);


        public static final String SQL_CREATE_ENTRIES = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "%s INT NOT NULL %s, " +
                        "%s INT NOT NULL, " +
                        "%s INT NOT NULL %s, "+
                        "%s INT NOT NULL ,"+
                        "%s REAL NOT NULL)",
                TABLE_NAME, BaseColumns._ID,
                COLUMN_IDINVOICE, REFERENCE_ID_INVOICE,
                COLUMN_ORDERPRODUCT,
                COLUMN_IDPRODUCT, REFERENCE_ID_PRODUCT,
                COLUMN_AMOUNT,
                COLUMN_PRICE);

        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }



}
