package com.example.luise.proyectochat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostRequestMensaje extends AsyncTask<String, Void, String> {
    Mensaje mensaje=new Mensaje("","","","");
    String retorno="";
    ProgressDialog progressDialog;
    public Context contexto;

    public void setContexto(Context c){
        contexto=c;
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            retorno=postData(strings[0]);


            return retorno;

        } catch (IOException ex) {
            retorno="Network error !";
            return "Network error !";
        } catch (JSONException ex) {
            retorno="Data Invalid";
            return "Data Invalid !";
        }
    }
    private String postData(String urlPath) throws IOException, JSONException {

        StringBuilder result = new StringBuilder();
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {
            //Create data to send to server
            JSONObject dataToSend = new JSONObject();

            //post mensaje
            dataToSend.put("Emisor", mensaje.getEmisor());
            dataToSend.put("Receptor",mensaje.getReceptor());
            dataToSend.put("Contenido", mensaje.getContenido());
            dataToSend.put("Tipo", mensaje.getTipo());
            dataToSend.put("codCifrado", mensaje.getCodCifrado());


            //Initialize and config request, then connect to server.
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);  //enable output (body data)
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Write data into server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(dataToSend.toString());
            bufferedWriter.flush();

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
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }

        return result.toString();
    }
}
