package com.example.luise.proyectochat;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                PostRequest nuevoRequest=new PostRequest();
                nuevoRequest.user=usuarioNuevo;
                nuevoRequest.execute("http://10.200.184.25:1234/usuarios/signup");
                Toast.makeText(CrearUsuario.this,"Usuario Creado", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnRegresar:
                break;
        }
    }
}
