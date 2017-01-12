package com.danielacedo.manageproductrecycler;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;

import com.danielacedo.manageproductrecycler.interfaces.ProductPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by usuario on 16/12/16.
 */

public class SimpleMultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {


    private Context context;
    private ProductPresenter presenter;

    private List<View> hiddenViews;
    private int cont = 0;

    private int statusBarColor;

    public SimpleMultiChoiceModeListener(Context context,ProductPresenter presenter, List<View> hiddenViews){
        this.context = context;
        this.presenter = presenter;
        this.hiddenViews = hiddenViews;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if(checked){
            cont++;
            //TODO adapter.addSelection(position, checked);
        }else{
            cont--;
            //TODO adapter.removeSelection(position, checked);
        }

        mode.setTitle(String.valueOf(cont));
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.ctx_menu_product, menu);


        for (View v: hiddenViews) {
            v.setVisibility(View.GONE);
        }

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            statusBarColor = ((Activity)context).getWindow().getStatusBarColor();
            ((Activity)context).getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.colorError));
        }

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.ctx_menu_product_delete:
                //TODO presenter.deleteSelectedProduct();
                break;
        }

        mode.finish();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        for (View v: hiddenViews) {
            v.setVisibility(View.VISIBLE);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ((Activity)context).getWindow().setStatusBarColor(statusBarColor);
        }


        presenter = null;
    }
}
