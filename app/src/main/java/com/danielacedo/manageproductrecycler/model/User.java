package com.danielacedo.manageproductrecycler.model;

/**
 * Created by Daniel on 6/10/16.
 */

import java.io.Serializable;

/**
 * Class that contains the user information to be stored
 * @author Daniel Acedo Calderón
 */
public class User{
    private String user;
    private String password;

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
