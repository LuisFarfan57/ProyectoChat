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
 GetRequest nuevaRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuevaRequest=new GetRequest();
        setContentView(R.layout.activity_main);
        nuevaRequest.setContexto(MainActivity.this);
        nuevaRequest.execute("http://192.168.1.8:1234/usuarios/getusuarios");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnIngresar, R.id.btnCrearUsuario})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.btnIngresar:
                String usuario=txtUsuario.getText().toString();
                String contraseña=txtcontraseña.getText().toString();
                Contantes.listaChats.addAll(nuevaRequest.listaUsuarios);
               if(Verificacion.VerificarUsuario(usuario,contraseña,nuevaRequest.listaUsuarios)){
                    Contantes.usuarioenSesion=usuario;
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
