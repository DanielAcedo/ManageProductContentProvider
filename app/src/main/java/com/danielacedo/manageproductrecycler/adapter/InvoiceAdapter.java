package com.danielacedo.manageproductrecycler.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.model.Invoice;
import com.danielacedo.manageproductrecycler.model.InvoiceView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel on 18/11/16.
 */


/**
 * It isn't necessary to call notifyDataSetChanged() when we use ArrayAdapter's default add, delete... methods
 */

public class InvoiceAdapter extends CursorAdapter {

    private boolean isAlphabeticallyAscendant;

    public InvoiceAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public InvoiceView getItem(int position) {
        getCursor().moveToPosition(position);

        InvoiceView invoice = new InvoiceView();
        invoice.set_id(getCursor().getInt(0));
        invoice.setId_pharmacy(getCursor().getInt(1));
        invoice.setPharmacy(getCursor().getString(2));
        invoice.setId_status(getCursor().getInt(3));
        invoice.setStatus(getCursor().getString(4));
        Calendar calendarTmp = Calendar.getInstance();
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            calendarTmp.setTime(format.parse(getCursor().getString(5)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        invoice.setDate(calendarTmp);


        return invoice;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_invoice, null);

        InvoiceHolder holder = new InvoiceHolder();
        holder.txv_pharmacy = (TextView) rootView.findViewById(R.id.txv_pharmacy);
        holder.txv_status = (TextView)rootView.findViewById(R.id.txv_status);
        holder.txv_date = (TextView)rootView.findViewById(R.id.txv_date);

        rootView.setTag(holder);

        return rootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        InvoiceHolder holder = (InvoiceHolder)view.getTag();
        InvoiceView invoice = getItem(cursor.getPosition());

        holder.txv_pharmacy.setText(invoice.getPharmacy());
        holder.txv_status.setText(invoice.getStatus());

        String format = invoice.getDate().get(Calendar.DAY_OF_MONTH)+"/"+String.format("%02d",invoice.getDate().get(Calendar.MONTH)+1)+"/"+invoice.getDate().get(Calendar.YEAR);
        holder.txv_date.setText(format);

    }

    static class InvoiceHolder {
        TextView txv_pharmacy;
        TextView txv_status;
        TextView txv_date;
    }


}
