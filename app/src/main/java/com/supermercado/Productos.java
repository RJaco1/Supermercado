package com.supermercado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import DAOImpl.OrdenDAOImpl;
import DAOImpl.ProductoDAOImpl;
import dao.CRUD;
import modelo.Orden;
import modelo.Producto;
import util.ProductoAdapter;

public class Productos extends Fragment {

    private RecyclerView recView;

    private List<Producto> listProd;
    private CRUD dao, daoOrden;
    private ProductoAdapter prodAdapter;
    private int num, uId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("catNum", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                num = bundle.getInt("num");
                uId = bundle.getInt("user");
                listarProductos();
                prodAdapter.setOnItemClickListener(new ProductoAdapter.OnItemClickListener() {
                    @Override
                    public void onAddClick(int position) {
                        Integer cant = listProd.get(position).getCantComprar() + 1;
                        listProd.get(position).setCantComprar(cant);
                        prodAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onRemoveClick(int position) {
                        Integer cant = listProd.get(position).getCantComprar();
                        if (cant >= 2) {
                            cant -= 1;
                            listProd.get(position).setCantComprar(cant);
                        }
                        prodAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onAddCarrito(int position) {
                        Orden orden = new Orden();
                        orden.setCantidad(listProd.get(position).getCantComprar());
                        double precioT = (listProd.get(position).getCantComprar() * listProd.get(position).getPrecio());
                        orden.setPrecioTotal(precioT);
                        orden.setIdProducto(listProd.get(position).getIdProducto());
                        orden.setIdUsuario(uId);
                        daoOrden.registrar(orden);
                        Toast.makeText(getContext(), "El producto: " + listProd.get(position).getProducto() + " ha sido agregado a tu lista",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.productos_fragment, container, false);

        dao = new ProductoDAOImpl(getContext());
        daoOrden = new OrdenDAOImpl(getContext());

        FloatingActionButton fABtn = v.findViewById(R.id.fABtn);
        SearchView searchV = v.findViewById(R.id.idSV);
        recView = v.findViewById(R.id.rcView);

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

        fABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("user", uId);
                getParentFragmentManager().setFragmentResult("userId", bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new Carrito()).commit();
            }
        });

        return v;
    }

    public void listarProductos() {
        listProd = new ArrayList<>();
        listProd = dao.listarPorId(num);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        prodAdapter = new ProductoAdapter((AppCompatActivity) getContext(), R.layout.cliente_producto, listProd);
        recView.setAdapter(prodAdapter);
    }
}