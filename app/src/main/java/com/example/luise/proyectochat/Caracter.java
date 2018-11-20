package com.example.luise.proyectochat;

import java.util.Comparator;

public class Caracter {

        private char caracter;
        private float probabilidad;
        private String direccion;

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String dir) {
            direccion = dir;
        }

        public char getCaracter() {
            return caracter;
        }

        public void setCaracter(char caracter) {
            this.caracter = caracter;
        }

        public float getProbabilidad() {
            return probabilidad;
        }

        public void setProbabilidad(float probabilidad) {
            this.probabilidad = probabilidad;
        }


        public Caracter(char Caracter, float Probabilidad,String dir){
            caracter = Caracter;
            probabilidad = Probabilidad;
            direccion=dir;
        }
    }

    class CompareByProbabilidad implements Comparator<Caracter> {

        @Override

        public int compare(Caracter c1, Caracter c2) {

            if (c1.getProbabilidad() == c2.getProbabilidad()) {
                return 0;
            }
            else if (c1.getProbabilidad() < c2.getProbabilidad()) {
                return -1;
            }
            else{
                return 1;
            }

        }
    }
