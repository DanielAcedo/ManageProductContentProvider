package com.danielacedo.manageproductrecycler;

import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.danielacedo.manageproductrecycler.interfaces.ILoginMvp;
import com.danielacedo.manageproductrecycler.model.User;
import com.danielacedo.manageproductrecycler.presenter.LoginPresenter;

import static com.danielacedo.manageproductrecycler.R.layout.activity_login_;

public class Login_Activity extends AppCompatActivity implements ILoginMvp.View{

    private ILoginMvp.Presenter loginMvp;
    private EditText edt_User, edt_Pass;
    private Button btn_Login, btn_Register;
    private TextInputLayout til_User, til_Pass;

    private final String TAG = "loginrelative";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login_);
        loginMvp = new LoginPresenter(this); // Presenter has a reference to the view

        edt_User = (EditText)findViewById(R.id.edt_User);
        edt_User.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til_User.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_Pass = (EditText)findViewById(R.id.edt_Pass);
        edt_Pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til_Pass.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        til_User = (TextInputLayout)findViewById(R.id.til_User);
        til_Pass = (TextInputLayout)findViewById(R.id.til_Password);
        btn_Login = (Button)findViewById(R.id.btn_Login);
        btn_Login.setOnClickListener((v) -> {
            loginMvp.validateCredentials(edt_User.getText().toString(), edt_Pass.getText().toString());
        });

        btn_Register = (Button)findViewById(R.id.btn_Register);
        btn_Register.setOnClickListener((v)->{
            loginMvp.openRegisterActivity();
        });

        Typeface font = Typeface.createFromAsset(getAssets(), "sun.ttf");
        edt_User.setTypeface(font);

        //Check user persistence
        User user = ((ListProduct_Application)getApplicationContext()).getUser();
        if(user != null){
            Log.d(TAG, user.getUser());
        }

        Log.d(TAG, "Created Activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Finished Activity");
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Check user persistence
        User user = ((ListProduct_Application)getApplicationContext()).getUser();
        if(user != null){
            Log.d(TAG, user.getUser());
        }

        Log.d(TAG, "Started Activity");
    }

    /**
     * Sends an order to the view to display a message error
     * @param messageError The message to be displayed
     * @author Daniel Acedo Calderón
     */
    @Override
    public void setMessageError(String messageError, int view) {
        //Toast.makeText(this, messageError, Toast.LENGTH_SHORT).show();

        switch (view){
            case R.id.edt_User:
                til_User.setError(messageError);
                break;
            case R.id.edt_Pass:
                til_Pass.setError(messageError);
                break;
        }
    }



    /**
     * Reset the input fiels for the login
     * @author Daniel Acedo Calderón
     */
    private void resetValues() {
        edt_User.setText("");
        edt_Pass.setText("");
    }
}
