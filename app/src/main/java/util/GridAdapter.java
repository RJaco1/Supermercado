package util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.supermercado.R;

import java.util.List;

import modelo.Categoria;

public class GridAdapter extends ArrayAdapter<Categoria> {

    private AppCompatActivity appCompatActivity;
    private int recurso;
    private List<Categoria> catLista;

    public GridAdapter(@NonNull AppCompatActivity context, int resource, @NonNull List<Categoria> lista) {
        super(context, resource, lista);
        this.appCompatActivity = context;
        this.recurso = resource;
        this.catLista = lista;
    }

    public View getView(int position, View contentView, ViewGroup parent) {
        return initView(position, contentView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View contentView, ViewGroup parent) {
        if (contentView == null) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            contentView = inflater.inflate(recurso, null);
        }

        ImageView imgView = contentView.findViewById(R.id.imgCateg);
        int imagen = appCompatActivity.getResources().getIdentifier(catLista.get(position).getImagenProducto(),
                "mipmap", appCompatActivity.getPackageName());
        imgView.setImageResource(imagen);

        /*ImageView imgE = contentView.findViewById(R.id.imgEliminar);
        imgE.setImageResource(R.drawable.ic_delete);*/

        TextView txtCategoria = contentView.findViewById(R.id.txtCateg);
        txtCategoria.setText(catLista.get(position).getCategoria());

        return contentView;
    }
}
