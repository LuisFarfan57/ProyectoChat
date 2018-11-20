package com.example.luise.proyectochat;

import android.app.Application;
import android.net.Uri;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Huffman {
    String Cadena;
    String CadenaDescompresa;
    String[] TablaCerosAscii = new String[3];
    List<Caracter> tabla = new LinkedList<>();
    List<Nodo> tablaArbol = new LinkedList<>();
    String textoBinario;
    String textoAscii;
    Application application;
    int CerosExtra;
    private String nombreArchivo="";
    private Uri datosArchivo;

    public Huffman (Application app, Uri archivo)throws IOException{
        application = app;
        CerosExtra = 0;
        textoBinario = "";
        textoAscii = "";
        CadenaDescompresa = "";
        Cadena = Lector.LeerArchivo(application, archivo);
        datosArchivo=archivo;
    }

    public boolean ComprimirArchivo(){
        try {
            GenerarTabla();
            GenerarArbol();
            setDirecciones("",tablaArbol.get(0));
            textoBinario = CrearTextoBinario();
            textoAscii = TextoToAscii();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void GenerarTabla(){
        List<Character> CaracteresBuscados = new LinkedList<>();

        for (int i = 0; i < Cadena.length() ; i++)
        {
            if (!CaracteresBuscados.contains(Cadena.charAt(i))) {
                Float probabilidad = ((float)contarCaracter(Cadena.charAt(i))/(float)Cadena.length());
                Caracter car = new Caracter(Cadena.charAt(i), probabilidad,"");
                CaracteresBuscados.add(car.getCaracter());
                tabla.add(car);
            }
            else{
                continue;
            }
        }
    }

    private void GenerarArbol(){
        Collections.sort(tabla, new CompareByProbabilidad());

        for (Caracter car:tabla) {
            Nodo nodo = new Nodo(car);
            tablaArbol.add(nodo);
        }

        while(tablaArbol.size() > 1){
            encontrarMenores();
        }
    }

    private void encontrarMenores(){
        JuntarNodos(tablaArbol.get(0),tablaArbol.get(1));
    }

    private void JuntarNodos(Nodo n1, Nodo n2){
        float sumaProbabilidad = n1.getCaracter().getProbabilidad() + n2.getCaracter().getProbabilidad();
        Nodo padre = new Nodo(new Caracter('\u0000', sumaProbabilidad,""));
        padre.setHijoDerecho(n2);
        padre.setHijoIzquierdo(n1);

        n1.setPadre(padre);
        n2.setPadre(padre);

        tablaArbol.add(padre);
        tablaArbol.remove(n1);
        tablaArbol.remove(n2);

        Collections.sort(tablaArbol, new CompareByCaracter());
    }

    private int contarCaracter (char x){
        int cantidad = 0;

        for (int i = 0; i < (float)Cadena.length(); i++) {
            if (x == Cadena.charAt(i)) {
                cantidad++;
            }
        }

        return cantidad;
    }

    private void setDirecciones(String ubicacion, Nodo raiz){
        if(raiz.getCaracter().getCaracter()==('\u0000')){
            setDirecciones(ubicacion+"0", raiz.getHijoIzquierdo());
            setDirecciones(ubicacion+"1", raiz.getHijoDerecho());
        }
        else{
            raiz.getCaracter().setDireccion(ubicacion);
        }
    }

    private String CrearTextoBinario(){
        String texto="";
        for(int x=0;x<Cadena.length();x++){
            for (Caracter i: tabla) {
                if (Cadena.charAt(x) == i.getCaracter()){
                    texto+=i.getDireccion();
                    break;
                }
            }
        }

        CerosExtra = 8 - texto.length() % 8;

        if (CerosExtra != 8) {
            for (int i = 0; i < CerosExtra; i++) {
                texto = "0" + texto;
            }
        }
        else{
            CerosExtra = 0;
        }

        return texto;
    }

    private String TextoToAscii(){
        String textoAscii = "";
        int contador = 0;
        String ascii = "";
        int numero = 0;

        for (int i = 0; i < textoBinario.length(); i++) {
            contador++;
            if (contador <= 8) {
                ascii = ascii + textoBinario.charAt(i);
                if ((contador == 8)||(i == textoBinario.length() - 1)){
                    numero = Integer.parseInt(ascii,2);
                    textoAscii += (char)Integer.valueOf(numero).intValue();
                    contador = 0;
                    ascii = "";
                }
            }
        }

        return textoAscii;
    }

    public boolean GenerarArchivoBinario(Uri uri){
        if(Escritor.Escribir2(uri,application,textoBinario)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean GenerarArchivosCompresion(Uri uri){
        String ArchivoHuff = "";
        for (int i = 0; i < tabla.size(); i++) {
            ArchivoHuff += tabla.get(i).getCaracter() + "¬°" + tabla.get(i).getProbabilidad();

            if (i != tabla.size() - 1) {
                ArchivoHuff += "°~";
            }
        }

        ArchivoHuff += "~&";
        ArchivoHuff += CerosExtra;
        ArchivoHuff += "~&" + textoAscii;
        if(Escritor.Escribir2(uri,application,ArchivoHuff)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean Descomprimir(){
        try{
            TablaCerosAscii = Cadena.split("~&");
            GenerarTablaDescompresion(TablaCerosAscii[0]);
            CerosExtra = Integer.parseInt(TablaCerosAscii[1]);
            textoAscii = TablaCerosAscii[2];
            textoBinario = ExtraerBinariodeAscii(textoAscii);
            GenerarArbol();
            setDirecciones("", tablaArbol.get(0));
            QuitarCeros(textoBinario);
            CadenaDescompresa = ConvertirBinarioaAscii();

            return true;
        }catch(Exception e){
            return false;
        }
    }

    private String ExtraerBinariodeAscii(String CodigoAscii){
        String textoBinario = "";
        for (int i = 0; i < CodigoAscii.length(); i++) {
            String asciiBinario = Integer.toBinaryString(CodigoAscii.charAt(i));
            if (asciiBinario.length() % 8 != 0) {
                int CerosFaltantes = 8 - asciiBinario.length() % 8;
                for (int j = 0; j < CerosFaltantes; j++) {
                    asciiBinario = "0" + asciiBinario;
                }
            }
            textoBinario += asciiBinario;
        }
        return textoBinario;
    }

    private void GenerarTablaDescompresion(String cadenaTabla){
        String[] caracteres = cadenaTabla.split("°~");

        for (int i = 0; i < caracteres.length; i++) {
            String[] caracter = caracteres[i].split("¬°");
            tabla.add(new Caracter(caracter[0].charAt(0),Float.parseFloat(caracter[1]), ""));
        }
    }

    private void QuitarCeros(String cadenaBinaria){
        textoBinario = textoBinario.substring(CerosExtra);
    }

    private String ConvertirBinarioaAscii(){
        boolean hoja = false, fin = false;
        Nodo nodo = tablaArbol.get(0);
        int contador = 0;
        String numeroBinario = "";
        String cadena = "";

        while(!fin){
            while(!hoja){
                if ((nodo.getHijoIzquierdo() != null)||(nodo.getHijoDerecho() != null)) {
                    if(textoBinario.charAt(contador) == '0'){
                        nodo = nodo.getHijoIzquierdo();
                        numeroBinario += 0;
                        contador++;
                    }
                    else{
                        nodo = nodo.getHijoDerecho();
                        numeroBinario += 1;
                        contador++;
                    }
                }
                else{
                    cadena += nodo.getCaracter().getCaracter();
                    nodo = tablaArbol.get(0);
                    hoja = true;
                    numeroBinario = "";
                }
            }
            if (contador >= textoBinario.length() - 1) {
                fin = true;
            }
            else{
                hoja = false;
            }
        }

        return cadena;
    }

    public boolean GenerarArchivosDescompresion(Uri uri){
        if(Escritor.Escribir2(uri,application,CadenaDescompresa)){
            return true;
        }
        else{
            return false;
        }
    }
}
