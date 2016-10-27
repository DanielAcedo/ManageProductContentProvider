package com.danielacedo.manageproductrecycler.presenter;

/**
 * Created by Daniel on 20/10/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.danielacedo.manageproductrecycler.ListProduct_Application;
import com.danielacedo.manageproductrecycler.R;
import com.danielacedo.manageproductrecycler.interfaces.IAddProductMvp;
import com.danielacedo.manageproductrecycler.model.Product;

/**
 * Presenter for the AddProductView
 * @author Daniel Acedo Calderón
 */
public class ManageProductPresenter implements IAddProductMvp.Presenter{

    IAddProductMvp.View view;

    public ManageProductPresenter(IAddProductMvp.View view){
        this.view = view;
    }

    @Override
    public void validateProductFields(String name, String description, String brand, String dosage, String price, String stock, String image) {
        double finalPrice;
        int finalStock;
        int finalImage;

        if(name.isEmpty()){ //If it's empty send error message
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productName_empty), R.id.edt_Name);
        }
        else if(description.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productDescription_empty), R.id.edt_Description);
        }
        else if(brand.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productBrand_empty), R.id.edt_Brand);
        }
        else if(dosage.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productDosage_empty), R.id.edt_Dosage);
        }
        else if(price.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productPrice_empty), R.id.edt_Price);
        }
        else if(stock.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productStock_empty), R.id.edt_Stock);
        }
        else if(image.isEmpty()){
            view.setMessageError(((Context)view).getResources().getString(R.string.err_productImage_empty), R.id.edt_Image);
        }

        else{
            finalPrice = Double.parseDouble(price);
            finalStock = Integer.parseInt(stock);
            //finalImage = Integer.parseInt(image);
            finalImage = R.drawable.weed; // Placeholder image

            //Add the product to the Application's list
            ((ListProduct_Application)((Activity)view).getApplication()).saveProduct(new Product(name, description, finalPrice, brand, dosage, finalStock, finalImage));
            Intent intent = new Intent();
            //Return to Product_Activity with the result
            ((Activity)view).setResult(Activity.RESULT_OK, intent);
            ((Activity)view).finish();
        }


    }
}