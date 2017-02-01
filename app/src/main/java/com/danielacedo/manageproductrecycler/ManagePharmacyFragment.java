package com.danielacedo.manageproductrecycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.danielacedo.manageproductrecycler.interfaces.CategoryPresenter;
import com.danielacedo.manageproductrecycler.interfaces.IProduct;
import com.danielacedo.manageproductrecycler.interfaces.ManagePharmacyPresenter;
import com.danielacedo.manageproductrecycler.interfaces.ManageProductPresenter;
import com.danielacedo.manageproductrecycler.model.Pharmacy;
import com.danielacedo.manageproductrecycler.model.Product;
import com.danielacedo.manageproductrecycler.presenter.ManagePharmacyPresenterImpl;
import com.danielacedo.manageproductrecycler.presenter.ManagePresenterImpl;

/**
 * Activity used for adding new products to the application. After creating one successfully it calls back ListProductActivity
 * @author Daniel Acedo Calderón
 */
public class ManagePharmacyFragment extends Fragment implements ManagePharmacyPresenter.View{

    public static final String PHARMACY_RESULT_KEY = "pharmacyResult";

    private ManagePharmacyPresenter presenter;
    private EditText edt_cif, edt_address, edt_phone, edt_email;

    private Button btn_AddPharmacy;
    private LinearLayout root;
    private ManagePharmacyListener mCallback;

    private boolean editing;

    interface ManagePharmacyListener {
        void showListPharmacy();
    }

    public static ManagePharmacyFragment newInstance(Bundle args){
        ManagePharmacyFragment fragment = new ManagePharmacyFragment();

        if(args != null){
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (ManagePharmacyListener)activity;
        }catch(ClassCastException e){
            throw new ClassCastException(getContext().toString() + "must implement ManageProductListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ManagePharmacyPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootview = inflater.inflate(R.layout.fragment_manage_pharmacy, null);

        if(rootview != null){
            editing = false;


            root = (LinearLayout)rootview.findViewById(R.id.add_product);

            edt_cif = (EditText)rootview.findViewById(R.id.edt_pharmacy_cif);
            edt_address = (EditText)rootview.findViewById(R.id.edt_pharmacy_address);
            edt_phone = (EditText)rootview.findViewById(R.id.edt_pharmacy_phone);
            edt_email = (EditText)rootview.findViewById(R.id.edt_pharmacy_email);

            btn_AddPharmacy = (Button)rootview.findViewById(R.id.btn_pharmacy_add);
            btn_AddPharmacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(presenter.validateFields(edt_cif.getText().toString(), edt_address.getText().toString(), edt_phone.getText().toString(), edt_email.getText().toString())){
                        addResultPharmacy();
                    }
                }
            });



            checkIntent();
        }

        return rootview;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void addResultPharmacy(){

        Pharmacy pharmacy;

        if(editing){
            //TODO ADD UPDATEPHARMACY
            pharmacy = new Pharmacy();
            pharmacy.setCif(edt_cif.getText().toString());
            pharmacy.setAddress(edt_address.getText().toString());
            pharmacy.setTelephone_number(edt_phone.getText().toString());
            pharmacy.setEmail(edt_email.getText().toString());

            presenter.addPharmacy(pharmacy);
            mCallback.showListPharmacy();
        }else{
            pharmacy = new Pharmacy();
            pharmacy.setCif(edt_cif.getText().toString());
            pharmacy.setAddress(edt_address.getText().toString());
            pharmacy.setTelephone_number(edt_phone.getText().toString());
            pharmacy.setEmail(edt_email.getText().toString());

            presenter.addPharmacy(pharmacy);
            mCallback.showListPharmacy();
        }
    }

    private Pharmacy getEditPharmacy(){
        Bundle bundle = getArguments();
        Pharmacy pharmacy = null;

        if(bundle != null) {
            pharmacy = bundle.getParcelable(Pharmacy.PHARMACY_KEY);
        }

        return pharmacy;
    }

    private void checkIntent(){

        Bundle bundle = getArguments();

        if(bundle != null){
            Pharmacy pharmacy = bundle.getParcelable(Pharmacy.PHARMACY_KEY);
            if(pharmacy != null){
                edt_cif.setText(pharmacy.getCif());
                edt_address.setText(pharmacy.getAddress());
                edt_phone.setText(pharmacy.getTelephone_number());
                edt_email.setText(pharmacy.getEmail());

                btn_AddPharmacy.setText(R.string.btn_AddProduct_Edit);

                editing = true;
            }
        }


    }

    /**
     * Displays error messages from the presenter
     * @param messageError The message to be displayed
     * @author Daniel Acedo Calderón
     */
    @Override
    public void showMessage(int messageError) {
        Snackbar.make(root, getResources().getString(messageError), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
    }
}
