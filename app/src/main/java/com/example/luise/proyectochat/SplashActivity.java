package com.example.luise.proyectochat;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

public class SplashActivity extends AppCompatActivity {

    public PostRequest requestToken;
    Token tok;
    String datos[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       datos= Lector.leerToken("usuarioinfo",SplashActivity.this).split(",");
        requestToken=new PostRequest();
        requestToken.setContexto(SplashActivity.this);
        requestToken.VerificarToken=true;
        requestToken.token=datos[1];
        requestToken.execute("http://192.168.1.8:1234/usuarios/private");
        while (!requestToken.procesoTerminado){
            int num=0;
        }
        Gson deserializar=new Gson();
       tok=deserializar.fromJson(requestToken.retorno,Token.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                if(tok.token.equals("Tienes autorizacion")){
                    Contantes.usuarioenSesion=datos[0];
                    Intent SalaChat = new Intent(getApplicationContext(), ListaChats.class);
                    startActivity(SalaChat);
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        }, 2000);
    }
}
