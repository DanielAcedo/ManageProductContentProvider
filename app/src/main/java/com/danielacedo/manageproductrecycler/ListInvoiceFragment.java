package com.danielacedo.manageproductrecycler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.danielacedo.manageproductrecycler.adapter.PharmacyAdapter;
import com.danielacedo.manageproductrecycler.interfaces.PharmacyPresenter;
import com.danielacedo.manageproductrecycler.model.Pharmacy;
import com.danielacedo.manageproductrecycler.presenter.PharmacyPresenterImpl;

/**
 * Activity inherating from ListActivity that displays all the products in out Applicationś List
 * @author Daniel Acedo Calderón
 */
public class ListInvoiceFragment extends Fragment{

    private PharmacyAdapter adapter;
    private ListView lv_ListProduct;
    private TextView txv_empty;
    private FloatingActionButton fab_AddProduct;
    private ProgressDialog progressDialog;
    private View root;

    private ListPharmacyListener mCallback;
    PharmacyPresenter presenter;

    interface ListPharmacyListener{
        void showManagePharmacy(Bundle bundle);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (ListPharmacyListener)activity;
        }catch(ClassCastException e){
            throw new ClassCastException(getContext().toString() + "must implement ListProductListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PharmacyAdapter(getContext(), null, 0);
        //presenter = new PharmacyPresenterImpl(this);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (presenter != null) {
            presenter.loadPharmacy();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootview = inflater.inflate(R.layout.fragment_product, null);

        lv_ListProduct = (ListView) rootview.findViewById(R.id.lv_listProduct);
        lv_ListProduct.setAdapter(adapter);
        lv_ListProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Pharmacy.PHARMACY_KEY, (Pharmacy)parent.getItemAtPosition(position));

                mCallback.showManagePharmacy(bundle);
            }
        });

        progressDialog = new ProgressDialog(getContext());

        txv_empty = (TextView)rootview.findViewById(android.R.id.empty);

        registerForContextMenu(lv_ListProduct);

        //FloatingActionButton
        fab_AddProduct = (FloatingActionButton) rootview.findViewById(R.id.fab_AddProduct);
        fab_AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.showManagePharmacy(null);
            }
        });

        root = rootview.findViewById(R.id.list_product);

        return rootview;
    }



    /* Creates context menu */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();

        switch (v.getId()){
            case R.id.lv_listProduct:
                inflater.inflate(R.menu.ctx_menu_product, menu);
                break;
        }
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.ctx_menu_product_delete:
                Pharmacy pharmacy = adapter.getItem(((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position);
                presenter.deletePharmacy(pharmacy);
                break;
        }

        return super.onContextItemSelected(item);
    }

    /*
                Inflates menu in the fragment
    */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_listproduct, menu); //Inflates the menu resource in the menu object
    }

    /*
            Code to be executed for every menuitem
         */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add_product:
                mCallback.showManagePharmacy(null);
                break;
            case R.id.action_sort_alphabetically:
                //adapter.getAlphabeticallySortedProducts();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        presenter = null;
    }
}
