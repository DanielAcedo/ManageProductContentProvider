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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.model.Pharmacy;
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

public class PharmacyAdapter extends CursorAdapter {

    private boolean isAlphabeticallyAscendant;

    public PharmacyAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public Pharmacy getItem(int position) {
        getCursor().moveToPosition(position);

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(getCursor().getInt(0));
        pharmacy.setCif(getCursor().getString(1));
        pharmacy.setAddress(getCursor().getString(2));
        pharmacy.setTelephone_number(getCursor().getString(3));
        pharmacy.setEmail(getCursor().getString(4));

        return pharmacy;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_pharmacy, null);

        PharmacyHolder holder = new PharmacyHolder();
        holder.txv_cif = (TextView) rootView.findViewById(R.id.txv_pharmacy_cif);
        holder.txv_address = (TextView)rootView.findViewById(R.id.txv_pharmacy_address);
        holder.txv_phone = (TextView)rootView.findViewById(R.id.txv_pharmacy_phone);
        holder.txv_email = (TextView)rootView.findViewById(R.id.txv_pharmacy_email);

        rootView.setTag(holder);

        return rootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        PharmacyHolder holder = (PharmacyHolder)view.getTag();
        holder.txv_cif.setText(getItem(cursor.getPosition()).getCif());
        holder.txv_address.setText(getItem(cursor.getPosition()).getAddress());
        holder.txv_phone.setText(getItem(cursor.getPosition()).getTelephone_number());
        holder.txv_email.setText(getItem(cursor.getPosition()).getEmail());

    }

    public void updatePharmacy(Cursor cursor){
        changeCursor(cursor);
    }

    static class PharmacyHolder {
        TextView txv_cif;
        TextView txv_address;
        TextView txv_phone;
        TextView txv_email;
    }


}
