package com.danielacedo.manageproductrecycler.interfaces;

import android.content.Intent;

/**
 * Created by Daniel on 11/11/16.
 */

public interface LoginPresenter {
    int OK = 0; // Success
    int PASSWORD_DIGIT = 10; //The password doesn't comply with the password minimum digit policy
    int PASSWORD_UPPERLOWERCASE = 11; //The password doesn't comply with the password minimum lowercase/uppercase policy
    int PASSWORD_LENGTH = 12; //The password doesn't comply with the password length policy
    int DATA_EMPTY = 13; //The user or password field is empty

    /**
     * Interface to be implemented by the view in the pattern
     * @author Daniel Acedo Calderón
     */
    interface View{
        /**
         * Sends an order to the view to display a message error
         * @param messageError The message to be displayed
         * @author Daniel Acedo Calderón
         */
        void setMessageError(String messageError, int view);

        void startActivity(Intent intent);
    }

    /**
     * Interface to be implemented by the presenter in the pattern
     */
    interface Presenter{

        void validateCredentials(String user, String pass);

        int validateCredentialsUser(String user);

        int validateCredentialsPassword(String pass);

        /*static int validateCredentialsUser(String user){
            int code = Error.OK;

            if(TextUtils.isEmpty(user)) {
                code = Error.DATA_EMPTY;
            }

            return code;

        }*/

        /*static int validateCredentialsPassword(String pass){

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
        }*/
    }
}
