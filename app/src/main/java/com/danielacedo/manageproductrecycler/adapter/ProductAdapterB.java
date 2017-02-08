package com.danielacedo.manageproductrecycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.db.DatabaseManager;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.ArrayList;

/**
 * Created by Daniel on 21/10/16.
 */

/**
 * Custom adapter for displaying Products using a custom View. A little more efficient than A
 * @author Daniel Acedo Calderón
 */
public class ProductAdapterB extends ArrayAdapter<Product> {

    public ProductAdapterB(Context context) {
        super(context, R.layout.item_product, new ArrayList<Product>(DatabaseManager.getInstance().getAllProducts()));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imv_listProduct_Image;
        TextView txv_listProduct_Name, txv_listProduct_Price, txv_listProduct_Stock;
        View item = convertView;

        //We take advantage of the already available convertView and if it's not null, we use it instead of inflating another one
        if (item == null){
            //1. Create the LayoutInflater
            //LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //2. Inflate the view. Create in memory the View object that contains the widgets
            item = layoutInflater.inflate(R.layout.item_product, null);
        }


        //3. Search for the widgets in the just created view
        imv_listProduct_Image = (ImageView)item.findViewById(R.id.imv_listProduct_Image);
        txv_listProduct_Name = (TextView)item.findViewById(R.id.txv_listProduct_Name);
        txv_listProduct_Price = (TextView)item.findViewById(R.id.txv_listProduct_Price);
        txv_listProduct_Stock = (TextView)item.findViewById(R.id.txv_listProduct_Stock);

        //4. Assign display info to widgets
        imv_listProduct_Image.setImageResource((int)getItem(position).getImage());
        txv_listProduct_Name.setText(getItem(position).getName());
        txv_listProduct_Price.setText(String.valueOf(getItem(position).getPrice()));
        txv_listProduct_Stock.setText(String.valueOf(getItem(position).getStock()));

        return item;
    }
}
