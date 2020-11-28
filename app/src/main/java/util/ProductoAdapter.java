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

import modelo.Producto;


public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> implements Filterable {

    public interface OnItemClickListener{
        void onAddClick(int position);
        void onRemoveClick(int position);
        void onAddCarrito(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtProducto, txtCantidad, txtPrecio;
        private ImageView imgProd, imgRemover, imgAgregar, imgAgregarCarrito;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtProducto = itemView.findViewById(R.id.txtProducto);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            imgProd = itemView.findViewById(R.id.imgProd);
            imgAgregar = itemView.findViewById(R.id.imgAgregar);
            imgRemover = itemView.findViewById(R.id.imgRemover);
            imgAgregarCarrito = itemView.findViewById(R.id.imgAgregarCarrito);

            imgAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddClick(position);
                        }
                    }
                }
            });

            imgRemover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRemoveClick(position);
                        }
                    }
                }
            });

            imgAgregarCarrito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onAddCarrito(position);
                        }
                    }
                }
            });
        }
    }

    private List<Producto> listProdCompleta;
    private List<Producto> listProd;
    private AppCompatActivity appCompatActivity;
    private int recurso;
    private ProductoAdapter.OnItemClickListener listener;

    public ProductoAdapter(AppCompatActivity context, int recurso, List<Producto> listProd) {
        this.recurso = recurso;
        this.appCompatActivity = context;
        this.listProd = listProd;
        this.listProdCompleta = new ArrayList<>(listProd);
    }

    @Override
    public Filter getFilter() {
        return filtrado;
    }

    private Filter filtrado = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Producto> listaFiltrada = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                listaFiltrada.addAll(listProdCompleta);
            } else {
                String filtrar = charSequence.toString().toLowerCase().trim();
                for (Producto e : listProdCompleta) {
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
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(recurso, parent, false);
        ProductoAdapter.ViewHolder viewHolder = new ProductoAdapter.ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StringBuilder concat = new StringBuilder();
        concat.append("$").append(listProd.get(position).getPrecio());
        holder.txtProducto.setText(listProd.get(position).getProducto());
        holder.txtPrecio.setText(concat);
        holder.txtCantidad.setText(String.valueOf(listProd.get(position).getCantComprar()));//cantidad sera seleccionada por cliente
        int imagen = appCompatActivity.getResources().getIdentifier(listProd.get(position).getImagenProducto(),
                "mipmap", appCompatActivity.getPackageName());
        holder.imgProd.setImageResource(imagen);
    }

    @Override
    public int getItemCount() {
        return listProd.size();
    }

    public void setOnItemClickListener(ProductoAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
