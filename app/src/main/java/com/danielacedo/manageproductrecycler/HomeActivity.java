package com.danielacedo.manageproductrecycler;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by usuario on 1/12/16.
 */

public class HomeActivity extends AppCompatActivity implements ManageProductFragment.ManageProductListener, MultiListProductFragment.MultiListProductListener {
    private static final int LONG_DELAY = 3500;
    private static final int SHORT_DELAY = 2000;

    private Toolbar toolbar;
    private MultiListProductFragment listProductFragment;
    private ManageProductFragment manageProductFragment;
    private boolean pressed = false;
    private long currentPressMillis;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private boolean selectingEmail = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_navigation);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_hamburguer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle = setupDrawerToggle();

        setupDrawerContent();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //Email selection
        navigationView.getHeaderView(0).findViewById(R.id.txv_ProfileEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectingEmail){
                    navigationView.getMenu().clear();
                    Menu menu = navigationView.getMenu();
                    menu.add(20, 0, 0, "dani_acedo@hotmail.com");
                    menu.add(20, 0, 1, "danielexmachina0451@gmail.com");


                    selectingEmail = true;
                }
                else{
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.menu_navview);
                    selectingEmail = false;
                }


                //navigationView.inflateMenu(R.menu.menu_emails);
            }
        });


        if(savedInstanceState == null) {
            listProductFragment = new MultiListProductFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.framehome, listProductFragment).commit();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setupDrawerContent(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getGroupId() == 20){
                    ((TextView)navigationView.getHeaderView(0).findViewById(R.id.txv_ProfileEmail)).setText(item.getTitle());
                    return false;
                }

                item.setChecked(true);

                switch (item.getItemId()){
                    case R.id.action_product:
                        showListProduct();
                        setTitle(item.getTitle());
                    break;

                    case R.id.action_pharmacy:
                        //onListPharmacyListener();
                        break;

                    default:
                        item.setChecked(false);
                        break;

                }

                drawerLayout.closeDrawers();

                return true;
            }
        });


    }

    public ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;

        switch (item.getItemId()){
            case R.id.action_settings_account:
                intent = new Intent(HomeActivity.this, AccountSettings_Activity.class);
                startActivity(intent);
                break;

            case R.id.action_settings_general:
                intent = new Intent(HomeActivity.this, GeneralSettings_Activity.class);
                startActivity(intent);
                break;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showManageProduct(Bundle bundle) {
        manageProductFragment = ManageProductFragment.newInstance(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.framehome, manageProductFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showListProduct() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.framehome, listProductFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
        }else{
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                if(System.currentTimeMillis() - currentPressMillis >= SHORT_DELAY){
                    Toast.makeText(this, "Pulsa atrás de nuevo para cerrar la aplicación", Toast.LENGTH_SHORT).show();
                }else{
                    finish();
                }

                currentPressMillis = System.currentTimeMillis();
            }else{
                super.onBackPressed();
            }
        }


    }
}
