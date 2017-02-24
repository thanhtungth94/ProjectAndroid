package com.example.thanhtung.thanhtung_tiente;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by thanhtung on 09/12/2016.
 */

public class Chart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imageViewchart;
    Bitmap bitmapchart;
    Button button1d, button3m, button1y, button2y;
    ImageButton btfullscreet;
    String Currency1, Curency2, s;
    CheckInternet check;
    TextView Datetimett;
    String url = "http://vietcombank.com.vn/ExchangeRates/ExrateXML.aspx";
    ArrayList<String> arrayCurrency = new ArrayList<String>();
    private XMLParser ngayngay = new XMLParser(url);
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
        setContentView(R.layout.activity_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        check = new CheckInternet(getApplicationContext());
        if (check.checkMobileInternetConn() == true)
            Toast.makeText(Chart.this, "Kiem tra ket noi internet", Toast.LENGTH_SHORT).show();
        else {

//            Toast.makeText(Chart.this, "ket noi", Toast.LENGTH_LONG).show();

            ngayngay.fetchXML();
            while (ngayngay.parsingComplete) {
            }
            Datetimett = (TextView) findViewById(R.id.textViewDateTime);
            Datetimett.setText("Cập nhật lần cuối:" + ngayngay.getDATE());
            arrayCurrency = ngayngay.getStringxml();
        }
        imageViewchart = (ImageView) findViewById(R.id.imageViewbieudo);
        btfullscreet =(ImageButton)findViewById(R.id.buttonfull);
        button1d = (Button) findViewById(R.id.button1D);
        button3m = (Button) findViewById(R.id.button3M);
        button1y = (Button) findViewById(R.id.button1Y);
        button2y = (Button) findViewById(R.id.button2Y);

        Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
//        SpinnerArrayAdapter adapter = new SpinnerArrayAdapter(this,
//                R.layout.layout,arrayCurrency);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spin1.setAdapter(new SpinerArrayAdapter(this,R.layout.spinner_row,ma_nuoc));
        spin2.setAdapter(new SpinerArrayAdapter(this,R.layout.spinner_row,ma_nuoc));
        spin1.setOnItemSelectedListener(new MyProcessEvent1());
        spin2.setOnItemSelectedListener(new MyProcessEvent2());

        button1d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        s = "http://chart.finance.yahoo.com/b?s=" + Currency1 + Curency2 + "%3DX";
                        new LoadChart().execute(s);
//                        Toast.makeText(Chart.this, Currency1 + Curency2, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        button1y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        s = "http://chart.finance.yahoo.com/1y?" + Currency1 + Curency2 + "=x";
                        new LoadChart().execute(s);
//                        Toast.makeText(Chart.this, Currency1 + Curency2, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        button2y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        s = "http://chart.finance.yahoo.com/2y?" + Currency1 + Curency2 + "=x";
                        new LoadChart().execute(s);
//                        Toast.makeText(Chart.this, Currency1 + Curency2, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        button3m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        s = "http://chart.finance.yahoo.com/3m?" + Currency1 + Curency2 + "=x";
                        new LoadChart().execute(s);
//                        Toast.makeText(Chart.this, Currency1 + Curency2, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        btfullscreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FullScreen.class);
                i.putExtra("URL",s);
                startActivity(i);

            }
        });
    }

    public class MyProcessEvent2 implements AdapterView.OnItemSelectedListener {//Khi có chọn lựa thì vào hàm này

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {//arg2 là phần tử được chọn trong data source
            Curency2 = arrayCurrency.get(arg2);
        }//Nếu không chọn gì cả

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public class MyProcessEvent1 implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Currency1 = arrayCurrency.get(arg2);

        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    //Nếu không chọn gì cả
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    class LoadChart extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL u = new URL(params[0]);
                bitmapchart = BitmapFactory.decodeStream(u.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imageViewchart.setImageBitmap(bitmapchart);
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
        getMenuInflater().inflate(R.menu.chart, menu);
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
        if (id == R.id.action_currency) {
            Intent chart = new Intent(Chart.this, Currency.class);
            startActivity(chart);
            // Handle the camera action
        } else if (id == R.id.action_convert) {
            Intent chart = new Intent(Chart.this, Convert.class);
            startActivity(chart);

        } else if (id == R.id.action_currency) {

        } else if (id == R.id.action_like) {

        } else if (id == R.id.action_ratings) {
            Intent intent = new Intent(Chart.this, Ratings.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getUrlimage() {
        return s;
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
