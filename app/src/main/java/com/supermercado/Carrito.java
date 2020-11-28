package com.supermercado;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import DAOImpl.OrdenDAOImpl;
import dao.OrdenDAO;
import modelo.CompraDetalle;
import util.ItemCompraAdapter;

public class Carrito extends Fragment {

    private TextView user, total;
    private RecyclerView recView;
    private Button btnCompra;

    private OrdenDAO dao;
    private List<CompraDetalle> listaCompra;
    private ItemCompraAdapter itemAdapter;
    private int idU;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("user0", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                idU = bundle.getInt("usuario");
                listarItemCompra();
                eliminarProd();
                completarCompra();

            }
        });
        getParentFragmentManager().setFragmentResultListener("userId", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                idU = bundle.getInt("user");
                listarItemCompra();
                eliminarProd();
                completarCompra();
            }
        });


        //Este call back permite regresar al fragmento de categorias presionando el boton back del dispositivo
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle arg = new Bundle();
                arg.putInt("usuar", idU);
                getParentFragmentManager().setFragmentResult("userId0", arg);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new Categorias()).remove(Carrito.this).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.carrito_fragment, container, false);
        dao = new OrdenDAOImpl(getContext());

        user = v.findViewById(R.id.txtUserName);
        recView = v.findViewById(R.id.rcView);
        total = v.findViewById(R.id.TotalPagar);
        btnCompra = v.findViewById(R.id.btnComprar);


        return v;
    }

    public void listarItemCompra() {

        listaCompra = new ArrayList<>();
        listaCompra = dao.listarCompra(idU);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemAdapter = new ItemCompraAdapter((AppCompatActivity) getContext(), R.layout.item_compra, listaCompra);
        recView.setAdapter(itemAdapter);
        String u = "";
        if (!(listaCompra.size() == 0)) {
            u = "Usuario: " + listaCompra.get(0).getUsuario();
        } else {
            u = "Carrito de compras vacio";
        }
        user.setText(u);
        mostrarTotal();
    }

    public void eliminarProd() {
        itemAdapter.setOnItemClickListener(new ItemCompraAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                int pos = listaCompra.get(position).getIdOrden();
                String prod = listaCompra.get(position).getProducto();
                dao.eliminar(pos);
                listaCompra.remove(position);
                Toast.makeText(getContext(), "Producto: " + prod + " se ha eliminado", Toast.LENGTH_LONG).show();
                itemAdapter.notifyDataSetChanged();
                mostrarTotal();
            }
        });
    }

    public void completarCompra() {
        btnCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.eliminarOrden(idU);
                Toast.makeText(getContext(), "Compra completada con exito", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void mostrarTotal() {
        double t = dao.mostrarTotal(idU);
        String tS = String.valueOf(formatoD(t));
        if (!(listaCompra.size() == 0)) {
            tS = "Total a pagar: " + tS;
        } else {

            tS = "---";
        }
        total.setText(tS);
    }

    public double formatoD(double n){
        DecimalFormat formato = new DecimalFormat("0000000.00");
        return Double.parseDouble(formato.format(n));
    }
}