package com.danielacedo.manageproductrecycler.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.UUID;

/**
 * Created by Daniel on 19/10/16.
 */

/**
 * Model class representing a pharmaceutical product
 * @author Daniel Acedo Calderón
 */
public class Product implements Comparable<Product>, Parcelable{
    private int id;
    private String name;
    private String description;
    private String brand;
    private String dosage;
    private int id_category;
    private double price;
    private int stock;
    private Bitmap image;


    public static final Comparator<Product> PRICE_COMPARATOR = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    };

    public static final Comparator<Product> STOCK_COMPARATOR = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return Double.compare(o1.getStock(), o2.getStock());
        }
    };

    public static final Comparator<Product> NAME_ASCENDANT_COMPARATOR =
            new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };

    public static final Comparator<Product> NAME_DESCENDANT_COMPARATOR = new Comparator<Product>(){
        @Override
        public int compare(Product o1, Product o2) {
            return -1 * o1.getName().compareTo(o2.getName());
        }
    };

    public Product(String name, String description, double price, String brand, String dosage, int stock, Bitmap image, int id_category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.dosage = dosage;
        this.stock = stock;
        this.image = image;
        this.id_category = id_category;
    }

    public Product(int id, String name, String description, double price, String brand, String dosage, int stock, Bitmap image, int id_category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.dosage = dosage;
        this.stock = stock;
        this.image = image;
        this.id_category = id_category;
    }

    public Product(){}

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        brand = in.readString();
        dosage = in.readString();
        id_category = in.readInt();
        price = in.readDouble();
        stock = in.readInt();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(brand);
        dest.writeString(dosage);
        dest.writeInt(id_category);
        dest.writeDouble(price);
        dest.writeInt(stock);
        dest.writeParcelable(image, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    @Override
    public String toString() {
        return name+": "+description;
    }

    @Override
    public boolean equals(Object o) {

        if(o instanceof Product){ //Check if the Object shares the same type
            Product product = (Product)o;
            if(this.name.equals(product.getName()) && this.brand.equals(product.getBrand()) && this.dosage.equals(product.getDosage())){ //If name, brand, and concentration are equals, then the objects are considered the same
                return true;
            }
        }

        return false;
    }

    @Override
    public int compareTo(Product o) {
        if (this.getName().compareTo(o.getName()) == 0){
            return this.getBrand().compareTo(o.getBrand());
        }else{
            return this.getName().compareTo(o.getName());
        }
    }

}
