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

import com.danielacedo.manageproductrecycler.adapter.InvoiceAdapter;
import com.danielacedo.manageproductrecycler.adapter.PharmacyAdapter;
import com.danielacedo.manageproductrecycler.interfaces.ChangeableTitle;
import com.danielacedo.manageproductrecycler.interfaces.InvoicePresenter;
import com.danielacedo.manageproductrecycler.interfaces.PharmacyPresenter;
import com.danielacedo.manageproductrecycler.model.Invoice;
import com.danielacedo.manageproductrecycler.model.InvoiceView;
import com.danielacedo.manageproductrecycler.model.Pharmacy;
import com.danielacedo.manageproductrecycler.presenter.InvoicePresenterImpl;
import com.danielacedo.manageproductrecycler.presenter.PharmacyPresenterImpl;

/**
 * Activity inherating from ListActivity that displays all the products in out Applicationś List
 * @author Daniel Acedo Calderón
 */
public class ListInvoiceFragment extends Fragment implements InvoicePresenter.View{

    private InvoiceAdapter adapter;
    private ListView lv_ListProduct;
    private TextView txv_empty;
    private FloatingActionButton fab_AddProduct;
    private ProgressDialog progressDialog;
    private View root;

    private ListInvoiceListener mCallback;
    InvoicePresenter presenter;

    private ChangeableTitle titleChanger;

    interface ListInvoiceListener{
        void showManageInvoice(Bundle bundle);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (ListInvoiceListener) activity;
            titleChanger = (ChangeableTitle)activity;
        }catch(ClassCastException e){
            throw new ClassCastException(getContext().toString() + "must implement ListInvoiceListener and ChangeableTitle");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
        titleChanger = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new InvoiceAdapter(getContext(), null, 0);
        presenter = new InvoicePresenterImpl(this);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (presenter != null) {
            presenter.loadInvoice();
        }
    }

    @Override
    public void showInvoice(Cursor invoiceCursor) {
        if (adapter != null) {
            adapter.changeCursor(invoiceCursor);
        }
    }

    @Override
    public void showEmptyState(boolean show) {

    }

    @Override
    public void showMessage(int message) {
        Snackbar.make(root, getResources().getString(message),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageDelete(Invoice invoice) {

    }

    @Override
    public ProgressDialog getProgressDialog() {
        return progressDialog;
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
                bundle.putParcelable(InvoiceView.INVOICEVIEW_KEY, (InvoiceView)parent.getItemAtPosition(position));

                mCallback.showManageInvoice(bundle);
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
                mCallback.showManageInvoice(null);
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
                int id = adapter.getItem(((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position).get_id();
                presenter.deleteInvoiceById(id);
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
                mCallback.showManageInvoice(null);
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
