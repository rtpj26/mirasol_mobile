package com.example.windows8.mirasoltiresupplyv3;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WINDOWS 8 on 9/29/2016.
 */
public class NetRequestAsync extends AsyncTask<Void, Void, Boolean> {

    String name, csr, loc, phone, vpass;
    String email, password, requisition;
    String id;
    int choice;

    public NetRequestAsync(String str1, String str2, int choice) {
        this.email = str1;
        this.password = str2;
        this.choice = 1;
        globalValues.wait = true;
    }

    public NetRequestAsync(String id, int choice) {
        this.id = id;
        this.choice = 3;
        globalValues.wait = true;
    }

    public NetRequestAsync(String str1, String str2, String str3, String str4, int choice) {
        this.name = str1;
        this.loc = str2;
        this.phone = str3;
        this.choice = 0;
        this.requisition = str4;
        globalValues.wait = true;
    }

    public NetRequestAsync(String name, String email, String phone, String pass, String vpass){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = pass;
        this.vpass = vpass;
        this.choice = 2;
        globalValues.wait = true;
        Log.d("Debug: ", name + ' ' + email + ' ' + phone + ' ' + password + ' ' + pass + ' ' + vpass);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        InputStream is;
        String result="";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                "http://mirasoltiresupply.com/php/rescue_driver.php?code=" + choice);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                    2);
            switch(choice){
                case 0:
                    nameValuePairs.add(new BasicNameValuePair("rName", name));
                    nameValuePairs.add(new BasicNameValuePair("rLoc", loc));
                    nameValuePairs.add(new BasicNameValuePair("rContact", phone));
                    nameValuePairs.add(new BasicNameValuePair("rReq", requisition));
                    break;
                case 1:
                    nameValuePairs.add(new BasicNameValuePair("semail", email));
                    nameValuePairs.add(new BasicNameValuePair("spass", password));
                    break;
                case 2:
                    nameValuePairs.add(new BasicNameValuePair("fname", name));
                    nameValuePairs.add(new BasicNameValuePair("email", email));
                    nameValuePairs.add(new BasicNameValuePair("pnum", phone));
                    nameValuePairs.add(new BasicNameValuePair("pass", password));
                    break;
                case 3:
                    nameValuePairs.add(new BasicNameValuePair("id", id));
                    break;
            }
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //httpclient.execute(httppost);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if(choice == 1 || choice == 2 || choice == 3) {
                is = entity.getContent();

                try {
                    BufferedReader httpResponseReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    String line = null;
                    String jsonString = "";
                    while ((line = httpResponseReader.readLine()) != null) {
                        jsonString += (line + "\n");
                    }

                    Log.d("JSON_STRING: ",jsonString);
                    JSONObject json_obj = new JSONObject(jsonString);
                    if(choice == 1) {
                        if (json_obj.getString("success").equals("false")) {
                            globalValues.user_Id = "-1";
                        } else {
                            globalValues.setAccountValues(json_obj.getString("id"), json_obj.getString("fname"),
                                    json_obj.getString("lname"), json_obj.getString("email"), json_obj.getString("address"),
                                    json_obj.getString("gender"), json_obj.getString("contact"));
                        }
                    }
                    else if(choice == 2){
                        Log.d("DEBUG: ",json_obj.getString("success") );
                        if (json_obj.getString("success").equals("true")) {
                            globalValues.lastStatus = true;
                        }else{
                            globalValues.lastStatus = false;
                        }
                    }else if(choice == 3){
                        JSONArray arr = new JSONArray();
                        arr = json_obj.getJSONArray("details");
                        Log.d("Values", arr.toString());

                        JSONObject json_obj_sub;

                        for(int i = 0; i < arr.length(); i++){
                            json_obj_sub =  new JSONObject(arr.get(i).toString());
                            Transaction t = new Transaction(json_obj_sub.getString("TRANSCTION_MOP"), json_obj_sub.getString("TEANSACTION_DATE"), json_obj_sub.getString("TRANSACTION_STATUS"));
                            globalValues.addTransaction(t);
                        }

                    }
                    is.close();
                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }
            }
            globalValues.wait = false;
            return true;
        } catch (ClientProtocolException e) {
                e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        globalValues.wait = false;
        return false;


    }


}
