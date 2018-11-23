package com.example.luise.proyectochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListaChats extends AppCompatActivity  {

    @BindView(R.id.listChats)
    ListView listChats;
    ItemAdapter adapter;
    public static GetRequestMensaje getMensaje=new GetRequestMensaje();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chats);
        ButterKnife.bind(this);
        listChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item = position;
                Usuario itemval = (Usuario) listChats.getItemAtPosition(position);

                Contantes.usuarioConversacion=itemval.getUsuario();
                getMensaje.setContexto(ListaChats.this);
                getMensaje.execute("http://192.168.1.16:1234/mensajes/allmensajes");
                Intent SalaChat = new Intent(getApplicationContext(), ChatIndividual.class);
                startActivity(SalaChat);


            }

        });
    }

    @OnClick(R.id.btnCargar)
    public void onViewClicked() {

        adapter = new ItemAdapter(this, Contantes.listaChats);
        listChats.setAdapter(adapter);
    }

}
