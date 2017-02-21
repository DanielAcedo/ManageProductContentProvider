package com.danielacedo.manageproductrecycler.model;

import java.util.Calendar;

/**
 * Created by Daniel on 20/02/2017.
 */

public class InvoiceView {
    private int _id;
    private String pharmacy;
    private String status;
    private Calendar date;

    public InvoiceView(String pharmacy, String status, Calendar date) {
        this.pharmacy = pharmacy;
        this.status = status;
        this.date = date;
    }

    public InvoiceView() {
    }

    public int get_id() {
        return _id;
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
}
