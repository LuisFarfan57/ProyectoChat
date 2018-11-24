package com.example.luise.proyectochat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Opciones extends AppCompatActivity {
    public static String idMensaje;
    public static String tipoMensaje;
    public static String contenido;
    public static String nombreArchivo;
    @BindView(R.id.btnEliminar)
    Button btnEliminar;
    @BindView(R.id.btnDescargar)
    Button btnDescargar;

    Uri uri;

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
    public void onViewClicked(View view) throws IOException {
        switch (view.getId()) {
            case R.id.btnEliminar:
                DeleteRequest eliminar=new DeleteRequest();
                eliminar.setContexto(Opciones.this);
                eliminar.execute("http://192.168.0.13:1234/mensajes/"+idMensaje+"/delete");
                Intent intent3 = new Intent(Opciones.this,ChatIndividual.class);
                startActivity(intent3);
                break;
            case R.id.btnDescargar:
                if(nombreArchivo.contains(".txt")){
                    LZW lzw = new LZW(contenido);

                    contenido = lzw.Descomprimir();
                }
                ElegirRutaDescarga();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                uri = data.getData();
                if(Escritor.Escribir(uri, this.getApplication(), contenido)){
                    Toast.makeText(this.getApplicationContext(), "Archivo guardado", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this.getApplicationContext(), "Error al guardar el archivo", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void ElegirRutaDescarga(){
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("*/*");
        intent2.putExtra(Intent.EXTRA_TITLE, nombreArchivo);
        startActivityForResult(intent2, 0);
    }
}
