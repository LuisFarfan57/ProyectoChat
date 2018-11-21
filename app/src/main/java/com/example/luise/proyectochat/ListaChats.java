package com.example.luise.proyectochat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaChats extends AppCompatActivity {

    @BindView(R.id.listChats)
    ListView listChats;
    GetRequest GetUsuarios=new GetRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GetUsuarios.setContexto(ListaChats.this);
        GetUsuarios.execute("http://192.168.1.16:1234/usuarios/getusuarios");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chats);
        ButterKnife.bind(this);
    }
}
