package com.example.thanhtung.thanhtung_tiente;


/**
 * Created by thanhtung on 09/12/2016.
 */
public class DataCurrency {
    private String CurrencyCode;
    private String CurrencyName;
    private String Buy;
    private String Transfer;
    private String Sell;
    public DataCurrency(){};
    public String getCurrencyCode(){
        return CurrencyCode;
    }
    public String getCurrencyName(){
        return CurrencyName;
    }
    public String getBuy(){
        return Buy;
    }
    public String getTransfer(){
        return Transfer;
    }
    public String getSell(){
        return  Sell;
    }
    public void setCurrencyCode(String currencyCode){
        this.CurrencyCode = currencyCode;
    }
    public void setCurrencyName(String currentName){
        this.CurrencyName = currentName;
    }
    public void setBuy(String buy){
        this.Buy =buy;
    }
    public void setTransfer(String transfer){
        this.Transfer = transfer;
    }
    public void setSell(String sell){
        this.Sell = sell;
    }
}
