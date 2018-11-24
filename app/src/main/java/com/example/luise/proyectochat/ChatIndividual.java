package com.example.luise.proyectochat;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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
    GetRequestMensaje mensaje;
    PostRequestMensaje enviar;
    ZigZag cifrar=new ZigZag();
    ArrayList<Mensaje> mensajesTemporal=new ArrayList<>();
    boolean ArchivoCargado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //txtUsuarioReceptor.setText(Contantes.usuarioConversacion);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);
        ButterKnife.bind(this);
        txtUsuarioReceptor.setText(Contantes.usuarioConversacion);
    }

    @OnClick({R.id.btnEnviar, R.id.btnActualizar, R.id.btnArchivo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEnviar:
                String tipo;
                String contenido="";
                if(ArchivoCargado){
                   tipo="archivo";
                   //poner el contenido del archivo ya comprimido
                   contenido="";
                }
                else{
                    txtMensaje.setEnabled(true);
                    tipo="normal";
                    contenido=txtMensaje.getText().toString();
                }
                //primero muestra el mensaje en listview
                Mensaje message=new Mensaje(Contantes.usuarioenSesion,Contantes.usuarioConversacion,contenido,tipo);
                enviar=new PostRequestMensaje();
              message.setCodCifrado(Contantes.CodigoCifradoActual);
                Toast.makeText(ChatIndividual.this,"Mensaje Enviado", Toast.LENGTH_SHORT).show();
                mensajesTemporal.add(message);
                adapter = new ItemAdapterMensaje(ChatIndividual.this, mensajesTemporal);
                listChat.setAdapter(adapter);
                //despues envia el mensaje a la base de datos
                enviar.mensaje=new Mensaje(Contantes.usuarioenSesion,Contantes.usuarioConversacion,txtMensaje.getText().toString(),tipo);
                enviar.mensaje.setCodCifrado(Contantes.CodigoCifradoActual);
                enviar.mensaje.setContenido(cifrar.Cifrar(contenido,Contantes.CodigoCifradoActual));
                enviar.setContexto(ChatIndividual.this);
                enviar.execute("http://192.168.1.8:1234/mensajes/enviar");
                txtMensaje.setText("");
                break;
            case R.id.btnActualizar:
                       mensaje=new GetRequestMensaje();
                        mensaje.setContexto(ChatIndividual.this);
                        try{
                            mensaje.execute("http://192.168.1.8:1234/mensajes/allmensajes");
                        }catch(Exception e){

                            Toast.makeText(ChatIndividual.this,"Vuelva a interntalo en un momento", Toast.LENGTH_SHORT).show();
                        }
                        int contCiclos=0;

                       while (!mensaje.procesoTerminado){

                           //Esperando que Inicie la peticiones
                           contCiclos++;
                       }

                if(adapter!=null){
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                }
                if(mensaje.listaMensajes!=null){
                    for (int i=0;i<mensaje.listaMensajes.size();i++){
                        if ((mensaje.listaMensajes.get(i).getEmisor().equals(Contantes.usuarioenSesion) && mensaje.listaMensajes.get(i).getReceptor().equals(Contantes.usuarioConversacion)) || (mensaje.listaMensajes.get(i).getEmisor().equals(Contantes.usuarioConversacion) && mensaje.listaMensajes.get(i).getReceptor().equals(Contantes.usuarioenSesion))) {
                            mensaje.listaMensajes.get(i).setContenido(cifrar.Descifrar(mensaje.listaMensajes.get(i).getContenido(),Contantes.CodigoCifradoActual));
                            actualConversacion.add(mensaje.listaMensajes.get(i));
                        }
                    }

                }
                adapter = new ItemAdapterMensaje(ChatIndividual.this, actualConversacion);
                listChat.setAdapter(adapter);

                break;
            case R.id.btnArchivo:
                //codido para cargar archivo y obtener contenido
                ArchivoCargado=true;
                txtMensaje.setEnabled(false);
                break;
        }
    }
}
