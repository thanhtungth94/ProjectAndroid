package com.example.thanhtung.thanhtung_tiente;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by thanhtung on 09/12/2016.
 */

public class Convert extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String url ="http://vietcombank.com.vn/ExchangeRates/ExrateXML.aspx";
    String Currency2="";
    String Currency1="";
    int vitri1,vitri2;
    ArrayList tiente ;
    ArrayList< DataCurrency> AAAAAAAAAAAA=new ArrayList<DataCurrency>();
    String[] ma_nuoc = {"AUD", "CAD", "CHF", "DKK", "EUR", "GBP", "HKD", "INR", "JPY", "KRW", "KWD", "MYR", "NOK", "RUB", "SAR", "SEK", "SGD", "THB", "USD"};
//    String[] ten_nuoc = {"AUST.DOLLAR", "CANADIAN DOLLAR", "SWISS FRANCE", "DANISH KRONE", "EURO", "BRITISH POUND",
//            "HONGKONG DOLLAR", "INDIAN RUPEE", "JAPANESE YEN", "SOUTH KOREAN WON", "KUWAITI DINAR", "MALAYSIAN RINGGIT",
//            "NORWEGIAN KRONER", "RUSSIAN RUBLE", "SAUDI RIAL", "SWEDISH KRONA", "SINGAPORE DOLLAR", "THAI BAHT", "US DOLLAR"};
    int[] images = {R.drawable.aud, R.drawable.cad, R.drawable.chf, R.drawable.dkk, R.drawable.eur, R.drawable.gbp,
            R.drawable.hkd, R.drawable.inr, R.drawable.jpy, R.drawable.krw, R.drawable.kwd, R.drawable.myr, R.drawable.nok,
            R.drawable.rub, R.drawable.sar, R.drawable.sek, R.drawable.sgd, R.drawable.thb, R.drawable.usd};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final XMLParser handleXml =new XMLParser(url);
        handleXml.fetchXML();
        while (handleXml.parsingComplete) {}
        tiente=handleXml.getStringxml();
        AAAAAAAAAAAA = handleXml.getDataXML();
        EditText editTextSo =(EditText) findViewById(R.id.editText);
        final TextView tvkq=(TextView)findViewById(R.id.textviewngoaite);
        TextView tvdate=(TextView)findViewById(R.id.textViewDateTime);

        Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
//        SpinnerArrayAdapter adapter = new SpinnerArrayAdapter(this,R.layout.layout,tiente);
//        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spin1.setAdapter(new SpinerArrayAdapter(this,R.layout.spinner_row,ma_nuoc));
        spin2.setAdapter(new SpinerArrayAdapter(this,R.layout.spinner_row,ma_nuoc));

        spin1.setOnItemSelectedListener(new MyProcessEvent1());
        spin2.setOnItemSelectedListener(new MyProcessEvent2());
        editTextSo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((!Currency2.equals("---NgoaiTe---")) && (!Currency1.equals("---NgoaiTe---"))) {
                    if (!s.toString().equals("")) {
                        String text = s.toString();
                        double input = Double.parseDouble(text);    //chuyen sang kieu double
                        String tygiangoaite1 = AAAAAAAAAAAA.get(vitri1).getTransfer();
                        String tygiangoaite2 = AAAAAAAAAAAA.get(vitri2).getTransfer();
                        double tigiangoaite1 = Double.parseDouble(tygiangoaite1);
                        double tigiangoaite2 = Double.parseDouble(tygiangoaite2);
                        double ketqua = input * tigiangoaite1 / tigiangoaite2;
                        DecimalFormat dcf = new DecimalFormat("#.000");
                        tvkq.setText(dcf.format(ketqua));
                    } else {
                        tvkq.setText("0.0");
                    }
                } else {
                    tvkq.setText("0.0");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public class MyProcessEvent2 implements AdapterView.OnItemSelectedListener {//Khi có chọn lựa thì vào hàm này
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {//arg2 là phần tử được chọn trong data source
            Currency2 = (String) tiente.get(arg2);
            vitri2=arg2;
        }//Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    public class MyProcessEvent1 implements AdapterView.OnItemSelectedListener {
        //Khi có chọn lựa thì vào hàm này
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Currency1 = (String) tiente.get(arg2);
            vitri1=arg2;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
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
        getMenuInflater().inflate(R.menu.convert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_chart) {
            Intent chart = new Intent(Convert.this,Chart.class);
            startActivity(chart);
            // Handle the camera action
        } else if (id == R.id.action_currency) {
            Intent chart = new Intent(Convert.this,Currency.class);
            startActivity(chart);

        } else if (id == R.id.action_convert) {

        }else if (id == R.id.action_like) {
            Toast.makeText(Convert.this,"Dang duoc cap nhat",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_ratings) {
            Intent a = new Intent(Convert.this,Ratings.class);
            startActivity(a);

        } else if (id == R.id.nav_share) {
            Toast.makeText(Convert.this,"Dang duoc cap nhat",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(Convert.this,"Dang duoc cap nhat",Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class SpinerArrayAdapter extends ArrayAdapter<String> {
        public SpinerArrayAdapter(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.spinner_row, parent, false);
            TextView txvtMaNuoc = (TextView) mySpinner.findViewById(R.id.textViewMaNuoc);
            txvtMaNuoc.setText(ma_nuoc[position]);
//            TextView txvtTenNuoc = (TextView) mySpinner.findViewById(R.id.textViewTenNuoc);
//            txvtTenNuoc.setText(ten_nuoc[position]);
            ImageView imgNuoc = (ImageView) mySpinner.findViewById(R.id.country);
            imgNuoc.setImageResource(images[position]);
            return mySpinner;

        }
    }
}
