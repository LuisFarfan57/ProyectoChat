package com.example.luise.proyectochat;

import java.util.Comparator;

public class Nodo {
    private Nodo Padre;
    private Nodo HijoDerecho;
    private Nodo HijoIzquierdo;
    private Caracter caracter;

    public Caracter getCaracter() {
        return caracter;
    }

    public void setCaracter(Caracter car) {
        caracter = car;
    }

    public Nodo getPadre() {
        return Padre;
    }

    public void setPadre(Nodo padre) {
        Padre = padre;
    }

    public Nodo getHijoDerecho() {
        return HijoDerecho;
    }

    public void setHijoDerecho(Nodo hijoDerecho) {
        HijoDerecho = hijoDerecho;
    }

    public Nodo getHijoIzquierdo() {
        return HijoIzquierdo;
    }

    public void setHijoIzquierdo(Nodo hijoIzquierdo) {
        HijoIzquierdo = hijoIzquierdo;
    }

    public Nodo (Caracter car/*, float p*/){
        Padre = null;
        HijoDerecho = null;
        HijoIzquierdo = null;
        caracter = car;
        //probabilidad = p;
    }

}

class CompareByCaracter implements Comparator<Nodo> {



    @Override

    public int compare(Nodo c1, Nodo c2) {

        if (c1.getCaracter().getProbabilidad() == c2.getCaracter().getProbabilidad()) {
            return 0;
        }
        else if (c1.getCaracter().getProbabilidad() < c2.getCaracter().getProbabilidad()) {
            return -1;
        }
        else{
            return 1;
        }

    }
}
