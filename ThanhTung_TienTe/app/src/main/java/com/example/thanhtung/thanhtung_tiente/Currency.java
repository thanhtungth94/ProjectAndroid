package com.example.thanhtung.thanhtung_tiente;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by thanhtung on 09/12/2016.
 */

public class Currency extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener {
    ListView listViewCurrency;
    String url="http://vietcombank.com.vn/ExchangeRates/ExrateXML.aspx";
    CheckInternet check;
    private XMLParser handleXml = new XMLParser(url);
    private ArrayAdapter adapter;
    private ArrayList<DataCurrency> arrayList;
    TextView textViewDATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listViewCurrency =(ListView)findViewById(R.id.listViewCurency);
        textViewDATE = (TextView)findViewById(R.id.textViewDateTime);
        check = new CheckInternet(getApplicationContext());
        if(check.checkMobileInternetConn())
            Toast.makeText(Currency.this,"Kiem tra ket noi internet", Toast.LENGTH_SHORT).show();
        else{
//            Toast.makeText(Currency.this,"ket noi internet",Toast.LENGTH_LONG).show();
            handleXml.fetchXML();
            while (handleXml.parsingComplete) {}
            arrayList =new ArrayList<DataCurrency>();
            arrayList=handleXml.getDataXML();

            adapter = new MyArrayAdapter(this,R.layout.list_row,arrayList);
            listViewCurrency.setAdapter(adapter);
            textViewDATE.setText("Cập nhật lần cuối: " +handleXml.getDATE());
        }
        listViewCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataCurrency A=arrayList.get(position);
                String CurrencyCode=A.getCurrencyCode();
                String CurrencyName=A.getCurrencyName();
                String Buy=A.getBuy();
                String Transfer=A.getTransfer();
                String Sell=A.getSell();
                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), DetailCurrency.class);
                // sending data to new activity
                i.putExtra("Date",handleXml.getDATE());
                i.putExtra("CurrencyCode",CurrencyCode);
                i.putExtra("CurrencyName",CurrencyName);
                i.putExtra("Buy",Buy);
                i.putExtra("Transfer",Transfer);
                i.putExtra("Sell",Sell);
                startActivity(i);
            }
        });
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
        getMenuInflater().inflate(R.menu.currency, menu);
        // Inflate menu to add items to action bar if it is present.
        if  ( Build. VERSION . SDK_INT >=  Build . VERSION_CODES . HONEYCOMB )  {
            MenuItem searchItem = menu . findItem ( R.id.action_search );
            SearchManager searchManager = ( SearchManager ) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView)  MenuItemCompat. getActionView(searchItem);
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            adapter = new MyArrayAdapter(Currency.this,R.layout.list_row,arrayList);
            listViewCurrency.setAdapter(adapter);
        } else {
            ArrayList<DataCurrency> arr = new ArrayList<DataCurrency>();
            for(int j=0;j<arrayList.size();j++){
                if(arrayList.get(j).getCurrencyName().toLowerCase().startsWith(newText.toString())
                        || arrayList.get(j).getCurrencyName().startsWith(newText.toString())){
                    arr.add(arrayList.get(j));
                }
            }
            adapter = new MyArrayAdapter(Currency.this,R.layout.list_row,arr);
            listViewCurrency.setAdapter(adapter);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_chart) {
            Intent chart = new Intent(Currency.this,Chart.class);
            startActivity(chart);
            // Handle the camera action
        } else if (id == R.id.action_convert) {
            Intent chart = new Intent(Currency.this,Convert.class);
            startActivity(chart);

        } else if (id == R.id.action_currency) {

        }else if (id == R.id.action_like) {
            Toast.makeText(Currency.this,"Dang duoc cap nhat",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_ratings) {
            Intent ratings = new Intent(Currency.this,Ratings.class);
            startActivity(ratings);
        } else if (id == R.id.nav_share) {
            Toast.makeText(Currency.this,"Dang duoc cap nhat",Toast.LENGTH_SHORT).show();
            Intent share= new Intent(Currency.this,ShareCurrency.class);
            startActivity(share);
            finish();
        } else if (id == R.id.nav_send) {
            Toast.makeText(Currency.this,"Dang duoc cap nhat",Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
