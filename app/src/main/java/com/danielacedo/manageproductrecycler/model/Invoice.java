package com.danielacedo.manageproductrecycler.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by usuario on 13/02/17.
 */

public class Invoice {
    private int _id;
    private int id_pharmacy;
    private int id_status;
    private Calendar date;

    public Invoice(int id_pharmacy, int id_status, Calendar date) {
        this.id_pharmacy = id_pharmacy;
        this.id_status = id_status;
        this.date = date;
    }

    public Invoice(){}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
