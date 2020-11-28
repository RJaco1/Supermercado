package util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.supermercado.R;

import java.util.ArrayList;
import java.util.List;

import modelo.CompraDetalle;
import modelo.Producto;

public class ItemCompraAdapter extends RecyclerView.Adapter<ItemCompraAdapter.ViewHolder> //implements Filterable
{

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtProducto, txtCantidad, txtPrecio, txtPrecioT;
        private ImageView imgView, imgEliminar;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtProducto = itemView.findViewById(R.id.txtProducto);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtPrecioT = itemView.findViewById(R.id.txtPrecioTProd);
            imgView = itemView.findViewById(R.id.imgView);
            imgEliminar = itemView.findViewById(R.id.imgEliminar);

            imgEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    private List<CompraDetalle> listDetalle;
    private List<CompraDetalle> listProdCompleta;
    private AppCompatActivity appCompatActivity;
    private OnItemClickListener listener;
    private int recurso;


    public ItemCompraAdapter(AppCompatActivity context, int resource, List<CompraDetalle> listDetalle) {
        this.listDetalle = listDetalle;
        this.listProdCompleta = new ArrayList<>(listDetalle);
        this.appCompatActivity = context;
        this.recurso = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(recurso, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StringBuilder concat = new StringBuilder();
        concat.append("$").append(listDetalle.get(position).getPrecio());
        holder.txtProducto.setText(listDetalle.get(position).getProducto());
        holder.txtCantidad.setText(String.valueOf(listDetalle.get(position).getCantidad()));
        holder.txtPrecio.setText(concat);
        holder.txtPrecioT.setText("$" + listDetalle.get(position).getPrecioTotal());
        int imagen = appCompatActivity.getResources().getIdentifier(listDetalle.get(position).getImagen(),
                "mipmap", appCompatActivity.getPackageName());
        holder.imgView.setImageResource(imagen);
    }

    @Override
    public int getItemCount() {
        return listDetalle.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
/*
    @Override
    public Filter getFilter() {
        return filtrado;
    }

    private Filter filtrado = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<CompraDetalle> listaFiltrada = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                listaFiltrada.addAll(listProdCompleta);
            } else {
                String filtrar = charSequence.toString().toLowerCase().trim();
                for (CompraDetalle e : listProdCompleta) {
                    if (e.getIdProducto().toString().toLowerCase().contains(filtrar) ||
                            e.getProducto().toLowerCase().contains(filtrar) ||
                            e.getCantidad().toString().toLowerCase().contains(filtrar) ||
                            String.valueOf(e.getPrecio()).toLowerCase().contains(filtrar)) {
                        listaFiltrada.add(e);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = listaFiltrada;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listProd.clear();
            listProd.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };*/
}
