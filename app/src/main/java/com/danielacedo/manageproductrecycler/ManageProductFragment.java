package com.danielacedo.manageproductrecycler;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.danielacedo.manageproductrecycler.interfaces.IProduct;
import com.danielacedo.manageproductrecycler.interfaces.ManagePresenter;
import com.danielacedo.manageproductrecycler.model.Product;
import com.danielacedo.manageproductrecycler.presenter.ManagePresenterImpl;

/**
 * Activity used for adding new products to the application. After creating one successfully it calls back ListProductActivity
 * @author Daniel Acedo Calderón
 */
public class ManageProductFragment extends Fragment implements ManagePresenter.View{

    public static final String PRODUCT_RESULT_KEY = "productResult";

    ManagePresenter presenter;
    EditText edt_Name, edt_Description, edt_Price, edt_Brand, edt_Dosage, edt_Stock, edt_Image;
    Button btn_AddProduct;
    LinearLayout root;
    private ManageProductListener mCallback;

    boolean editing;

    interface ManageProductListener {
        void showListProduct();
    }

    public static ManageProductFragment newInstance(Bundle args){
        ManageProductFragment fragment = new ManageProductFragment();

        if(args != null){
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (ManageProductListener)activity;
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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootview = inflater.inflate(R.layout.activity_manage_product_, null);

        if(rootview != null){
            editing = false;
            presenter = new ManagePresenterImpl(this);

            edt_Name = (EditText) rootview.findViewById(R.id.edt_Name);
            edt_Description = (EditText) rootview.findViewById(R.id.edt_Description);
            edt_Price = (EditText) rootview.findViewById(R.id.edt_Price);
            edt_Brand = (EditText) rootview.findViewById(R.id.edt_Brand);
            edt_Dosage = (EditText) rootview.findViewById(R.id.edt_Dosage);
            edt_Stock = (EditText) rootview.findViewById(R.id.edt_Stock);
            edt_Image = (EditText) rootview.findViewById(R.id.edt_Image);
            root = (LinearLayout)rootview.findViewById(R.id.add_product);

            btn_AddProduct = (Button)rootview.findViewById(R.id.btn_AddProduct);
            btn_AddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (presenter.validateProductFields(edt_Name.getText().toString(), edt_Description.getText().toString(),
                            edt_Brand.getText().toString(), edt_Dosage.getText().toString(),
                            edt_Price.getText().toString(), edt_Stock.getText().toString(),
                            edt_Image.getText().toString())){

                        addResultProduct();
                        mCallback.showListProduct();
                    }


                }
            });

            checkIntent();
        }

        return rootview;

    }


    private void addResultProduct(){

        Product product;

        if(editing){
            product = getEditProduct();
            product.setName(edt_Name.getText().toString());
            product.setBrand(edt_Brand.getText().toString());
            product.setDescription(edt_Description.getText().toString());
            product.setDosage(edt_Dosage.getText().toString());
            product.setStock(Integer.parseInt(edt_Stock.getText().toString()));
            product.setPrice(Double.parseDouble(edt_Price.getText().toString()));

            presenter.updateProduct(product);
        }else{
            product = new Product(edt_Name.getText().toString(), edt_Description.getText().toString(),
                    Double.parseDouble(edt_Price.getText().toString()),
                    edt_Brand.getText().toString(), edt_Dosage.getText().toString(), Integer.parseInt(edt_Stock.getText().toString()),
                    R.drawable.weed);

            presenter.addProduct(product);
        }
    }

    private Product getEditProduct(){
        Bundle bundle = getArguments();
        Product product = null;

        if(bundle != null) {
            product = bundle.getParcelable(IProduct.PRODUCT_KEY);
        }

        return product;
    }

    private void checkIntent(){

        Bundle bundle = getArguments();

        if(bundle != null){
            Product product = bundle.getParcelable(IProduct.PRODUCT_KEY);
            if(product != null){
                edt_Name.setText(product.getName());
                edt_Description.setText(product.getDescription());
                edt_Price.setText(String.valueOf(product.getPrice()));
                edt_Brand.setText(product.getBrand());
                edt_Stock.setText(String.valueOf(product.getStock()));
                edt_Dosage.setText(product.getDosage());
                edt_Image.setText(String.valueOf(product.getImage()));

                btn_AddProduct.setText(R.string.btn_AddProduct_Edit);

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
}
