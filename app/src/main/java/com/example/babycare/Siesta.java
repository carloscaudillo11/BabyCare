package com.example.babycare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Siesta extends Fragment {
    private View vista;
    TextView titulo, adv;
    FloatingActionButton button;
    private RecyclerView recyclerView;
    private ArrayList<ListSiestas> SiestasArray;
    private SiestasAdapter SiestasAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match

    public Siesta() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_siesta, container, false);
        button = vista.findViewById(R.id.add);
        titulo = vista.findViewById(R.id.titulo);
        adv = vista.findViewById(R.id.adv);
        recyclerView = vista.findViewById(R.id.siestas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        SiestasArray = new ArrayList<>();
        SiestasAdapter = new SiestasAdapter(getContext(),SiestasArray);
        recyclerView.setAdapter(SiestasAdapter);
        button.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),RegistroSiesta.class));
        });
        if (getArguments() != null) {
            if (getArguments().getString("sinBebe") == null){
                titulo.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                mostrarSiestas();
            }else{
                adv.setVisibility(View.VISIBLE);
            }
        }
        return vista;
    }

    @SuppressLint("NotifyDataSetChanged")
    void mostrarSiestas(){
        String id = mAuth.getCurrentUser().getUid();
        db.collection("cicloSueño").whereEqualTo("id", id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Log.d("AQUI",document.getData()+"");
                                SiestasArray.add(document.toObject(ListSiestas.class));
                                SiestasAdapter.notifyDataSetChanged();
                            }
                        }else{
                            showAlert();
                        }
                    }
                });


    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error!");
        builder.setMessage("No se han podido obtener los datos, revisa tu conexion a internet");
        builder.setPositiveButton("Aceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}