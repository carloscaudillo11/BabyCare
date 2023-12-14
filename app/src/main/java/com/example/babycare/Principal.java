package com.example.babycare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Principal extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Bundle args;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        args = new Bundle();
        getDatos();
        Fragment fragment = null;
        fragment = new Inicio();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment1 = null;
            switch (item.getItemId()){
                case R.id.menu_home:
                    fragment1 = new Inicio();
                    fragment1.setArguments(args);
                    break;
                case R.id.menu_Alimentacion:
                    fragment1 = new Alimentacion();
                    fragment1.setArguments(args);
                    break;
                case R.id.menu_Sueño:
                    fragment1 = new Siesta();
                    fragment1.setArguments(args);
                    break;
                case R.id.menu_Guias:
                    fragment1 = new Guias();
                    fragment1.setArguments(args);
                    break;
                case R.id.menu_user:
                    fragment1 = new Perfil();
                    fragment1.setArguments(args);
                    break;
            }
            assert fragment1 != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
            return true;
        });
        /*if (getIntent() != null){
            Fragment fragment2 = null;
            fragment2 = new Guias();
            fragment2.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
        }*/
    }

    private void getDatos(){
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db.collection("usuario").document(id).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()){
                String apellido = documentSnapshot.getString("Apellidos");
                String nombre = documentSnapshot.getString("Nombre");
                String relacion = documentSnapshot.getString("relacion");
                args.putString("nombre", nombre);
                args.putString("apellido", apellido);
                args.putString("relacion", relacion);
            }
        }).addOnFailureListener(e -> {
            Log.e("Error", "No se pudo obtener los datos del usuario");
        });

        db.collection("bebe").document(id).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()){
                String nombre = documentSnapshot.getString("Nombre");
                String complicacion = documentSnapshot.getString("Complicacion");
                String peso = documentSnapshot.getString("Peso");
                String estatura = documentSnapshot.getString("Estatura");
                args.putString("nombreBebe", nombre);
                args.putString("complicacion", complicacion);
                args.putString("peso", peso);
                args.putString("estatura", estatura);
            }else{
                args.putString("sinBebe", "Sin bebes");
            }
        }).addOnFailureListener(e -> {
            Log.e("Error", "No se pudo obtener los datos del bebe");
        });


    }
}