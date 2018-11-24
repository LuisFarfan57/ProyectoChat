package com.example.luise.proyectochat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteRequest extends AsyncTask<String,Void,String> {
    public String Resultado="";
    ProgressDialog progressDialog;
    public Context contexto;
    boolean procesoTerminado=false;
    public void setContexto(Context c){
        contexto=c;
    }
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Resultado=deleteData(strings[0].toString());
            procesoTerminado=true;
            return Resultado;
        } catch (IOException ex) {
            return "Network error !";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
        private String deleteData(String urlPath) throws IOException {

            String result =  null;

            //Initialize and config request, then connect to server.
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Check update successful or not
            if (urlConnection.getResponseCode() == 204) {
                result = "Delete successfully !";
            } else {
                result = "Delete failed !";
            }
            return result;
        }



}
