package com.example.luise.proyectochat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapterMensaje extends ArrayAdapter {
    List<Mensaje> listaMensaje=new ArrayList<>();
    Context activity ;
    public ItemAdapterMensaje(@NonNull Context context, @NonNull List objects) {
        super(context,  R.layout.activity_chat_item, objects);
        this.activity=context;
        listaMensaje=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View item = inflater.inflate(R.layout.activity_chat_item, null);


        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView nombre = (TextView) item.findViewById(R.id.txtUsuario);
        nombre.setText(listaMensaje.get(position).getEmisor());
        TextView mensaje = (TextView) item.findViewById(R.id.txtContenidoM);
        mensaje.setText(listaMensaje.get(position).getContenido());
        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }
}
