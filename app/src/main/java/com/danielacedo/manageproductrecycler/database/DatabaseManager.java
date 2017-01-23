package com.danielacedo.manageproductrecycler.database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.danielacedo.manageproductrecycler.ListProduct_Application;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.List;

/**
 * Created by usuario on 23/01/17.
 */

public class DatabaseManager {
    private static DatabaseManager databaseManager;

    private DatabaseManager(){
    }

    public static DatabaseManager getInstance(){
        if(databaseManager == null){
            databaseManager = new DatabaseManager();
        }

        return databaseManager;
    }

    public List<Product> getAllProducts(){

        return null;
    }

    public void updateProduct(Product p){

    }

    public void deleteProduct(Product p){
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(ListProduct_Application.getContext()).openDatabase();

        sqLiteDatabase.delete(ManageProductContract.ProductEntry.TABLE_NAME, "? = ?", new String[]{BaseColumns._ID , p.getId()});

        DatabaseHelper.getInstance(ListProduct_Application.getContext()).closeDatabase();
    }

    public void addProduct(Product p){
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(ListProduct_Application.getContext()).openDatabase();

        ContentValues values = new ContentValues();

        values.put(ManageProductContract.ProductEntry.COLUMN_NAME, p.getName());
        values.put(ManageProductContract.ProductEntry.COLUMN_DESCRIPTION, p.getDescription());
        values.put(ManageProductContract.ProductEntry.COLUMN_BRAND, p.getBrand());
        values.put(ManageProductContract.ProductEntry.COLUMN_DOSAGE, p.getDosage());
        values.put(ManageProductContract.ProductEntry.COLUMN_PRICE, p.getPrice());
        values.put(ManageProductContract.ProductEntry.COLUMN_STOCK, p.getStock());
        values.put(ManageProductContract.ProductEntry.COLUMN_IMAGE, p.getImage());
        values.put(ManageProductContract.ProductEntry.COLUMN_IDCATEGORY, 1);

        sqLiteDatabase.insert(ManageProductContract.ProductEntry.TABLE_NAME, null, values);

        DatabaseHelper.getInstance(ListProduct_Application.getContext()).closeDatabase();
    }
}
