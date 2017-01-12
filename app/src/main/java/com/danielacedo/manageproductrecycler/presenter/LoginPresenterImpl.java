package com.danielacedo.manageproductrecycler.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.danielacedo.manageproductrecycler.HomeActivity;
import com.danielacedo.manageproductrecycler.ListProductFragment;
import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.model.Error;
import com.danielacedo.manageproductrecycler.preferences.AccountPreferenceImpl;
import com.danielacedo.manageproductrecycler.utils.ErrorMapUtils;

import java.util.regex.Pattern;

/**
 * Created by Daniel on 6/10/16.
 */

/**
 * Presenter for the login Activity. It handles all the communication with the model and updates the view when necessary
 * @author Daniel Acedo Calderón
 */
public class LoginPresenterImpl implements com.danielacedo.manageproductrecycler.interfaces.LoginPresenter.Presenter {

    private com.danielacedo.manageproductrecycler.interfaces.LoginPresenter.View view;
    private Context context;

    public LoginPresenterImpl(com.danielacedo.manageproductrecycler.interfaces.LoginPresenter.View view){
        this.view = view;
        context = (Context)view;
    }

    @Override
    public void validateCredentials(String user, String password) {

        int validateUser = validateCredentialsUser(user);
        int validatePassword = validateCredentialsPassword(password);

        boolean userOk = validateUser == Error.OK;
        boolean passOK = validatePassword == Error.OK;


        if (!userOk) {
            String nameResource = ErrorMapUtils.getErrorMap(context).get(String.valueOf(validateUser));
            view.setMessageError(nameResource, R.id.til_User);
        }
        if(!passOK){
            String nameResource = ErrorMapUtils.getErrorMap(context).get(String.valueOf(validatePassword));
            view.setMessageError(nameResource, R.id.til_Password);
        }
        if(userOk && passOK){
            success(user, password);
        }
    }

    @Override
    public int validateCredentialsUser(String user) {
        int code = Error.OK;

        if(TextUtils.isEmpty(user)) {
            code = Error.DATA_EMPTY;
        }

        return code;
    }

    @Override
    public int validateCredentialsPassword(String pass) {
        int code = Error.OK;

        if(TextUtils.isEmpty(pass)){
            code = Error.DATA_EMPTY;
        }
        else if(!Pattern.matches(".*[0-9].*", pass)){
            code = Error.PASSWORD_DIGIT;
        }
        else if(!Pattern.matches(".*[a-z].*",pass) || !Pattern.matches(".*[A-Z].*",pass)){
            code = Error.PASSWORD_UPPERLOWERCASE;
        }
        else if(pass.length()<8){
            code = Error.PASSWORD_LENGTH;
        }

        return code;
    }

    //Previous method for getting error message based on error code
    public String switchErrorsMessage(int code){

        String message = "";

        switch(code){
            case Error.DATA_EMPTY:
                message = ((Context)view).getResources().getString(R.string.err_emptyData);
                break;
            case Error.PASSWORD_DIGIT:
                message = ((Context)view).getResources().getString(R.string.err_Password_Digit);
                break;
            case Error.PASSWORD_UPPERLOWERCASE:
                message = ((Context)view).getResources().getString(R.string.err_Password_UpperLowerCase);
                break;
            case Error.PASSWORD_LENGTH:
                message = ((Context)view).getResources().getString(R.string.err_Password_Length);
                break;
        }

        return message;
    }

    public void success(String user, String pass){
        AccountPreferenceImpl.getInstance(context).putUser(user);
        AccountPreferenceImpl.getInstance(context).putPassword(pass);

        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        view.startActivity(intent);
    }

}
