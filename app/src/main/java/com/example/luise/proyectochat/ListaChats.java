package com.example.luise.proyectochat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    public static  GetRequest getUser=new GetRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


                getMensaje.setContexto(ListaChats.this);
                getMensaje.execute("http://192.168.1.8:1234/mensajes/allmensajes");
                getUser.setContexto(ListaChats.this);
                getUser.execute("http://192.168.1.8:1234/usuarios/getusuarios");

        while(!getUser.procesoTerminado){
            int nada=0;
        }
        Contantes.listaChats=getUser.listaUsuarios;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chats);
        ButterKnife.bind(this);
         LlenarListView();
        listChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item = position;
                Usuario itemval = (Usuario) listChats.getItemAtPosition(position);

                Contantes.usuarioConversacion=itemval.getUsuario();
                int CodigoCifrar=2;
                int pos=0;
                boolean codExitse=true;
                if(getMensaje.listaMensajes.size()!=0){
                    int ResultadoBusqueda=Verificacion.EncontrarChatCod(Contantes.usuarioenSesion,Contantes.usuarioConversacion,getMensaje.listaMensajes);
                    if(ResultadoBusqueda!=0){
                        Contantes.CodigoCifradoActual=ResultadoBusqueda;
                    }else{
                        while(codExitse){
                            CodigoCifrar++;
                            for (int x=0;x<getMensaje.listaMensajes.size();x++){

                                if(CodigoCifrar==getMensaje.listaMensajes.get(x).getCodCifrado()){
                                    codExitse=true;
                                    break;
                                }
                                else{
                                    codExitse=false;
                                }
                            }
                        }
                        Contantes.CodigoCifradoActual=CodigoCifrar;
                    }
                }
                else{
                    Contantes.CodigoCifradoActual=2;
                }




                Intent SalaChat = new Intent(getApplicationContext(), ChatIndividual.class);
                startActivity(SalaChat);


            }

        });
    }

public void LlenarListView(){
    if(adapter!=null){
        adapter.clear();
        adapter.notifyDataSetChanged();
    }
    adapter = new ItemAdapter(this, Contantes.listaChats);
    listChats.setAdapter(adapter);
}
}
