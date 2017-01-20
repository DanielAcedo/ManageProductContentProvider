package com.danielacedo.manageproductrecycler;

import android.app.Application;

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
    private List<Product> productList;

    @Override
    public void onCreate() {
        super.onCreate();
        productList = new ArrayList<Product>();
        initializeProducts();
        DatabaseHelper.getInstance(this).open();

    }

    private void initializeProducts(){
        saveProduct(new Product("Dalsy", "La panacea tal cual", 12.50, "DalsyCorp", "250ml", 5, R.drawable.dalsy_logo));
        saveProduct(new Product("Neuropocina", "Elimina el rechazo a implantes cibernéticos", 5000, "VersaLife", "50ml", 2, R.drawable.neuropozyne));
        saveProduct(new Product("Paracetamol", "La pastilla antitodo", 7, "Bayer", "1g", 50, R.drawable.paracetamol));
        saveProduct(new Product("Weed", "Smoke everyday", 5, "SnoopDog", "50mg", 420, R.drawable.weed));
        saveProduct(new Product("ADAM", "El lienzo del ADN", 500, "Fontaine Futuristics", "100ml", 9, R.drawable.adam));
        saveProduct(new Product("Virus-T", "¿Qué podría ir mal?", 25000, "Umbrella Corp.", "1", 1, R.drawable.tvirus));
        saveProduct(new Product("Dalsy", "La panacea tal cual", 12.50, "DalsyCorp", "250ml", 5, R.drawable.dalsy_logo));
        saveProduct(new Product("Neuropocina", "Elimina el rechazo a implantes cibernéticos", 5000, "VersaLife", "50ml", 2, R.drawable.neuropozyne));
        saveProduct(new Product("Paracetamol", "La pastilla antitodo", 7, "Bayer", "1g", 50, R.drawable.paracetamol));
        saveProduct(new Product("Weed", "Smoke everyday", 5, "SnoopDog", "50mg", 420, R.drawable.weed));
        saveProduct(new Product("ADAM", "El lienzo del ADN", 500, "Fontaine Futuristics", "100ml", 9, R.drawable.adam));
        saveProduct(new Product("Virus-T", "¿Qué podría ir mal?", 25000, "Umbrella Corp.", "1", 1, R.drawable.tvirus));
        saveProduct(new Product("Dalsy", "La panacea tal cual", 12.50, "DalsyCorp", "250ml", 5, R.drawable.dalsy_logo));
        saveProduct(new Product("Neuropocina", "Elimina el rechazo a implantes cibernéticos", 5000, "VersaLife", "50ml", 2, R.drawable.neuropozyne));
        saveProduct(new Product("Paracetamol", "La pastilla antitodo", 7, "Bayer", "1g", 50, R.drawable.paracetamol));
        saveProduct(new Product("Weed", "Smoke everyday", 5, "SnoopDog", "50mg", 420, R.drawable.weed));
        saveProduct(new Product("ADAM", "El lienzo del ADN", 500, "Fontaine Futuristics", "100ml", 9, R.drawable.adam));
        saveProduct(new Product("Virus-T", "¿Qué podría ir mal?", 25000, "Umbrella Corp.", "1", 1, R.drawable.tvirus));
    }

    public void saveProduct(Product product){
        productList.add(product);
    }

    public List<Product> getProducts(){
        //Collections.sort(productList, (o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice())); //Lambda expression example
        return productList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
