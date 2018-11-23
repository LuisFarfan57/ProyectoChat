package com.example.luise.proyectochat;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter {
    List<Usuario> listaUsuario=new ArrayList<>();
   Context activity ;

    public ItemAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, R.layout.activity_list, objects);
        this.activity=context;
        listaUsuario=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View item = inflater.inflate(R.layout.activity_list, null);


        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView nombre = (TextView) item.findViewById(R.id.txtNombreReceptor);
        nombre.setText(listaUsuario.get(position).getUsuario());


        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }
}
