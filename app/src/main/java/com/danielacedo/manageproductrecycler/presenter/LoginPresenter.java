package com.danielacedo.manageproductrecycler.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.danielacedo.manageproductrecycler.ListProduct_Application;
import com.danielacedo.manageproductrecycler.Product_Activity;
import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.Register_Activity;
import com.danielacedo.manageproductrecycler.interfaces.ILoginMvp;
import com.danielacedo.manageproductrecycler.model.User;

import java.util.regex.Pattern;

/**
 * Created by Daniel on 6/10/16.
 */

/**
 * Presenter for the login Activity. It handles all the communication with the model and updates the view when necessary
 * @author Daniel Acedo Calderón
 */
public class LoginPresenter implements ILoginMvp.Presenter {

    private ILoginMvp.View view;

    public LoginPresenter(ILoginMvp.View view){
        this.view = view;
    }

    /**
     * Checks whether the submitted login information is valid and act in consequence
     * @param user User login
     * @param pass Password login
     */
    @Override
    public void validateCredentials(String user, String pass) {
        if(TextUtils.isEmpty(user)) {
            view.setMessageError(((Context) view).getResources().getString(R.string.err_emptyData), R.id.edt_User);
        }
        else if(TextUtils.isEmpty(pass)){
            view.setMessageError(((Context) view).getResources().getString(R.string.err_emptyData), R.id.edt_Pass);
        }
        else if(!Pattern.matches(".*[0-9].*", pass)){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_Password_Digit), R.id.edt_Pass);
        }
        else if(!Pattern.matches(".*[a-z].*",pass) || !Pattern.matches(".*[A-Z].*",pass)){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_Password_UpperLowerCase), R.id.edt_Pass);
        }
        else if(pass.length()<8){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_Password_Length), R.id.edt_Pass);
        }else{
            //Save the user in the Application class
            ((ListProduct_Application)((Context)view).getApplicationContext()).setUser(new User(user, pass));
            Intent intent = new Intent((Context)view, Product_Activity.class);
            ((Context) view).startActivity(intent);
        }
    }



    /**
     * Deals with opening the RegisterActivity and reacts to its results
     * @author Daniel Acedo Calderón
     */
    @Override
    public void openRegisterActivity() {
        Intent intent = new Intent((Context)view, Register_Activity.class);
        ((Context) view).startActivity(intent);
    }
}
