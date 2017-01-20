package com.danielacedo.manageproductrecycler.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Daniel on 20/01/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ManageProduct.db";
    public static final int DATABASE_VERSION = 3;
    private static volatile DatabaseHelper databaseHelper;


    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static DatabaseHelper getInstance(Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context.getApplicationContext());
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ManageProductContract.CategoryEntry.SQL_CREATE_ENTRIES);
        db.execSQL(ManageProductContract.ProductEntry.SQL_CREATE_ENTRIES);
        db.execSQL(ManageProductContract.PharmacyEntry.SQL_CREATE_ENTRIES);
        db.execSQL(ManageProductContract.InvoiceStatusEntry.SQL_CREATE_ENTRIES);
        db.execSQL(ManageProductContract.InvoiceEntry.SQL_CREATE_ENTRIES);
        db.execSQL(ManageProductContract.InvoiceLineEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ManageProductContract.InvoiceLineEntry.SQL_DELETE_ENTRIES);
        db.execSQL(ManageProductContract.InvoiceEntry.SQL_DELETE_ENTRIES);
        db.execSQL(ManageProductContract.InvoiceStatusEntry.SQL_DELETE_ENTRIES);
        db.execSQL(ManageProductContract.ProductEntry.SQL_DELETE_ENTRIES);
        db.execSQL(ManageProductContract.CategoryEntry.SQL_DELETE_ENTRIES);
        db.execSQL(ManageProductContract.PharmacyEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, newVersion, oldVersion);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onConfigure(db);

        if(!db.isReadOnly()) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                db.setForeignKeyConstraintsEnabled(true);
            else
                db.execSQL("PRAGMA foreign_keys = ON");
        }
    }

    public SQLiteDatabase open(){
        return getWritableDatabase();
    }
}
