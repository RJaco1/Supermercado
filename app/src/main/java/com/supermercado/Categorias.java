package com.supermercado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import DAOImpl.CategoriaDAOImpl;
import dao.CRUD;
import modelo.Categoria;
import util.GridAdapter;

public class Categorias extends Fragment {

    private List<Categoria> listaCategoria;
    private CRUD dao;
    private GridView gv;
    private int idU;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("userId0", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                idU = bundle.getInt("usuar");
            }
        });
        getParentFragmentManager().setFragmentResultListener("userId", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                idU = bundle.getInt("user");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categorias_fragment, container, false);

        gv = v.findViewById(R.id.gridView);
        FloatingActionButton fABtn = v.findViewById(R.id.fABtn);

        dao = new CategoriaDAOImpl(getContext());
        listaCategoria = dao.listar();

        GridAdapter adaptador = new GridAdapter((AppCompatActivity) getContext(), R.layout.grid_view, listaCategoria);
        gv.setAdapter(adaptador);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("num", listaCategoria.get(i).getIdCategoria());
                bundle.putInt("user", idU);
                getParentFragmentManager().setFragmentResult("catNum", bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new Productos()).addToBackStack(null).commit();
            }
        });


        fABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("usuario", idU);
                getParentFragmentManager().setFragmentResult("user0", bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new Carrito()).addToBackStack(null).commit();
            }
        });

        return v;
    }
}