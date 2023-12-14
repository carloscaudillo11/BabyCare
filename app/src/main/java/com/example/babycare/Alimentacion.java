package com.example.babycare;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class Alimentacion extends Fragment {
    private View vista;
    TextView titulo, adv;
    FloatingActionButton button;
    private RecyclerView recyclerView;
    private ArrayList<ListComidas> ComidasArray;
    private ComidasAdapter ComidasAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public Alimentacion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_alimentacion, container, false);
        button = vista.findViewById(R.id.add);
        titulo = vista.findViewById(R.id.titulo);
        adv = vista.findViewById(R.id.adv);
        recyclerView = vista.findViewById(R.id.comidas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        ComidasArray = new ArrayList<>();
        ComidasAdapter = new ComidasAdapter(getContext(),ComidasArray);
        recyclerView.setAdapter(ComidasAdapter);
        button.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),RegistroAlimentar.class));
        });
        if (getArguments() != null) {
            if (getArguments().getString("sinBebe") == null){
                titulo.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                mostrarComidas();
            }else{
                adv.setVisibility(View.VISIBLE);
            }
        }
        return vista;
    }

    @SuppressLint("NotifyDataSetChanged")
    void mostrarComidas(){
        String id = mAuth.getCurrentUser().getUid();
        db.collection("cicloAlimentacion").whereEqualTo("id", id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Log.d("AQUI",document.getData()+"");
                                ComidasArray.add(document.toObject(ListComidas.class));
                                ComidasAdapter.notifyDataSetChanged();
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