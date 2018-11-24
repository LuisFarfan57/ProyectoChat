package com.example.luise.proyectochat;

import java.util.LinkedList;

public class ZigZag {
    public String Cifrar(String cadena, int clave){
        try{
            if(clave >= 2){
                LinkedList[] listas = new LinkedList[clave];
                for (int i = 0; i < listas.length; i++) {
                    listas[i] = new LinkedList();
                }

                String cadenaCifrada = "";

                int olasMinimas = 0, caracteresPorOla = 0, caracteresFaltantes = 0;
                caracteresPorOla = (clave * 2) - 2;

                if(cadena.length()%caracteresPorOla == 0){
                    olasMinimas = cadena.length() / caracteresPorOla;
                }
                else{
                    olasMinimas = (cadena.length() / caracteresPorOla) + 1;
                    caracteresFaltantes = caracteresPorOla - cadena.length()%caracteresPorOla;
                    for (int i = 0; i < caracteresFaltantes; i++) {
                        cadena = cadena + "¬";
                    }
                }

                int contadorCaracteres = 0;

                for (int i = 0; i < olasMinimas; i++) {
                    int nivel = 0;
                    boolean deRegreso = false;
                    for (int j = 0; j < caracteresPorOla; j++) {
                        listas[nivel].add(cadena.charAt(contadorCaracteres));
                        contadorCaracteres++;

                        if(nivel < clave-1 && !deRegreso){
                            nivel++;
                        }
                        else if(nivel == clave-1){
                            deRegreso = true;
                            nivel--;
                        }
                        else if(deRegreso){
                            nivel--;
                        }
                    }
                }

                for (int i = 0; i < listas.length; i++) {
                    for (int j = 0; j < listas[i].size(); j++) {
                        cadenaCifrada += listas[i].get(j);
                    }
                }

                return cadenaCifrada;
            }
            else{
                return "ERROR";
            }
        }
        catch (Exception e){
            return "ERROR";
        }
    }

    public String Descifrar(String cadena, int clave){
        try{
            if(clave >= 2){
                String cadenaDescifrada = "";

                LinkedList[] listas = new LinkedList[clave];
                for (int i = 0; i < listas.length; i++) {
                    listas[i] = new LinkedList();
                }

                int olasMinimas = 0, caracteresPorOla = 0, caracteresFaltantes = 0;
                caracteresPorOla = (clave * 2) - 2;

                if(cadena.length()%caracteresPorOla == 0){
                    olasMinimas = cadena.length() / caracteresPorOla;
                }
                else{
                    olasMinimas = (cadena.length() / caracteresPorOla) + 1;
                    caracteresFaltantes = caracteresPorOla - cadena.length()%caracteresPorOla;
                    for (int i = 0; i < caracteresFaltantes; i++) {
                        cadena = cadena + "¬";
                    }
                }

                for (int i = 0; i < olasMinimas; i++) {
                    listas[0].add(cadena.charAt(i));
                    listas[clave-1].add(cadena.charAt(cadena.length() - (olasMinimas - i)));
                }

                if(clave !=2){
                    int caracteresEnMedio = cadena.length() - (olasMinimas*2);
                    int tamañoMedio = caracteresEnMedio/(clave-2);
                    int contadorFilas = 1;

                    for (int i = olasMinimas; i < cadena.length() - olasMinimas; i++) {
                        if(listas[contadorFilas].size() < tamañoMedio){
                            listas[contadorFilas].add(cadena.charAt(i));
                        }
                        else if(listas[contadorFilas].size() == tamañoMedio){
                            contadorFilas++;
                            listas[contadorFilas].add(cadena.charAt(i));
                        }
                    }
                }

                for (int i = 0; i < olasMinimas; i++) {
                    int nivel = 0;
                    boolean deRegreso = false;
                    for (int j = 0; j < caracteresPorOla; j++) {
                        cadenaDescifrada += listas[nivel].getFirst();
                        listas[nivel].remove(0);

                        if(nivel < clave-1 && !deRegreso){
                            nivel++;
                        }
                        else if(nivel == clave-1){
                            nivel--;
                            deRegreso = true;
                        }
                        else if(deRegreso){
                            nivel--;
                        }
                    }
                }

                int contarExtra = 0;

               // for (int i = cadenaDescifrada.length() - 1; i > -1; i++) {
                 //   if(cadenaDescifrada.charAt(i) != '¬'){
                   //     break;
                  //  }
                   // else
                    //{
                      //  contarExtra++;
        //}
              // }
                //cadenaDescifrada = cadenaDescifrada.substring(0, (cadenaDescifrada.length() - contarExtra));
                cadenaDescifrada=cadenaDescifrada.replace("¬","");
                return cadenaDescifrada;
            }
            else{
                return "ERROR";
            }
        }
        catch(Exception e){
            return "ERROR";
        }
    }
}
