package com.example.luise.proyectochat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static junit.framework.Assert.assertNotNull;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.txtUsuario)
    EditText txtUsuario;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.editText2)
    EditText txtcontraseña;
    @BindView(R.id.textView5)
    TextView textView5;
 GetRequest nuevaRequest;
    PostRequest RequestToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuevaRequest=new GetRequest();
        setContentView(R.layout.activity_main);
        nuevaRequest.setContexto(MainActivity.this);
        nuevaRequest.execute("http://192.168.0.13:1234/usuarios/getusuarios");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnIngresar, R.id.btnCrearUsuario})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.btnIngresar:
                String usuario=txtUsuario.getText().toString();
                String contraseña=Verificacion.getMD5(txtcontraseña.getText().toString());
               if(Verificacion.VerificarUsuario(usuario,contraseña,nuevaRequest.listaUsuarios)){
                   RequestToken=new PostRequest();
                   RequestToken.Token=true;
                   RequestToken.setContexto(MainActivity.this);
                   RequestToken.execute("http://192.168.0.13:1234/usuarios/signin");
                   while(!RequestToken.procesoTerminado){
                       int espera=+1;
                   }
                    Contantes.usuarioenSesion=usuario;
                    Gson deserializar=new Gson();
                    Token tok=deserializar.fromJson(RequestToken.retorno,Token.class);
                    String DatoEscribir=Contantes.usuarioenSesion+","+tok.token;
                    Escritor.EscribirToken("usuarioinfo",MainActivity.this,DatoEscribir);
                  Intent SalaChat = new Intent(getApplicationContext(), ListaChats.class);
                   startActivity(SalaChat);
               }else{
                   Toast.makeText(MainActivity.this,"Error de auntenticacion, Usuario o contraseña invalidos", Toast.LENGTH_SHORT).show();
               }

                break;
            case R.id.btnCrearUsuario:
                Intent CrearUsuario = new Intent(getApplicationContext(), CrearUsuario.class);
                startActivity(CrearUsuario);
                break;
        }
    }



}
class Token{
    @SerializedName("token")
    public String token="";
}
