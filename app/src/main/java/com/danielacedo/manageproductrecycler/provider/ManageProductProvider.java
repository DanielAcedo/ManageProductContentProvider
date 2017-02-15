package com.danielacedo.manageproductrecycler.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.db.DatabaseContract;
import com.danielacedo.manageproductrecycler.db.DatabaseHelper;

/**
 * Created by usuario on 6/02/17.
 */

public class ManageProductProvider extends ContentProvider {

    public static final int PRODUCT = 1;
    public static final int PRODUCT_ID = 2;
    public static final int CATEGORY = 3;
    public static final int CATEGORY_ID = 4;
    public static final int INVOICESTATUS = 5;
    public static final int INVOICESTATUS_ID = 6;
    public static final int PHARMACY = 7;
    public static final int PHARMACY_ID = 8;
    public static final int INVOICELINE = 9;
    public static final int INVOICELINE_ID = 10;
    public static final int INVOICE = 11;
    public static final int INVOICE_ID = 12;

    private SQLiteDatabase sqLiteDatabase;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.ProductEntry.CONTENT_PATH, PRODUCT);
        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.ProductEntry.CONTENT_PATH+"/#", PRODUCT_ID);

        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.CategoryEntry.CONTENT_PATH, CATEGORY);
        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.CategoryEntry.CONTENT_PATH+"/#", CATEGORY_ID);

        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceStatusEntry.CONTENT_PATH, INVOICESTATUS);
        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceStatusEntry.CONTENT_PATH+"/#", INVOICESTATUS_ID);

        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.PharmacyEntry.CONTENT_PATH, PHARMACY);
        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.PharmacyEntry.CONTENT_PATH+"/#", PHARMACY_ID);

        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceLineEntry.CONTENT_PATH, INVOICELINE);
        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceLineEntry.CONTENT_PATH+"/#", INVOICELINE_ID);

        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceEntry.CONTENT_PATH, INVOICE);
        uriMatcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceEntry.CONTENT_PATH+"/#", INVOICE_ID);
    }

    @Override
    public boolean onCreate() {
        sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)){
            case CATEGORY:
                sqLiteQueryBuilder.setTables(DatabaseContract.CategoryEntry.TABLE_NAME);

                if(sortOrder.isEmpty()){
                    sortOrder = DatabaseContract.CategoryEntry.DEFAULT_SORT;
                }

                break;
            case CATEGORY_ID:

                break;

            case PRODUCT:
                sqLiteQueryBuilder.setTables(DatabaseContract.ProductEntry.TABLE_NAME);
                sqLiteQueryBuilder.setProjectionMap(ManageProductContract.ProductEntry.sProductProjectionMap);

                if(sortOrder.isEmpty()){
                    sortOrder = DatabaseContract.ProductEntry.DEFAULT_SORT;
                }

                break;

            case PRODUCT_ID:

                break;

            case PHARMACY:
                sqLiteQueryBuilder.setTables(DatabaseContract.PharmacyEntry.TABLE_NAME);

                if(sortOrder.isEmpty()){
                    sortOrder = DatabaseContract.CategoryEntry.DEFAULT_SORT;
                }

                break;

            case INVOICE:
                sqLiteQueryBuilder.setTables(DatabaseContract.InvoiceEntry.INNER_JOINS);

                if(sortOrder.isEmpty()){
                    sortOrder = DatabaseContract.CategoryEntry.DEFAULT_SORT;
                }

                break;

            case UriMatcher.NO_MATCH:
                throw new IllegalArgumentException("Invalid URI");


        }

        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        String type = null;
        int match = uriMatcher.match(uri);

        switch (match){
            case CATEGORY:
                type = "vnd.android.cursor.dir/vnd.danielacedo.manageproductrecycler.category";
                break;

            case CATEGORY_ID:
                type = "vnd.android.cursor.dir/vnd.danielacedo.manageproductrecycler.category";
                break;

            case PRODUCT:
                type = "vnd.android.cursor.dir/vnd.danielacedo.manageproductrecycler.product";
                break;

            case PRODUCT_ID:
                type = "vnd.android.cursor.dir/vnd.danielacedo.manageproductrecycler.product";
                break;

            case PHARMACY:
                type = "vnd.android.cursor.dir/vnd.danielacedo.manageproductrecycler.pharmacy";
                break;

            case PHARMACY_ID:
                type = "vnd.android.cursor.dir/vnd.manageproductrecycler.pharmacy";
                break;

            case INVOICE:
                type = "vnd.android.cursor.dir/vnd.manageproductrecycler.invoice";
                break;
        }

        return type;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newUri = null;
        long id = -1;

        switch (uriMatcher.match(uri)){
            case CATEGORY:
                id = sqLiteDatabase.insert(DatabaseContract.CategoryEntry.TABLE_NAME, null, values);
                newUri = ContentUris.withAppendedId(uri, id);

                break;

            case PRODUCT:
                id = sqLiteDatabase.insert(DatabaseContract.ProductEntry.TABLE_NAME, null, values);
                newUri = ContentUris.withAppendedId(uri, id);

                break;

            case PHARMACY:
                id = sqLiteDatabase.insert(DatabaseContract.PharmacyEntry.TABLE_NAME, null, values);
                newUri = ContentUris.withAppendedId(uri, id);

                break;
        }

        if(id != -1){
            //Notify observers uri has been modified
            getContext().getContentResolver().notifyChange(newUri, null);
        }else{
            throw new SQLException(getContext().getResources().getString(R.string.error_insert));
        }

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = 0;

        switch (uriMatcher.match(uri)){
            case PRODUCT:
                result = sqLiteDatabase.delete(DatabaseContract.ProductEntry.TABLE_NAME,
                        selection, selectionArgs);

                break;

            case PHARMACY:
                result = sqLiteDatabase.delete(DatabaseContract.PharmacyEntry.TABLE_NAME,
                        selection, selectionArgs);

                break;
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int result = 0;
        String id;

        switch (uriMatcher.match(uri)){
            case PRODUCT:
                result = sqLiteDatabase.update(DatabaseContract.ProductEntry.TABLE_NAME,
                        values, selection, selectionArgs);
                break;

            case PRODUCT_ID:
                id = uri.getLastPathSegment();
                selection = ManageProductContract.ProductEntry._ID +" = ?";
                selectionArgs = new String[]{id};

                result = sqLiteDatabase.update(DatabaseContract.ProductEntry.TABLE_NAME,
                        values, selection, selectionArgs);

                break;

            case PHARMACY:
                result = sqLiteDatabase.update(DatabaseContract.PharmacyEntry.TABLE_NAME,
                        values, selection, selectionArgs);

                break;
        }

        if(result != 0){
            getContext().getContentResolver().notifyChange(
                    ContentUris.withAppendedId(uri, values.getAsInteger(ManageProductContract.ProductEntry._ID)),
                    null);
        }

        return result;
    }
}
