package com.danielacedo.manageproductrecycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielacedo.manageproductrecycler.ListProduct_Application;
import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Daniel on 18/11/16.
 */


/**
 * It isn't necessary to call notifyDataSetChanged() when we use ArrayAdapter's default add, delete... methods
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    private boolean isAlphabeticallyAscendant;

    /**
     * We pass a new ArrayList containing the objects from the repository to obtain a local copy
     * @param context
     */
    public ProductAdapter(Context context){
        super(context, R.layout.item_product, new ArrayList<Product>(((ListProduct_Application)context.getApplicationContext()).getProducts()
        ));

        isAlphabeticallyAscendant = false;
    }


    public void remove(int position) {
        Product product = getItem(position);
        remove(product);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ProductHolder holder = null;

        if(v == null){
            holder = new ProductHolder();

            v = LayoutInflater.from(getContext()).inflate(R.layout.item_product, null);

            holder.imv_listProduct_Image = (ImageView)v.findViewById(R.id.imv_listProduct_Image);
            holder.txv_listProduct_Name = (TextView)v.findViewById(R.id.txv_listProduct_Name);
            holder.txv_listProduct_Price = (TextView)v.findViewById(R.id.txv_listProduct_Price);
            holder.txv_listProduct_Stock = (TextView)v.findViewById(R.id.txv_listProduct_Stock);

            v.setTag(holder);

        }else{
            holder = (ProductHolder) v.getTag();
        }

        holder.imv_listProduct_Image.setImageResource(getItem(position).getImage());
        holder.txv_listProduct_Name.setText(getItem(position).getName());
        holder.txv_listProduct_Stock.setText(String.valueOf(getItem(position).getStock()));
        holder.txv_listProduct_Price.setText(String.valueOf(getItem(position).getPrice()));

        return v;
    }

    public void getAlphabeticallySortedProducts(){
        Comparator<Product> comparator = isAlphabeticallyAscendant ? Product.NAME_ASCENDANT_COMPARATOR
                : Product.NAME_DESCENDANT_COMPARATOR;

        isAlphabeticallyAscendant = !isAlphabeticallyAscendant;

        sort(comparator);
    }

    public void addProduct(Product product) {
        add(product);
    }

    public void editProduct(Product product) {
        int productPosition = searchProductById(product.getId());
        Product producto = getItem(productPosition);

        producto.setName(product.getName());
        producto.setDescription(product.getDescription());
        producto.setPrice(product.getPrice());
        producto.setBrand(product.getBrand());
        producto.setDosage(product.getDosage());
        producto.setStock(product.getStock());
        producto.setImage(product.getImage());

        notifyDataSetChanged();
    }

    public void removeById(String id){
        for(int i = 0; i < getCount(); i++){
            if(getItem(i).getId().equals(id)){
                Product product = getItem(i);
                remove(product);
                break;
            }
        }
    }

    private int searchProductById(String id){
        int position = -1;

        for(int i = 0; i < getCount(); i++){
            if(getItem(i).getId().equals(id)){
                position = i;
                break;
            }
        }

        return position;
    }

    public void updateProduct(List<Product> products){
        clear();
        addAll(products);
    }

    static class ProductHolder{
        ImageView imv_listProduct_Image;
        TextView txv_listProduct_Name, txv_listProduct_Price, txv_listProduct_Stock;
    }


}
