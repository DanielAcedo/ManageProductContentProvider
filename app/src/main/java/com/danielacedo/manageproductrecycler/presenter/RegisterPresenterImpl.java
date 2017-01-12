package com.danielacedo.manageproductrecycler.presenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.interfaces.SignUpPresenter;
import com.danielacedo.manageproductrecycler.model.Error;
import com.danielacedo.manageproductrecycler.preferences.AccountPreferenceImpl;
import com.danielacedo.manageproductrecycler.utils.ErrorMapUtils;

import java.util.regex.Pattern;

;

/**
 * Created by Daniel on 10/11/16.
 */

public class RegisterPresenterImpl implements SignUpPresenter.PresenterUser{

    SignUpPresenter.View view;
    private Context context;

    public RegisterPresenterImpl(SignUpPresenter.View view){
        this.view = view;
        this.context = (Context)view;
    }


    @Override
    public void validateCredentials(String user, String pass, String confirmPass, String email, String confirmEmail, String companyName, boolean isCompany) {

        int validateUser = validateCredentialsUser(user);
        int validatePassword = validateCredentialsPassword(pass);
        int validateConfirmPassword = validateCredentialsConfirmPassword(pass, confirmPass);
        int validateEmail = validateCredentialsEmail(email);
        int validateConfirmEmail = validateCredentialsConfirmEmail(email, confirmEmail);
        int validateCompanyName = validateCredentialsCompanyName(companyName, isCompany);

        //region "Too much validation"
        if(validateUser==Error.OK){
            if(validatePassword==Error.OK){
                if(validateConfirmPassword==Error.OK){
                    if(validateEmail==Error.OK){
                        if(validateConfirmEmail==Error.OK){
                            if(validateCompanyName==Error.OK){

                                success(user, pass, email);    //Everything's good
                            }else{
                                view.setMessageError(getErrorResourceId(validateCompanyName), R.id.edt_CompanyNameRegister);
                            }
                        }else{
                            view.setMessageError(getErrorResourceId(validateConfirmEmail), R.id.edt_ConfirmEmail);
                        }
                    }else{
                        view.setMessageError(getErrorResourceId(validateEmail), R.id.edt_Email);
                    }
                }else{
                    view.setMessageError(getErrorResourceId(validateConfirmPassword), R.id.edt_ConfirmPass);
                }
            }else{
                view.setMessageError(getErrorResourceId(validatePassword), R.id.edt_PassRegister);
            }
        }
        else{
            view.setMessageError(getErrorResourceId(validateUser), R.id.edt_UserRegister);
        }
        // endregion
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

    @Override
    public int validateCredentialsEmail(String email) {
        int code = Error.OK;

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            code = Error.EMAIL_INVALID;
        }

        return code;
    }

    @Override
    public int validateCredentialsConfirmEmail(String email, String confirmEmail) {
        int code = Error.OK;

        if(!email.equals(confirmEmail)){
            code = Error.EMAIL_MISMATCH;
        }

        return code;
    }

    @Override
    public int validateCredentialsConfirmPassword(String password, String confirmPassword) {
        int code = Error.OK;

        if(!password.equals(confirmPassword)){
            code = Error.PASSWORD_MISMATCH;
        }

        return code;
    }

    @Override
    public int validateCredentialsCompanyName(String companyName, boolean isCompany) {
        int code = Error.OK;

        if(isCompany && companyName.equals("")) {
            code = Error.COMPANYNAME_EMPTY;
        }

        return code;
    }

    /**
     * Method to be executed if the validation is successful
     * @param user User name
     * @param password User password
     */
    private void success(String user, String password, String email){
        AccountPreferenceImpl accountPreference = (AccountPreferenceImpl) AccountPreferenceImpl.getInstance(context);
        accountPreference.putUser(user);
        accountPreference.putPassword(password);
        accountPreference.putEmail(email);

        Toast.makeText((Context)view, R.string.Toast_SuccessfulRegister, Toast.LENGTH_SHORT).show();
        ((Activity)view).finish();
    }

    private String getErrorResourceId(int errorCode){
        return ErrorMapUtils.getErrorMap(context).get(String.valueOf(errorCode));
    }
}
