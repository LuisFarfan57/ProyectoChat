package com.example.luise.proyectochat;

import android.app.Application;
import android.net.Uri;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class LZW {
    List<String> tabla = new LinkedList<>();
    List<String> tablaInicial = new LinkedList<>();
    List<String> tablaNumeros = new LinkedList<>();
    int cerosExtraTodo, cerosTabla;
    String Cadena;
    String textoCodificado;
    String textoBinario;
    String textoDecodificado;
    String[] tablaAscii;
    Uri datosArchivo;
    Application app2;

    LZW (Application app, Uri archivo)throws IOException {
        app2 = app;
        Cadena = Lector.LeerArchivo(app, archivo);
        textoCodificado = "";
        textoDecodificado = "";
        textoBinario = "";
        datosArchivo=archivo;
        cerosExtraTodo = 0;
        cerosTabla = 0;
    }

    public boolean Comprimir(){
        try{
            GenerarTablaInicial(Cadena);
            LZWCompresion(0,Cadena);
            CompletarBinarios();
            GenerarBinarioCompleto();
            BinarioAscii();

            return true;
        }catch(Exception e){
            return false;
        }
    }

    private void GenerarTablaInicial(String texto){
        for (int i = 0; i < texto.length(); i++) {
            //StringBuilder stringBuilder = new StringBuilder();
            //stringBuilder.append(texto.charAt(i));

            if(tablaInicial.contains(Character.toString(texto.charAt(i)))){
                continue;
            }
            else{
                tablaInicial.add(Character.toString(texto.charAt(i)));
                tabla.add(Character.toString(texto.charAt(i)));
            }
        }
    }

    private void LZWCompresion (int n, String texto)
    {
        if(n < (texto.length())){
            //StringBuilder stringBuilder = new StringBuilder();

            if(n == texto.length() - 1){
                //stringBuilder.append(texto.charAt(n));
                String s = Character.toString(texto.charAt(n));
                tablaNumeros.add(Integer.toBinaryString(tabla.indexOf(s)));
            }
            else{
                //stringBuilder.append(texto.charAt(n));
                String s = Character.toString(texto.charAt(n));

                //stringBuilder.delete(0,stringBuilder.length());

                //stringBuilder.append(texto.charAt(n+1));
                String siguiente = Character.toString(texto.charAt(n + 1));

                //stringBuilder.delete(0,stringBuilder.length());

                if (!tabla.contains(s + siguiente))
                {
                    tabla.add(s+siguiente);
                    tablaNumeros.add(Integer.toBinaryString(tabla.indexOf(s)));

                    LZWCompresion(n+1, texto);
                }
                else
                {
                    while((tabla.contains(s+siguiente))&&(siguiente != ""))
                    {
                        s = s+siguiente;

                        if(n+2<texto.length()){
                            //stringBuilder.append(texto.charAt(n+2));
                            siguiente = Character.toString(texto.charAt(n + 2));
                        }
                        else{
                            siguiente = "";
                        }

                        //stringBuilder.delete(0,stringBuilder.length());

                        n++;
                    }

                    if(siguiente != ""){
                        tabla.add(s+siguiente);
                    }

                    tablaNumeros.add(Integer.toBinaryString(tabla.indexOf(s)));

                    LZWCompresion(n+1, texto);
                }
            }
        }
    }

    private void CompletarBinarios(){
        String numeroMaximo = Integer.toBinaryString(tabla.size() - 1);
        cerosTabla = numeroMaximo.length();

        for (int i = 0; i < tablaNumeros.size(); i++) {
            String numero = tablaNumeros.get(i);
            int cerosFaltantes = cerosTabla - numero.length() % cerosTabla;

            if(cerosFaltantes != cerosTabla){
                for (int j = 0; j < cerosFaltantes; j++) {
                    numero = "0" + numero;
                }

                tablaNumeros.set(i,numero);
            }
        }
    }

    private void GenerarBinarioCompleto(){
        for (String numero:tablaNumeros) {
            textoBinario+=numero;
        }
    }


    private void BinarioAscii(){
        int contador = 0;
        String ascii = "";
        int numero = 0;

        cerosExtraTodo = 8 - textoBinario.length()%8;

        if(cerosExtraTodo != 8){
            for (int i = 0; i < cerosExtraTodo; i++) {
                textoBinario = "0" + textoBinario;
            }
        }
        else{
            cerosExtraTodo = 0;
        }

        for (int i = 0; i < textoBinario.length(); i++) {
            contador++;
            ascii = ascii + textoBinario.charAt(i);
            if (contador == 8) {
                numero = Integer.parseInt(ascii, 2);
                textoCodificado += Character.toChars(numero);
                contador = 0;
                ascii = "";
            }
        }
    }

    public boolean GenerarArchivosCompresion(Uri uri){
        String ArchivoLZW = "";
        for (int i = 0; i < tablaInicial.size(); i++) {
            ArchivoLZW += tablaInicial.get(i);

            if (i != tablaInicial.size() - 1) {
                ArchivoLZW += "°~";
            }
        }

        ArchivoLZW += "~&" + cerosExtraTodo + "~&" +  cerosTabla + "~&" + textoCodificado;
        if(Escritor.Escribir(uri,app2,ArchivoLZW)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean Descomprimir(){
        try{
            tablaAscii = Cadena.split("~&");
            LeerTablaInicial(tablaAscii[0]);
            cerosExtraTodo = Integer.parseInt(tablaAscii[1]);
            cerosTabla = Integer.parseInt(tablaAscii[2]);
            GenerarBinario(tablaAscii[3]);
            GenerarTablaNumeros();
            DescompresionLZW();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void LeerTablaInicial(String cadenaTabla){
        String[] caracteres = cadenaTabla.split("°~");

        for (int i = 0; i < caracteres.length; i++) {
            tablaInicial.add(caracteres[i]);
            tabla.add(caracteres[i]);
        }
    }

    private void GenerarBinario(String cadenaAscii){
        for (int i = 0; i < cadenaAscii.length(); i++) {
            int numero = cadenaAscii.charAt(i);
            //String asciiBinario = Integer.toBinaryString(cadenaAscii.charAt(i));
            String asciiBinario = Integer.toBinaryString(numero);
            if (asciiBinario.length() % 8 != 0) {
                int CerosFaltantes = 8 - asciiBinario.length() % 8;
                for (int j = 0; j < CerosFaltantes; j++) {
                    asciiBinario = "0" + asciiBinario;
                }
            }
            textoBinario += asciiBinario;
        }

        textoBinario = textoBinario.substring(cerosExtraTodo);
    }

    private void GenerarTablaNumeros(){
        int contador = 0;
        String numero = "";
        for (int i = 0; i < textoBinario.length(); i++) {
            numero += textoBinario.charAt(i);
            contador++;

            if(contador == cerosTabla){
                tablaNumeros.add(numero);
                contador = 0;
                numero = "";
            }
        }
    }

    private void DescompresionLZW(){
        String anterior = "";
        String actual = "";

        for (int i = 0; i < tablaNumeros.size(); i++) {
            if(i == 0){
                String numeroBinario = tablaNumeros.get(i);
                actual = tabla.get(Integer.parseInt(numeroBinario,2));

                textoDecodificado+=actual;
            }
            else{

                String numeroBinario = tablaNumeros.get(i-1);
                anterior = tabla.get(Integer.parseInt(numeroBinario,2));

                numeroBinario = tablaNumeros.get(i);
                actual = tabla.get(Integer.parseInt(numeroBinario,2));

                tabla.add(anterior + actual.charAt(0));
                textoDecodificado+=actual;

            }
        }
    }

    public boolean GenerarArchivosDescompresion(Uri uri){
        if(Escritor.Escribir(uri,app2,textoDecodificado)){
            return true;
        }
        else{
            return false;
        }
    }

}
