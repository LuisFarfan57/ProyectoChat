package com.example.luise.proyectochat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetRequest extends AsyncTask<String,Void,String>{
    public String Resultado="";
    ProgressDialog progressDialog;
    public Context contexto;
    public ArrayList<Usuario> listaUsuarios;
    boolean procesoTerminado=false;

    public void setContexto(Context c){
        contexto=c;
    }
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        listaUsuarios=new ArrayList<>();
    }

    @Override
    protected String doInBackground(String[] params) {


        try {
            Resultado=getData(params[0].toString());
            Type tipoListaUsuarios = new TypeToken<ArrayList<Usuario>>(){}.getType();
            Gson gson = new Gson();
            listaUsuarios= gson.fromJson(Resultado, tipoListaUsuarios);
            procesoTerminado=true;
            return Resultado;
        } catch (IOException ex) {
            return "Network error !";
        }

    }

    @Override
    protected void onPostExecute(String o) {

        //cancel progress dialog
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        super.onPostExecute(o.toString());
    }
    private String getData(String urlPath) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader =null;

        try {
            //Initialize and config request, then connect to server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Read data response from server
            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }

        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return result.toString();
    }
}
