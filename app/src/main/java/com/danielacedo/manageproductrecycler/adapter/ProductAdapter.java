package com.danielacedo.manageproductrecycler.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.model.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Daniel on 18/11/16.
 */


/**
 * It isn't necessary to call notifyDataSetChanged() when we use ArrayAdapter's default add, delete... methods
 */

public class ProductAdapter extends CursorAdapter {

    private boolean isAlphabeticallyAscendant;

    public ProductAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public Product getItem(int position) {
        getCursor().moveToPosition(position);

        Product product = new Product();
        product.setId(getCursor().getInt(0));
        product.setName(getCursor().getString(1));
        product.setDescription(getCursor().getString(2));
        product.setBrand(getCursor().getString(3));
        product.setDosage(getCursor().getString(4));
        product.setPrice(getCursor().getDouble(5));
        product.setStock(getCursor().getInt(6));
        product.setImage(Long.parseLong(getCursor().getString(7)));
        product.setId_category(getCursor().getInt(8));

        return product;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_product, null);

        ProductHolder holder = new ProductHolder();
        holder.txv_listProduct_Name = (TextView)rootView.findViewById(R.id.txv_listProduct_Name);
        holder.txv_listProduct_Price = (TextView)rootView.findViewById(R.id.txv_listProduct_Price);
        holder.txv_listProduct_Stock = (TextView)rootView.findViewById(R.id.txv_listProduct_Stock);
        holder.imv_listProduct_Image = (ImageView)rootView.findViewById(R.id.imv_listProduct_Image);

        rootView.setTag(holder);

        return rootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ProductHolder holder = (ProductHolder)view.getTag();

        holder.txv_listProduct_Name.setText(getItem(cursor.getPosition()).getName());
        holder.txv_listProduct_Price.setText(String.valueOf(getItem(cursor.getPosition()).getPrice()));
        holder.txv_listProduct_Stock.setText(String.valueOf(getItem(cursor.getPosition()).getStock()));
        holder.imv_listProduct_Image.setImageResource((int)getItem(cursor.getPosition()).getImage());
    }

    static class ProductHolder{
        ImageView imv_listProduct_Image;
        TextView txv_listProduct_Name, txv_listProduct_Price, txv_listProduct_Stock;
    }
}
