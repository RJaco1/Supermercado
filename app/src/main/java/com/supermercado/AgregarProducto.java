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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DAOImpl.CategoriaDAOImpl;
import DAOImpl.ProductoDAOImpl;
import dao.CRUD;
import modelo.Categoria;
import modelo.Producto;
import util.AdminAdapter;
import util.GridAdapter;

public class AgregarProducto extends Fragment {

    private EditText edtProduct, edtCant, edtPrecio;
    private Spinner spCat;
    private Button btnAgregar;
    private RecyclerView recView;

    private Integer catId;
    private String imgProd;
    private List<Categoria> listCat;
    private List<Producto> listProd;
    private CRUD dao, daoC;
    private Producto p;
    private AdminAdapter prodAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.agregar_producto_fragment, container, false);

        dao = new ProductoDAOImpl(getContext());
        daoC = new CategoriaDAOImpl(getContext());
        p = new Producto();
        listCat = new ArrayList<>();
        listCat = daoC.listar();

        edtProduct = v.findViewById(R.id.edtProducto);
        edtCant = v.findViewById(R.id.edtCantidad);
        edtPrecio = v.findViewById(R.id.edtPrecio);
        spCat = v.findViewById(R.id.spCategoria);
        btnAgregar = v.findViewById(R.id.btnAgregar);
        recView = v.findViewById(R.id.recyclerProductos);

        GridAdapter adapter = new GridAdapter((AppCompatActivity) getContext(), R.layout.spinner_view, listCat);
        spCat.setAdapter(adapter);

        listarProductos();

        spCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catId = listCat.get(i).getIdCategoria();
                imgProd = listCat.get(i).getImagenProducto();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(edtProduct.getText().toString().isEmpty() ||
                        edtCant.getText().toString().isEmpty() ||
                        edtPrecio.getText().toString().isEmpty())) {

                    p.setProducto(edtProduct.getText().toString().trim());
                    p.setCantidad(Integer.parseInt(edtCant.getText().toString().trim()));
                    p.setPrecio(Double.parseDouble(edtPrecio.getText().toString().trim()));
                    p.setImagenProducto(imgProd);
                    p.setIdCategoria(catId);
                    dao.registrar(p);
                    Toast.makeText(getContext(), "Producto: " + edtProduct.getText().toString() + " ha sido ingresado",
                            Toast.LENGTH_LONG).show();
                    limpiarCampos();
                    listarProductos();
                } else {
                    Toast.makeText(getContext(), "llene todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    public void limpiarCampos() {
        edtProduct.setText(null);
        edtCant.setText(null);
        edtPrecio.setText(null);
    }

    public void listarProductos() {
        listProd = new ArrayList<>();
        listProd = dao.listar();
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        prodAdapter = new AdminAdapter((AppCompatActivity) getContext(), R.layout.admin_producto, listProd);
        recView.setAdapter(prodAdapter);
    }
}