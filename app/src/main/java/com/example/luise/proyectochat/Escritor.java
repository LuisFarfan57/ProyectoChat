package com.example.luise.proyectochat;

import android.app.Application;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;

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

    public static boolean Escribir(String cadena, int ver,String nombre) {
        try {
            String nuevo = "";
            String raiz = Environment.getExternalStorageDirectory().toString();

            switch (ver){
                case 0:
                    raiz += "/CompresionHuffman/";
                    nuevo = raiz +nombre+ "COMP.huf";
                    break;
                case 1:
                    raiz += "/CompresionHuffman/";
                    nuevo = raiz + nombre+"COMP.BIN";
                    break;
                case 2:
                    raiz += "/CompresionHuffman/";
                    nuevo = raiz + nombre+"DESCOMPRESS.txt";
                    break;
                case 3:
                    raiz += "/CompresionLZW/";
                    nuevo = raiz + nombre+"COMP.lzw";
                    break;
                case 4:
                    raiz += "/CompresionLZW/";
                    nuevo = raiz +nombre+ "DESCOMPRESS.txt";
                    break;
                case 5:
                    raiz += "/MisCompresiones/";
                    nuevo = raiz+ "MISCOMPRESIONES.txt";
                    break;
                default:
                    return false;
            }

            File directorio = new File(raiz);

            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            File archivo = new File(nuevo);

            if (!archivo.exists()) {
                archivo.createNewFile();
            } else {
                archivo.delete();
                archivo.createNewFile();
            }

            PrintWriter print = new PrintWriter(archivo);
            print.print(cadena);
            print.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

    }

    public static boolean Escribir2(Uri selectedFile, Application app, String cadena){
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
}
