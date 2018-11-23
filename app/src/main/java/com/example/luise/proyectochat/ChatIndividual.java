package com.example.luise.proyectochat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatIndividual extends AppCompatActivity {

    @BindView(R.id.txtUsuarioReceptor)
    TextView txtUsuarioReceptor;
    @BindView(R.id.txtMensaje)
    EditText txtMensaje;
    List<Mensaje> actualConversacion = new ArrayList<>();
    ItemAdapterMensaje adapter;
    @BindView(R.id.listChat)
    ListView listChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //txtUsuarioReceptor.setText(Contantes.usuarioConversacion);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnEnviar, R.id.btnActualizar, R.id.btnArchivo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEnviar:
                break;
            case R.id.btnActualizar:
                if(adapter!=null){
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    if(actualConversacion!=null){
                        actualConversacion.clear();
                    }
                }


                if (ListaChats.getMensaje.listaMensajes != null) {
                    for (int i = 0; i < ListaChats.getMensaje.listaMensajes.size(); i++) {
                        if ((ListaChats.getMensaje.listaMensajes.get(i).getEmisor().equals(Contantes.usuarioenSesion) && ListaChats.getMensaje.listaMensajes.get(i).getReceptor().equals(Contantes.usuarioConversacion)) || (ListaChats.getMensaje.listaMensajes.get(i).getEmisor().equals(Contantes.usuarioConversacion) && ListaChats.getMensaje.listaMensajes.get(i).getReceptor().equals(Contantes.usuarioenSesion))) {
                            actualConversacion.add(ListaChats.getMensaje.listaMensajes.get(i));
                        }
                    }
                }
                adapter = new ItemAdapterMensaje(this, actualConversacion);
                listChat.setAdapter(adapter);
                break;
            case R.id.btnArchivo:
                break;
        }
    }
}
