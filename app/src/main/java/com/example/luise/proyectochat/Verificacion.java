package com.example.luise.proyectochat;

import java.util.List;

public class Verificacion {
    public static boolean VerificarUsuario(String user, String password, List<Usuario> listaUsuarios) {
        boolean UsuarioVerificado=false;


    if(listaUsuarios!=null){
        for (int i=0;i<listaUsuarios.size();i++){
            if(listaUsuarios.get(i).getUsuario().equals(user)&&listaUsuarios.get(i).getContraseña().equals(password)){
                UsuarioVerificado=true;
            }
        }

    }
        return UsuarioVerificado;
    }
    public static boolean VerificarUsuarioExistente(String user, List<Usuario> listaUsuarios) {
        boolean UsuarioVerificado=false;
    if(listaUsuarios!=null){
        for (int i=0;i<listaUsuarios.size();i++){
            if(listaUsuarios.get(i).getUsuario().equals(user)){
                UsuarioVerificado=true;
            }
        }
    }
        return UsuarioVerificado;
    }
}
