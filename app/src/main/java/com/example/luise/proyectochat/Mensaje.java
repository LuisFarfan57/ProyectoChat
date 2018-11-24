package com.example.luise.proyectochat;

import com.google.gson.annotations.SerializedName;

public class Mensaje {
    @SerializedName("_id")
    private String _id;
    @SerializedName("Emisor")
    private String emisor;
    @SerializedName("Receptor")
    private String receptor;
    @SerializedName("Contenido")
    private String contenido;
    @SerializedName("Tipo")
    private String tipo;
    @SerializedName("codCifrado")
    private int codCifrado;
    public int getCodCifrado() {
        return codCifrado;
    }

    public void setCodCifrado(int codCifrado) {
        this.codCifrado = codCifrado;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Mensaje(String emisor, String receptor, String contenido, String tipo) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.contenido = contenido;
        this.tipo = tipo;
    }

}

