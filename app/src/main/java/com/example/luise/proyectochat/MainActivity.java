package com.example.luise.proyectochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        GetRequest nuevaRequest=new GetRequest();
        final Type tipoListaUsuarios = new TypeToken<List<Usuario>>(){}.getType();
        Gson gson = new Gson();
      //  Type type = new TypeToken<Usuario[]>() {}.getType();
        nuevaRequest.execute("http://192.168.1.4:1234/usuarios/getusuarios");
        String res=nuevaRequest.Resultado;
        res=res.replaceAll(",\"__v\":0","");
      //  res="{ \"usuario\":"+res+"}";
        if(res!=""){
             List<Usuario> listaUsuarios = gson.fromJson(res, tipoListaUsuarios);
            for (int i=0;i<listaUsuarios.size();i++){
                if(listaUsuarios.get(i).getUsuario().equals(user)&&listaUsuarios.get(i).getContraseña().equals(password)){
                    UsuarioVerificado=true;
                }
            }
        }
        else{
            Toast.makeText(MainActivity.this,"Error de auntenticacion", Toast.LENGTH_SHORT).show();
        }
        return UsuarioVerificado;
    }

}
