package com.example.babycare;

import android.annotation.SuppressLint;
import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends Fragment {
    private FirebaseAuth mAuth;
    private View vista;
    private TextView logout,usuario,registro,relacion_Text;
    private ProgressBar progressCerrar;
    private String apellido;
    private String nombre;
    private String relacion;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        vista = inflater.inflate(R.layout.fragment_perfil, container, false);
        logout = vista.findViewById(R.id.cerrar);
        usuario = vista.findViewById(R.id.usuario);
        relacion_Text = vista.findViewById(R.id.relacion);
        progressCerrar = vista.findViewById(R.id.progressCerrar);
        registro = vista.findViewById(R.id.register_baby);
        if (getArguments() != null) {
            nombre = getArguments().getString("nombre");
            apellido = getArguments().getString("apellido");
            relacion = getArguments().getString("relacion");
            if (getArguments().getString("sinBebe") == null){
                String bebe = getArguments().getString("nombreBebe");
                relacion_Text.setText(relacion+" de "+bebe);

            }else{
                relacion_Text.setText(relacion);
            }
        }
        registro.setOnClickListener(view -> prohibir());
        logout.setOnClickListener(view -> logOut());
        usuario.setText(nombre +" "+ apellido);
        return vista;
    }

    private void prohibir(){
        if (getArguments().getString("sinBebe") == null){
            showAlert();
            registro.setEnabled(false);
        }else{
            startActivity(new Intent(getContext(), Bebes.class));
        }
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia!");
        builder.setMessage("Ya se ha registrado un bebé");
        builder.setPositiveButton("Aceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("User", "" + currentUser);
    }

    public void logOut(){
        progressCerrar.setVisibility(View.VISIBLE);
        mAuth.signOut();
        startActivity(new Intent(getContext(), Login.class));
    }
}