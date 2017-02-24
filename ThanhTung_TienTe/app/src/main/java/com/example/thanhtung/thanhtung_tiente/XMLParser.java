package com.example.thanhtung.thanhtung_tiente;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by thanhtung on 09/12/2016.
 */
public class XMLParser {
    private String urlString = null;
    private  ArrayList<String> arrstring= new ArrayList<String>();
    private XmlPullParserFactory xmlFactoryObject;
    private String DATE ="";
    public volatile boolean parsingComplete = true;
    private  ArrayList<String> arrListtuybien= new ArrayList<String>();
    private ArrayList<DataCurrency> arrList = new ArrayList<DataCurrency>();

    public XMLParser(String url) {
        // TODO Auto-generated constructor stub
        this.urlString = url;
    }
    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("DateTime")){
                            DATE += myParser.nextText();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                            if (name.equals("Exrate")) {
                            DataCurrency xmlParser = new DataCurrency();
                            xmlParser.setCurrencyCode(myParser.getAttributeValue(0));
                            xmlParser.setCurrencyName(myParser.getAttributeValue(1));
                            xmlParser.setBuy(myParser.getAttributeValue(2));
                            xmlParser.setTransfer(myParser.getAttributeValue(3));
                            xmlParser.setSell(myParser.getAttributeValue(4));
                            arrList.add(xmlParser);
                            arrstring.add(xmlParser.getCurrencyCode());
                            }
                        break;
                    default:
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        } catch (Exception ex) {}
    }
    public  String getDATE(){
        return DATE;
    }
    public ArrayList <String> getStringxml(){
        return arrstring;
    }
    public ArrayList<DataCurrency> getDataXML() {
        return arrList;
    }
    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();
                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(is, null);
                    parseXMLAndStoreIt(myparser);
                    is.close();
                } catch (Exception ex) {
                }
            }
        });
        thread.start();
    }
}
