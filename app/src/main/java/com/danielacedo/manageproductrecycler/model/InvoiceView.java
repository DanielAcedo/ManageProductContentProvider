package com.danielacedo.manageproductrecycler.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by Daniel on 20/02/2017.
 */

public class InvoiceView implements Parcelable {
    public static String INVOICEVIEW_KEY = "invoiceviewKey";

    private int _id;
    private int id_pharmacy;
    private String pharmacy;
    private int id_status;
    private String status;
    private Calendar date;

    public InvoiceView(int _id, int id_pharmacy, String pharmacy, int id_status, String status, Calendar date) {
        this._id = _id;
        this.id_pharmacy = id_pharmacy;
        this.pharmacy = pharmacy;
        this.id_status = id_status;
        this.status = status;
        this.date = date;
    }

    public InvoiceView() {
    }

    protected InvoiceView(Parcel in) {
        _id = in.readInt();
        id_pharmacy = in.readInt();
        pharmacy = in.readString();
        id_status = in.readInt();
        status = in.readString();
    }

    public static final Creator<InvoiceView> CREATOR = new Creator<InvoiceView>() {
        @Override
        public InvoiceView createFromParcel(Parcel in) {
            return new InvoiceView(in);
        }

        @Override
        public InvoiceView[] newArray(int size) {
            return new InvoiceView[size];
        }
    };

    public int get_id() {
        return _id;
    }

    public int getId_pharmacy() {
        return id_pharmacy;
    }

    public void setId_pharmacy(int id_pharmacy) {
        this.id_pharmacy = id_pharmacy;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(id_pharmacy);
        dest.writeString(pharmacy);
        dest.writeInt(id_status);
        dest.writeString(status);
    }
}
