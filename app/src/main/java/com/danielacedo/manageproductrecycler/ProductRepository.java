package com.danielacedo.manageproductrecycler;

import com.danielacedo.manageproductrecycler.interfaces.IRepository;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 9/12/16.
 */

public class ProductRepository implements IRepository {
    private List<Product> productList;

    private static ProductRepository repository;


    @Override
    public Product getProductById(String id) {
        return null;
    }

    @Override
    public void deleteProduct(Product product) {

    }

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public void updateProduct(Product product) {

    }


    private ProductRepository(){
        productList = new ArrayList<Product>();
        initializeProducts();
    }

    public static ProductRepository getInstance(){
        if(repository == null){
            repository = new ProductRepository();
        }

        return repository;
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
}
