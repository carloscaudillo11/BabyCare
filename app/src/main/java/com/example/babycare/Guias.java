package com.example.babycare;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Guias extends Fragment {
    private View vista;
    private RecyclerView recyclerView;
    private TextView textView;
    private ArrayList<ListConsejos> ConsejosArray;
    private ConsejosAdapter consejosAdapter;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_guias, container, false);
        recyclerView=vista.findViewById(R.id.consejos);
        textView=vista.findViewById(R.id.text);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db = FirebaseFirestore.getInstance();
        ConsejosArray = new ArrayList<>();
        consejosAdapter = new ConsejosAdapter(getContext(),ConsejosArray);
        recyclerView.setAdapter(consejosAdapter);
        if (getArguments() != null) {
            if (getArguments().getString("sinBebe") == null){
                mostrarGenerales();
                String complicacion = getArguments().getString("complicacion");
                if (complicacion.equals("Bronquiolitis")){
                    mostrarBronquiolitis();
                }else if(complicacion.equals("Asma")){
                    mostrarAsma();
                }else if(complicacion.equals("Neumonia")){
                    mostrarNeumonia();
                }
            }else{
                textView.setVisibility(View.VISIBLE);
            }
        }
        return vista;
    }

    @SuppressLint("NotifyDataSetChanged")
    void mostrarGenerales(){
        db.collection("consejosPaternales").orderBy("autor", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null){
                        Log.w("Error", "Listen failed.", error);
                        showAlert();
                        return;
                    }
                    for (DocumentChange dc: value.getDocumentChanges()){
                        if (dc.getType() == DocumentChange.Type.ADDED){
                            ConsejosArray.add(dc.getDocument().toObject(ListConsejos.class));
                            consejosAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    @SuppressLint("NotifyDataSetChanged")
    void mostrarAsma(){
        db.collection("consejosPaternalesAsma").orderBy("autor", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null){
                        Log.w("Error", "Listen failed.", error);
                        showAlert();
                        return;
                    }
                    for (DocumentChange dc: value.getDocumentChanges()){
                        if (dc.getType() == DocumentChange.Type.ADDED){
                            ConsejosArray.add(dc.getDocument().toObject(ListConsejos.class));
                        }
                        consejosAdapter.notifyDataSetChanged();
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

    @SuppressLint("NotifyDataSetChanged")
    void mostrarBronquiolitis(){
        db.collection("consejosPaternalesBronquiolitis")
                .addSnapshotListener((value, error) -> {
                    if (error != null){
                        Log.w("Error", "Listen failed.", error);
                        showAlert();
                        return;
                    }
                    for (DocumentChange dc: value.getDocumentChanges()){
                        if (dc.getType() == DocumentChange.Type.ADDED){
                            ConsejosArray.add(dc.getDocument().toObject(ListConsejos.class));
                        }
                        consejosAdapter.notifyDataSetChanged();
                    }
                });
    }

    void mostrarNeumonia(){
        db.collection("consejosPaternalesNeumonia").orderBy("autor", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null){
                        Log.w("Error", "Listen failed.", error);
                        showAlert();
                        return;
                    }
                    for (DocumentChange dc: value.getDocumentChanges()){
                        if (dc.getType() == DocumentChange.Type.ADDED){
                            ConsejosArray.add(dc.getDocument().toObject(ListConsejos.class));
                        }
                        consejosAdapter.notifyDataSetChanged();
                    }
                });
    }

}