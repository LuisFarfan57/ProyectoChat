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
    public static int EncontrarChatCod(String usuarioEmisor,String usuarioReceptor,List<Mensaje> lista){
        boolean llaveEncontrada=false;
        int pos=0;
        for (int i=0;i<lista.size();i++){
            if((usuarioEmisor.equals(lista.get(i).getEmisor())&&usuarioReceptor.equals(lista.get(i).getReceptor()))||(usuarioReceptor.equals(lista.get(i).getEmisor())&&usuarioEmisor.equals(lista.get(i).getReceptor()))){
                llaveEncontrada=true;
                pos=i;
                break;
            }
        }
        if(llaveEncontrada){
            return lista.get(pos).getCodCifrado();
        }
        else{
            return 0;
        }
    }
}
