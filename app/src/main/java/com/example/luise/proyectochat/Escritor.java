package com.example.luise.proyectochat;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class Escritor {
    private static Charset UTF8 = Charset.forName("UTF-8");

    public static boolean Escribir(Uri selectedFile, Application app, String cadena){
        try{
            ParcelFileDescriptor file = app.getContentResolver().openFileDescriptor(selectedFile, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(file.getFileDescriptor());
            Writer writer = new OutputStreamWriter(fileOutputStream, UTF8);
            writer.write(cadena);
            writer.flush();
            writer.close();
            fileOutputStream.close();
            file.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public static void EscribirToken(String nombre, Context contexto,String contenido){
        try {
            OutputStreamWriter fout= new OutputStreamWriter(contexto.openFileOutput(nombre, Context.MODE_PRIVATE));
            fout.write(contenido);
            fout.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }

    }
}
