package com.mohsin.soapcallnew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SoapCallWithoutSoapAction extends AppCompatActivity {
    TextView tvresult;
    EditText CountryISOCode;
    Button btnGetCapital;

    String Result;
    SoapObject ResultObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap_call_without_soap_action);

        tvresult = findViewById(R.id.tvresult);
        CountryISOCode = findViewById(R.id.CountryISOCode);
        btnGetCapital = findViewById(R.id.btnGetCapital);


        btnGetCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SoapCallWithoutSoapAction.MyTask mt = new SoapCallWithoutSoapAction.MyTask();
                mt.execute();
            }
        });
    }

    class MyTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            LoadData();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            parseXML();
        }
    }

    public void LoadData() {

//        http://dneonline.com/calculator.asmx?wsdl
        String wsdl_url = "http://192.168.105.11:8088/mockNumberConversionSoapBinding?WSDL";
        String name_space = "http://www.dataaccess.com/webservicesserver/";
        String method_name = "NumberToDollars";
        String soap_action = name_space + method_name;

        SoapObject soapObject = new SoapObject(name_space, method_name);

        soapObject.addProperty("dNum",32);


//        PropertyInfo intA = new PropertyInfo();
//        intA.setNamespace(name_space);
//        intA .setType(PropertyInfo.INTEGER_CLASS);
//        intA.setName("intA");
//        intA.setValue(32);
//        soapObject.addProperty(intA);
//
//        PropertyInfo intB = new PropertyInfo();
//        intB.setNamespace(name_space);
//        intB .setType(PropertyInfo.INTEGER_CLASS);
//        intB.setName("intB");
//        intB .setValue(92);
//        soapObject.addProperty(intB);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
/*
        envelope.dotNet = true;
*/
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE hts = new HttpTransportSE(wsdl_url);
        try {

            Log.d("test",soapObject.toString());
            Log.d("test2",envelope.toString());

            hts.call(soap_action, envelope);

            Log.d("response", envelope.bodyIn.toString());

            ResultObject =  (SoapObject) envelope.bodyIn;

//            Log.d("response",ResultObject.getPropertyCount()+"");
//            Log.d("response",ResultObject.getAttribute("NumberToDollarsResult")+"");
//            Log.d("response",ResultObject.getPropertyAsString("NumberToDollarsResult"));

            MyModel myModel = new MyModel(ResultObject.getPropertyAsString("NumberToDollarsResult"),ResultObject.getPropertyAsString("NumberToRupeesResult"),ResultObject.getPropertyAsString("NumberToRiyalResult"));

            Log.d("mymodel",myModel+"");


        } catch (IOException e) {
            Log.d("response", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.d("response", e.getLocalizedMessage());
        }
    }

    private void parseXML() {
//        SoapObject personObj = (SoapObject)response.getProperty(i);
        try {
            SAXParserFactory factory  = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(IOUtils.toInputStream(Result),new DefaultHandler()
            {
                String APIresult;
                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    super.characters(ch, start, length);
                    APIresult = new String(ch,start,length);
                }


                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    super.endElement(uri, localName, qName);
//                    if(qName.equals("AddResult"));
                    Toast.makeText(SoapCallWithoutSoapAction.this, APIresult, Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("result",e.getLocalizedMessage());
        }

    }
    }
