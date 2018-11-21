package com.example.luise.proyectochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrearUsuario extends AppCompatActivity {

    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.TextView3)
    TextView TextView3;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.txtNombre)
    EditText txtNombre;
    @BindView(R.id.txtApellido)
    EditText txtApellido;
    @BindView(R.id.txtCorreo)
    EditText txtCorreo;
    @BindView(R.id.txtUsuario)
    EditText txtUsuario;
    @BindView(R.id.txtContraseña)
    EditText txtClave;
    GetRequest RequestGet=new GetRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RequestGet.setContexto(CrearUsuario.this);
        RequestGet.execute("http://192.168.1.16:1234/usuarios/getusuarios");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnCrear, R.id.btnRegresar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCrear:
                Date fecha=new Date();
                Usuario usuarioNuevo=new Usuario(txtNombre.getText().toString(),txtApellido.getText().toString(),txtCorreo.getText().toString(),txtUsuario.getText().toString(),txtClave.getText().toString());
                if(!Verificacion.VerificarUsuarioExistente(usuarioNuevo.getUsuario(),RequestGet.listaUsuarios)){
                    PostRequest nuevoRequest=new PostRequest();
                    nuevoRequest.user=usuarioNuevo;
                    nuevoRequest.setContexto(CrearUsuario.this);
                    nuevoRequest.execute("http://192.168.1.16:1234/usuarios/signup");
                    Toast.makeText(CrearUsuario.this,"Usuario Creado", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CrearUsuario.this,"Usuario Existente", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnRegresar:
                Intent LogIn = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(LogIn);
                break;
        }
    }
}
