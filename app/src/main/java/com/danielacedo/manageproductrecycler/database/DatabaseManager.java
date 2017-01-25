package com.danielacedo.manageproductrecycler.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.danielacedo.manageproductrecycler.ListProduct_Application;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.ArrayList;
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
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(ListProduct_Application.getContext()).openDatabase();
        Cursor cursor = sqLiteDatabase.query(ManageProductContract.ProductEntry.TABLE_NAME, ManageProductContract.ProductEntry.SQL_ALLCOLUMNS,null, null, null, null, null);
        List<Product> products = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String brand = cursor.getString(3);
                String dosage = cursor.getString(4);
                double price = cursor.getDouble(5);
                int stock = cursor.getInt(6);
                int image = cursor.getInt(7);
                int id_category = cursor.getInt(8);
                products.add(new Product(id, name, description, price, brand, dosage, stock, image, id_category));
            }while(cursor.moveToNext());
        }

        DatabaseHelper.getInstance(ListProduct_Application.getContext()).closeDatabase();

        return products;
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

        try{
            sqLiteDatabase.insertOrThrow(ManageProductContract.ProductEntry.TABLE_NAME, null, values);
        }catch(SQLException e){
            e.getMessage();
        }

        DatabaseHelper.getInstance(ListProduct_Application.getContext()).closeDatabase();
    }
}
