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
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {
    TextView tvresult;
    EditText CountryISOCode;
    Button btnGetCapital;

    String Result;
    SoapObject reslut2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvresult = findViewById(R.id.tvresult);
        CountryISOCode = findViewById(R.id.CountryISOCode);
        btnGetCapital = findViewById(R.id.btnGetCapital);


        btnGetCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyTask mt = new MyTask();
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


        String wsdl_url = "http://dneonline.com/calculator.asmx?wsdl";
        String name_space = "http://tempuri.org/";
        String method_name = "Add";
        String soap_action = name_space + method_name;

        SoapObject soapObject = new SoapObject(name_space, method_name);


        PropertyInfo intA = new PropertyInfo();
        intA.setNamespace(name_space);
        intA .setType(PropertyInfo.INTEGER_CLASS);
        intA.setName("intA");
        intA.setValue(32);
        soapObject.addProperty(intA);

        PropertyInfo intB = new PropertyInfo();
        intB.setNamespace(name_space);
        intB .setType(PropertyInfo.INTEGER_CLASS);
        intB.setName("intB");
        intB .setValue(92);
        soapObject.addProperty(intB);

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

            Log.d("response", envelope.bodyOut.toString());
            Log.d("response", envelope.bodyIn.toString());

            Result = envelope.bodyIn.toString();

            reslut2 =  (SoapObject) envelope.bodyIn;

            Result =  reslut2.getProperty(0).toString();

            Log.d("response",Result);



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
                    Toast.makeText(MainActivity.this, APIresult, Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("result",e.getLocalizedMessage());
        }

    }
}

class Add implements KvmSerializable {
    int intA;
    int intB;

    public int getIntA() {
        return intA;
    }

    public void setIntA(int intA) {
        this.intA = intA;
    }

    public int getIntB() {
        return intB;
    }

    public void setIntB(int intB) {
        this.intB = intB;
    }


    @Override
    public Object getProperty(int index) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int index, Object value) {

    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {

    }
}