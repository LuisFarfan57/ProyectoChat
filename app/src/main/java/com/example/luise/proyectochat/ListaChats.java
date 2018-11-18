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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GetRequest nuevaRequest=new GetRequest();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Usuario>>() {}.getType();
        nuevaRequest.execute("http://192.168.1.4:1234/usuarios/getusuarios");
        ArrayList<Usuario> listaUsuarios=new ArrayList<>();
        String res=nuevaRequest.Resultado;
        if(res!=""){
            listaUsuarios=gson.fromJson(res,type);
            ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this, android.R.layout.activity_list_item,listaUsuarios);
            listChats.setAdapter(adapter);
        }
        else{
            Toast.makeText(ListaChats.this,"Error de carga", Toast.LENGTH_SHORT).show();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chats);
        ButterKnife.bind(this);
    }
}
