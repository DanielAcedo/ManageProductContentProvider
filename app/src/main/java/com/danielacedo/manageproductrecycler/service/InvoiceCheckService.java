package com.danielacedo.manageproductrecycler.service;

import android.app.IntentService;
import android.app.LoaderManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.danielacedo.manageproductrecycler.db.DatabaseContract;
import com.danielacedo.manageproductrecycler.model.InvoiceView;
import com.danielacedo.manageproductrecycler.provider.ManageProductContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Daniel on 23/02/2017.
 */

public class InvoiceCheckService extends Service {

    Loader.OnLoadCompleteListener onLoadCompleteListener;
    private static final int LOAD_INVOICES = 1;

    private Loader loader;


    @Override
    public void onCreate() {
        super.onCreate();

        loader = new CursorLoader(this, ManageProductContract.InvoiceEntry.CONTENT_URI,
                DatabaseContract.InvoiceEntry.PROJECTION, null, null, DatabaseContract.InvoiceEntry.DEFAULT_SORT);

        onLoadCompleteListener = new Loader.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(Loader loader, Object data) {
                Cursor cursor = (Cursor)data;
                int cont = 0;

                for (int i = 0; i < cursor.getCount(); i++){
                    InvoiceView invoice = getItem(cursor, i);

                    if(invoice.getStatus().equalsIgnoreCase("pendiente") && haveTwoDaysPassed(invoice.getDate())){
                        cont++;
                    }
                }

                Notification.Builder builder = new Notification.Builder(InvoiceCheckService.this)
                        .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                        .setContentTitle("Pedidos pendientes")
                        .setContentText("Tienes "+cont+" pedidos pendientes de hace más de dos días");


                NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nManager.notify(1, builder.build());

                stopSelf();
            }
        };

        loader.registerListener(LOAD_INVOICES, onLoadCompleteListener);
        loader.startLoading();
    }

    private boolean haveTwoDaysPassed(Calendar cal){
        Calendar todayCalendar = Calendar.getInstance();
        long today = todayCalendar.getTimeInMillis();
        int todayDays = (int) (today / (86400000));

        long targetTime = cal.getTimeInMillis();
        int targetDays = (int) (targetTime / (86400000));

        if(todayDays - targetDays > 2){
            return true;
        }

        return false;
    }

    public InvoiceView getItem(Cursor cursor, int position) {
        cursor.moveToPosition(position);

        InvoiceView invoice = new InvoiceView();
        invoice.set_id(cursor.getInt(0));
        invoice.setId_pharmacy(cursor.getInt(1));
        invoice.setPharmacy(cursor.getString(2));
        invoice.setId_status(cursor.getInt(3));
        invoice.setStatus(cursor.getString(4));
        Calendar calendarTmp = Calendar.getInstance();
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            calendarTmp.setTime(format.parse(cursor.getString(5)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        invoice.setDate(calendarTmp);


        return invoice;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (loader != null) {
            loader.unregisterListener(onLoadCompleteListener);
            loader.cancelLoad();
            loader.stopLoading();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
