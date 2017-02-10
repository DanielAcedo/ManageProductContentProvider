package com.danielacedo.manageproductrecycler.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.danielacedo.manageproductrecycler.ListProduct_Application;
import com.danielacedo.manageproductrecycler.provider.ManageProductContract;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Daniel on 20/01/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ManageProduct.db";
    public static final int DATABASE_VERSION = 8;
    private static volatile DatabaseHelper databaseHelper;
    private AtomicInteger mOpenCounter;
    private SQLiteDatabase mDatabase;


    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mOpenCounter = new AtomicInteger();
    }

    public static DatabaseHelper getInstance(){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(ListProduct_Application.getContext());
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        try{
            db.execSQL(DatabaseContract.CategoryEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.CategoryEntry.SQL_INSERT_ENTRIES);
            db.execSQL(DatabaseContract.ProductEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.PharmacyEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.PharmacyEntry.SQL_INSERT_ENTRIES);
            db.execSQL(DatabaseContract.InvoiceStatusEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.InvoiceEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.InvoiceLineEntry.SQL_CREATE_ENTRIES);
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e){
            Log.e("sqliteDatabase", "Error al crear la base de datos: "+e.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();

        try{
            db.execSQL(DatabaseContract.InvoiceLineEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.InvoiceEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.InvoiceStatusEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.ProductEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.CategoryEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.PharmacyEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e){
            Log.e("sqliteDatabase", "Error al actualizar la base de datos: "+e.getMessage());
        }
        finally {
            db.endTransaction();
        }
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

    public synchronized SQLiteDatabase openDatabase(){
        if(mOpenCounter.incrementAndGet() == 1){
            mDatabase = getWritableDatabase();
        }

        return mDatabase;
    }

    public synchronized void closeDatabase(){
        if(mOpenCounter.decrementAndGet() == 0){
            mDatabase.close();
        }
    }
}
