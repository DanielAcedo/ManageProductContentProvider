package com.danielacedo.manageproductrecycler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.database.DatabaseManager;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 21/10/16.
 */

/**
 * Custom adapter for displaying Products using a custom View. Correct solution.
 * @author Daniel Acedo Calderón
 */
public class ProductAdapterC extends ArrayAdapter<Product> {

    public ProductAdapterC(Context context) {
        super(context, R.layout.item_product, new ArrayList<Product>(DatabaseManager.getInstance().getAllProducts()));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ProductHolder productHolder;

        if(item == null){
            //1. Create the LayoutInflater
            //LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            productHolder = new ProductHolder();

            //2. Inflate the view. Create in memory the View object that contains the widgets
            item = layoutInflater.inflate(R.layout.item_product, null);
            //3. Search for the widgets in the just created view
            productHolder.imv_listProduct_Image = (ImageView) item.findViewById(R.id.imv_listProduct_Image);
            productHolder.txv_listProduct_Name = (TextView) item.findViewById(R.id.txv_listProduct_Name);
            productHolder.txv_listProduct_Price = (TextView) item.findViewById(R.id.txv_listProduct_Price);
            productHolder.txv_listProduct_Stock = (TextView) item.findViewById(R.id.txv_listProduct_Stock);

            item.setTag(productHolder);
        }else{
            productHolder = (ProductHolder)item.getTag();
        }

        //4. Assign display info to widgets
        productHolder.imv_listProduct_Image.setImageResource((int)getItem(position).getImage());
        productHolder.txv_listProduct_Name.setText(getItem(position).getName());
        productHolder.txv_listProduct_Price.setText(String.valueOf(getItem(position).getPrice()));
        productHolder.txv_listProduct_Stock.setText(String.valueOf(getItem(position).getStock()));

        if(position%2==0){
            item.setBackgroundColor(Color.RED);
        }else{
            item.setBackgroundColor(Color.WHITE);
        }

        return item;

    }


    public void getAllProducts(List<Product> productList){

    }

    /**
     * Inner class that contains the widgets from the XML file
     * @author Daniel Acedo Calderón
     */
    static class ProductHolder{
        ImageView imv_listProduct_Image;
        TextView txv_listProduct_Name, txv_listProduct_Price, txv_listProduct_Stock;
    }
}
