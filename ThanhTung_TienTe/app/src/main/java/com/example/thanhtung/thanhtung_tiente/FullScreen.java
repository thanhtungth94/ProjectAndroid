package com.example.thanhtung.thanhtung_tiente;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by thanhtung on 09/12/2016.
 */

public class FullScreen extends AppCompatActivity {
    Bitmap bitmapchart;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        final String url=i.getStringExtra("URL");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        imageView = (ImageView) findViewById(R.id.imageView);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                new LoadChart().execute(url);
            }
        });
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
            imageView.setImageBitmap(bitmapchart);
        }
    }
}
