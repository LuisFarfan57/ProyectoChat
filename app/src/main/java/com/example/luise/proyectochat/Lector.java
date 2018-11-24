package com.example.luise.proyectochat;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Lector {
    public static String LeerArchivo(Application application, Uri archivo) throws IOException {
        InputStream IS = application.getContentResolver().openInputStream(archivo);
        BufferedReader BR = new BufferedReader(new InputStreamReader(IS));
        StringBuilder SB = new StringBuilder();
        int line = 0;
        while((line = BR.read()) != -1)
        {
            char val = (char)line;
            SB.append(val);
        }

        IS.close();
        BR.close();
        return SB.toString();
    }

    public static String obtenerNombreDeArchivoDeUri(Application application, Uri uri) {
        String displayName = "";
        Cursor cursor = application.getApplicationContext().getContentResolver().query(uri, null, null, null, null, null);
        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } finally {
            cursor.close();
        }
        return displayName;
    }
    public static String leerToken(String nombre,Context contexto){
        try{
            BufferedReader fin = new BufferedReader(new InputStreamReader(contexto.openFileInput(nombre)));
            String texto = fin.readLine();
            fin.close();
            return texto;
        }
        catch (Exception ex)
        {

            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
            return "Error,123323478347834759837583457934797345";
        }
    }
}
