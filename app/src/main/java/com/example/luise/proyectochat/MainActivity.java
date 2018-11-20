package com.example.luise.proyectochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
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
    GetRequest nuevaRequest=new GetRequest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nuevaRequest.setContexto(MainActivity.this);
        nuevaRequest.execute("http://10.200.184.25:1234/usuarios/getusuarios");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnIngresar, R.id.btnCrearUsuario})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.btnIngresar:
                String usuario=txtUsuario.getText().toString();
                String contraseña=txtcontraseña.getText().toString();
               if(VerificarUsuario(usuario,contraseña)){
                  // Intent SalaChat = new Intent(getApplicationContext(), ListaChats.class);
                   //startActivity(SalaChat);
                   Toast.makeText(MainActivity.this,"to bien", Toast.LENGTH_SHORT).show();
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
    public boolean VerificarUsuario(String user,String password) {
        boolean UsuarioVerificado=false;


          if(nuevaRequest.Terminado!=false){
              for (int i=0;i<nuevaRequest.listaUsuarios.size();i++){
                  if(nuevaRequest.listaUsuarios.get(i).getUsuario().equals(user)&&nuevaRequest.listaUsuarios.get(i).getContraseña().equals(password)){
                      UsuarioVerificado=true;
                  }
              }
          }
        return UsuarioVerificado;
    }

}
