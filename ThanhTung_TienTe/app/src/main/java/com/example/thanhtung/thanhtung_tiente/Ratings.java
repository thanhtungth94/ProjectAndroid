package com.example.thanhtung.thanhtung_tiente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by thanhtung on 09/12/2016.
 */

public class Ratings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_ratings);
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
            Toast.makeText(Ratings.this, "Kiem tra ket noi internet", Toast.LENGTH_SHORT).show();
        else{
//            Toast.makeText(Ratings.this,"ket noi internet",Toast.LENGTH_LONG).show();
            handleXml.fetchXML();
            while (handleXml.parsingComplete) {}
            arrayList =new ArrayList<DataCurrency>();
            arrayList=handleXml.getDataXML();
            Collections.sort(arrayList, new Comparator<DataCurrency>() {
                @Override
                public int compare(DataCurrency nt1, DataCurrency nt2) {
                    if (Double.parseDouble(nt1.getSell()) < Double.parseDouble(nt2.getSell())) {
                        return 1;
                    } else {
                        if (Double.parseDouble(nt1.getSell()) == Double.parseDouble(nt2.getSell())) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                }
            });
            adapter = new MyArrayAdapter(Ratings.this,R.layout.list_row,arrayList);
            listViewCurrency.setAdapter(adapter);
            textViewDATE.setText("Cập nhật lần cuối:"+handleXml.getDATE());
            listViewCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DataCurrency A = arrayList.get(position);
                    String CurrencyCode = A.getCurrencyCode();
                    String CurrencyName = A.getCurrencyName();
                    String Buy = A.getBuy();
                    String Transfer = A.getTransfer();
                    String Sell = A.getSell();
                    // Launching new Activity on selecting single List Item
                    Intent i = new Intent(getApplicationContext(), DetailCurrency.class);
                    // sending data to new activity
                    i.putExtra("CurrencyCode", CurrencyCode);
                    i.putExtra("CurrencyName", CurrencyName);
                    i.putExtra("Buy", Buy);
                    i.putExtra("Transfer", Transfer);
                    i.putExtra("Sell", Sell);
                    startActivity(i);
                }
            });
        }

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
        getMenuInflater().inflate(R.menu.ratings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_chart) {
            Intent chart = new Intent(Ratings.this,Chart.class);
            startActivity(chart);
            // Handle the camera action
        } else if (id == R.id.action_convert) {
            Intent chart = new Intent(Ratings.this,Convert.class);
            startActivity(chart);

        } else if (id == R.id.action_currency) {
            Intent currency = new Intent(Ratings.this,Currency.class);
            startActivity(currency);
        }else if (id == R.id.action_like) {
            Toast.makeText(Ratings.this,"Dang duoc cap nhat",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_ratings) {

        } else if (id == R.id.nav_share) {
            Toast.makeText(Ratings.this,"Dang duoc cap nhat",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(Ratings.this,"Dang duoc cap nhat",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
