package com.ms.ramya.sampleapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;


public class MainActivity extends ActionBarActivity {

    private ProgressDialog progressDialog;
    private ListView postList;
    private SuperData beanPostArrayList;
    private CustomListAdapter postAdapter;
    private EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_listview);
        postList=(ListView)findViewById(R.id.postList);
        searchText = (EditText)findViewById(R.id.searchView);
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }
            @Override
            protected Void doInBackground(Void... voids) {
                Reader reader= Util.getData(ApplicationConstants.REST_URL);
                Type listType = new TypeToken<SuperData>(){}.getType();
                beanPostArrayList = new Gson().fromJson(reader,listType);
                handleJsonData();
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.dismiss();
            }
        }.execute();


        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"]");

                postAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
    public void handleJsonData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                postAdapter=new CustomListAdapter(MainActivity.this,beanPostArrayList.getResponseData().getResults());
                postList.setAdapter(postAdapter);
            }
        });
    }




}
