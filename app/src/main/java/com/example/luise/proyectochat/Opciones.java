package com.example.luise.proyectochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Opciones extends AppCompatActivity {
    public static String idMensaje;
    public static String tipoMensaje;
    public static String contenido;
    @BindView(R.id.btnEliminar)
    Button btnEliminar;
    @BindView(R.id.btnDescargar)
    Button btnDescargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        ButterKnife.bind(this);
        if (tipoMensaje.equals("normal")) {
            btnDescargar.setEnabled(false);
        }
    }

    @OnClick({R.id.btnEliminar, R.id.btnDescargar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEliminar:
                DeleteRequest eliminar=new DeleteRequest();
                eliminar.setContexto(Opciones.this);
                eliminar.execute("http://192.168.1.8:1234/mensajes/"+idMensaje+"/delete");
                Intent intent3 = new Intent(Opciones.this,ChatIndividual.class);
                startActivity(intent3);
                break;
            case R.id.btnDescargar:
                break;
        }
    }
}
