package com.danielacedo.manageproductrecycler;

import android.app.Application;
import android.content.Context;

import com.danielacedo.manageproductrecycler.database.DatabaseHelper;
import com.danielacedo.manageproductrecycler.model.Product;
import com.danielacedo.manageproductrecycler.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel on 6/10/16.
 */

/**
 * Singleton application class where we place our objects available to all classes
 * @author Daniel Acedo Calderón
 */
public class ListProduct_Application extends Application {

    private User user;
    private static ListProduct_Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper.getInstance(this).open();
        application = this;
    }

    public static Context getContext(){
        return application;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
