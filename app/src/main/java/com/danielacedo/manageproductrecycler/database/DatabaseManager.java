package com.danielacedo.manageproductrecycler.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.support.v4.app.AppLaunchChecker;
import android.util.Log;

import com.danielacedo.manageproductrecycler.ListProduct_Application;
import com.danielacedo.manageproductrecycler.model.Pharmacy;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 23/01/17.
 */

/**
 * Class used for defining operations dealing with the database
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


    // PRODUCT TABLE

    /**
     * Get all products from the database
     * @return List of products
     */
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

        cursor.close();

        //Show in log union between product and category tables
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(ManageProductContract.ProductEntry.PRODUCT_JOIN_CATEGORY);
        cursor = builder.query(sqLiteDatabase, ManageProductContract.ProductEntry.COLUMNS_PRODUCT_JOIN_CATEGORY, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do {
                String product = cursor.getString(0);
                String description = cursor.getString(1);
                String category = cursor.getString(2);
                Log.e("ProductJOIN", product+", "+description+", "+category);
            }while (cursor.moveToNext());
        }

        cursor.close();

        DatabaseHelper.getInstance(ListProduct_Application.getContext()).closeDatabase();

        return products;
    }

    public void updateProduct(Product p){

    }

    /**
     * Deletes a product from the database
     * @param p Product to be deleted
     */
    public void deleteProduct(Product p){
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(ListProduct_Application.getContext()).openDatabase();

        String where = "? = ?";
        String[] whereArgs = new String[]{BaseColumns._ID , String.valueOf(p.getId())};
        sqLiteDatabase.delete(ManageProductContract.ProductEntry.TABLE_NAME, where, whereArgs);

        DatabaseHelper.getInstance(ListProduct_Application.getContext()).closeDatabase();
    }

    /**
     * Adds a product to the database
     * @param p Product to be added
     */
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
        values.put(ManageProductContract.ProductEntry.COLUMN_IDCATEGORY, p.getId_category());

        try{
            sqLiteDatabase.insertOrThrow(ManageProductContract.ProductEntry.TABLE_NAME, null, values);
        }catch(SQLException e){
            e.getMessage();
        }

        DatabaseHelper.getInstance(ListProduct_Application.getContext()).closeDatabase();
    }

    //CATEGORY METHOD
    public Cursor getAllCategories(){
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(ListProduct_Application.getContext()).openDatabase();
        Cursor cursor = sqLiteDatabase.query(ManageProductContract.CategoryEntry.TABLE_NAME, ManageProductContract.CategoryEntry.SQL_ALL_COLUMN, null, null, null ,null, null);


        return cursor;
    }

    //PHARMACY METHODS
    public Cursor getAllPharmacy(){
        SQLiteDatabase db = DatabaseHelper.getInstance(ListProduct_Application.getContext()).openDatabase();
        Cursor cursor = db.query(ManageProductContract.PharmacyEntry.TABLE_NAME, ManageProductContract.PharmacyEntry.SQL_ALL_COLUMN, null, null, null, null, null);

        return cursor;
    }
}
