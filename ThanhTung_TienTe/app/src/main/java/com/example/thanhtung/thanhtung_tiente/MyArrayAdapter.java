package com.example.thanhtung.thanhtung_tiente;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by thanhtung on 09/12/2016.
 */
public class MyArrayAdapter extends ArrayAdapter<DataCurrency>{

    private  Activity activity;
    private  int idLayout;
    private ArrayList<DataCurrency> list;
    public MyArrayAdapter(Activity activity, int layoutId, ArrayList<DataCurrency> list){
        super(activity, layoutId, list);
        this.activity=activity;
        this.idLayout = layoutId;
        this.list = list;
    }
    public View getView(int position,View convertView,ViewGroup parent){
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView=inflater.inflate(idLayout,null);
        ImageView iamge =(ImageView)convertView.findViewById(R.id.image);
        TextView tvName =(TextView)convertView.findViewById(R.id.tv_name);
        TextView tvCode=(TextView)convertView.findViewById(R.id.tv_code);
        TextView tvSell =(TextView)convertView.findViewById(R.id.tv_sell);
        ImageView btnCall=(ImageView)convertView.findViewById(R.id.btn_call);
        tvName.setText(list.get(position).getCurrencyName());
        tvCode.setText(list.get(position).getCurrencyCode());
        tvSell.setText(list.get(position).getSell());

        String uri_icon = "drawable/" + list.get(position).getCurrencyCode().toLowerCase();
        int ImageResoure = convertView.getContext().getResources().getIdentifier(uri_icon, null, convertView.getContext().getApplicationContext().getPackageName());
        Drawable image = convertView.getContext().getResources().getDrawable(ImageResoure);
        iamge.setImageDrawable(image);
        return  convertView;
    }
}
