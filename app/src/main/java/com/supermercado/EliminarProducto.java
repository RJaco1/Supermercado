package com.supermercado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DAOImpl.ProductoDAOImpl;
import dao.CRUD;
import modelo.Producto;
import util.EliminarAdapter;

public class EliminarProducto extends Fragment {

    private SearchView searchV;
    private RecyclerView recView;

    private List<Producto> listProd;
    private CRUD dao;
    private EliminarAdapter prodAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.eliminar_producto_fragment, container, false);

        dao = new ProductoDAOImpl(getContext());

        searchV = v.findViewById(R.id.idSV);
        recView = v.findViewById(R.id.rcView);

        listarProductos();

        searchV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                prodAdapter.getFilter().filter(s);
                return false;
            }
        });

        prodAdapter.setOnItemClickListener(new EliminarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), listProd.get(position).getProducto(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDeleteClick(int position) {
                int pos = listProd.get(position).getIdProducto();
                String prod = listProd.get(position).getProducto();
                dao.eliminar(pos);
                listProd.remove(position);
                Toast.makeText(getContext(), "Producto: " + prod + " se ha eliminado", Toast.LENGTH_LONG).show();
                prodAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    public void listarProductos() {
        listProd = new ArrayList<>();
        listProd = dao.listar();
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        prodAdapter = new EliminarAdapter((AppCompatActivity) getContext(), R.layout.admin_eliminar_producto, listProd);
        recView.setAdapter(prodAdapter);
    }
}