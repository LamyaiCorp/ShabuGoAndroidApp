package com.knott.navtab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.knott.navtab.fragment.BillFragment;
import com.knott.navtab.fragment_cart.CartFragment;
import com.knott.navtab.fragment.HomeFragment;
import com.knott.navtab.listproduce.ProductsAdapter;
import com.knott.navtab.loing.*;
import com.knott.navtab.nfc.NFCFragment;
import com.knott.navtab.fragment.TabMenuFragment;
import com.knott.navtab.nfc.NfcTouch;
import com.knott.navtab.unity.Utinity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager myFragmentManager;
    private FragmentTransaction myFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        myFragmentManager = getSupportFragmentManager();
        myFragmentTransaction = myFragmentManager.beginTransaction();
        myFragmentTransaction.replace(R.id.content_view, new HomeFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            myFragmentManager = getSupportFragmentManager();
            myFragmentTransaction = myFragmentManager.beginTransaction();
            myFragmentTransaction.replace(R.id.content_view, new HomeFragment()).commit();

        } else if (id == R.id.nav_gallery) {

           if(Utinity.NFC ){

               myFragmentManager = getSupportFragmentManager();
               myFragmentTransaction = myFragmentManager.beginTransaction();
               myFragmentTransaction.replace(R.id.content_view, new NFCFragment()).commit();

           }
           else {
               myFragmentManager = getSupportFragmentManager();
               myFragmentTransaction = myFragmentManager.beginTransaction();
               myFragmentTransaction.replace(R.id.content_view, new TabMenuFragment()).commit();
           }

        } else if (id == R.id.nav_slideshow) {
            myFragmentManager = getSupportFragmentManager();
            myFragmentTransaction = myFragmentManager.beginTransaction();
            myFragmentTransaction.replace(R.id.content_view, new CartFragment()).commit();

        } else if (id == R.id.nav_manage) {

            myFragmentManager = getSupportFragmentManager();
            myFragmentTransaction = myFragmentManager.beginTransaction();
            myFragmentTransaction.replace(R.id.content_view, new BillFragment()).commit();

        } else if(id == R.id.nav_share){


            RequestParams params = new RequestParams();
            params.put("id",Utinity.table_id);
            invokeWSLogout(params);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void invokeWSLogout(RequestParams params) {


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Utinity.url + "table" + "/" + "nottable",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d("TAG ===== ",response.toString());
                try {

                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
////
                        Intent loginIntent = new Intent(Main2Activity.this, com.knott.navtab.loing.MainActivity.class);
                        startActivity(loginIntent);
//                        finish();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }


            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // When Http response code is '404'
                if (statusCode == 404) {

                }
                // When Http response code is '500'
                else if (statusCode == 500) {

                }
                // When Http response code other than 404, 500
                else {

                }
            }
        });

    }
}
