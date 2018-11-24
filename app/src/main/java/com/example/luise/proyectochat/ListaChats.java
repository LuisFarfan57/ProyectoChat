package com.example.luise.proyectochat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eliminar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Eliminar:
                DeleteRequest eliminar=new DeleteRequest();
                GetRequest obtenerUsuarios=new GetRequest();
                obtenerUsuarios.setContexto(ListaChats.this);
                obtenerUsuarios.execute("http://192.168.1.8:1234/usuarios/getusuarios");
                int ContEspera=0;
                while (!obtenerUsuarios.procesoTerminado){
                    //espera que termine peticion
                    ContEspera++;
                }
                String idEliminar=Verificacion.EncontrarUsuario(Contantes.usuarioenSesion,obtenerUsuarios.listaUsuarios);
                eliminar.setContexto(ListaChats.this);
                eliminar.execute("http://192.168.1.8:1234/usuarios/eliminar/"+idEliminar);
                Escritor.EscribirToken("usuarioinfo",ListaChats.this,"Vacio,Vacio");
                Intent intent3 = new Intent(ListaChats.this, MainActivity.class);
                startActivity(intent3);
                return true;
            case R.id.CerrarSesion:
                Escritor.EscribirToken("usuarioinfo",ListaChats.this,"Vacio,Vacio");
                Intent intent2 = new Intent(ListaChats.this, MainActivity.class);
                startActivity(intent2);
                return true;
            case R.id.Salir:
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
