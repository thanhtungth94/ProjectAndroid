package com.example.thanhtung.thanhtung_tiente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by thanhtung on 09/12/2016.
 */

public class DetailCurrency extends AppCompatActivity {
    String s=null;
    ShareActionProvider mShareActionProvider=null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcurrency);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String date=i.getStringExtra("Date");
        String Code=i.getStringExtra("CurrencyCode");
        String Name=i.getStringExtra("CurrencyName");
        String Buy=i.getStringExtra("Buy");
        String Transfer=i.getStringExtra("Transfer");
        String Sell=i.getStringExtra("Sell");

        TextView textView =(TextView)findViewById(R.id.textView2);
        s="Code : "+ Code +"\nName : "+Name+"\nBuy : "+Buy+"\nTransfer : "+Transfer+"\nSell : "+Sell;
        textView.setText("Ngày cập nhật : "+date+"\n\nCode : " + Code + "\n\nName : " + Name + "\n\nBuy : " + Buy + "\n\nTransfer : " + Transfer +"\n\nSell : "+Sell);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailcurrency, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareHistoryFileName(
                ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        mShareActionProvider.setShareIntent(getDefaultIntent());
        return true;
    }
    private Intent getDefaultIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Gia Ngoai Te");
        shareIntent.putExtra(Intent.EXTRA_TEXT,s);
        return shareIntent;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
