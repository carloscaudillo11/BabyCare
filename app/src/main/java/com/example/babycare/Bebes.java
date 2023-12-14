package com.example.babycare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Bebes extends AppCompatActivity {
    TextInputEditText name, peso, fechaNac, estatura;
    TextInputLayout text_name, text_peso, text_fechaNac, text_estatura,text_genero, text_comp;
    ProgressBar progressBar;
    AutoCompleteTextView genero, comp;
    ArrayAdapter<String> adapter1, adapter2;
    private ImageView backConf;
    private String select_genero, select_comp;
    DatePickerDialog.OnDateSetListener setListener;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private final String[] complicaciones = {"Ninguna", "Asma", "Neumonia", "Bronquiolitis", "Otra..."};
    private final String[] generos = {"Niño", "Niña"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebes);
        backConf = findViewById(R.id.backConf);
        name = findViewById(R.id.name);
        peso = findViewById(R.id.peso);
        fechaNac = findViewById(R.id.fechaNac);
        estatura = findViewById(R.id.estatura);
        genero = findViewById(R.id.genero);
        comp = findViewById(R.id.padece);
        text_name = findViewById(R.id.text_name);
        text_peso = findViewById(R.id.text_peso);
        text_fechaNac = findViewById(R.id.text_fechaNac);
        text_estatura = findViewById(R.id.text_estatura);
        text_genero = findViewById(R.id.text_genero);
        text_comp = findViewById(R.id.text_comp);
        progressBar = findViewById(R.id.bebe_progressBar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();
        fechaNac.setOnFocusChangeListener((view, b) -> {
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (b) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year,
                        month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                        Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = (datePicker, year, month, day) -> {
            month++;
            String date = day + "/" + month + "/" + year;
            fechaNac.setText(date);
        };

        backConf.setOnClickListener(view -> {
            finish();
        });

        adapter1 = new ArrayAdapter<>(this, R.layout.list_relacion, generos);
        genero.setAdapter(adapter1);
        genero.setOnItemClickListener((adapterView, view, i, l) ->
                select_genero = adapterView.getItemAtPosition(i).toString());

        adapter2 = new ArrayAdapter<String>(this,R.layout.list_relacion,complicaciones);
        comp.setAdapter(adapter2);
        comp.setOnItemClickListener((adapterView, view, i, l) ->
                select_comp = adapterView.getItemAtPosition(i).toString());
    }

    private void register_baby(String nombre, String fecha_nac, String genero, String peso,
                               String estatura, String complicacion) {
        progressBar.setVisibility(View.VISIBLE);
        Map<String, Object> bebe = new HashMap<>();
        bebe.put("Nombre", nombre);
        bebe.put("Fecha de Nacimiento", fecha_nac);
        bebe.put("Genero", genero);
        bebe.put("Peso", peso);
        bebe.put("Estatura", estatura);
        bebe.put("Complicacion", complicacion);
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db.collection("bebe").document(id).set(bebe).addOnSuccessListener(unused -> {
            Log.d("Baby", "Registro exitoso");
            finish();
        }).addOnFailureListener(e -> showAlert());

    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Ocurrio un error y no se pudo registrar su bebé");
        builder.setPositiveButton("Aceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean esVacio(String nombre, String fecha_nac, String peso, String estatura) {
        boolean bandera = true;
        if(nombre.isEmpty() || !nombre.matches("[a-zA-Z\\s]+")) {
            bandera = false;
            text_name.setError("Debe ingresar el nombre del bebé");
        }else
            text_name.setError(null);

        if(fecha_nac.isEmpty()) {
            bandera = false;
            text_fechaNac.setError("Debe ingresar la fecha de nacimiento del bebé");
        }else
            text_fechaNac.setError(null);


        if(select_genero == null) {
            bandera = false;
            text_genero.setError("Debe ingresar el genero del bebé");
        }else
            text_genero.setError(null);

        if(select_comp == null) {
            bandera = false;
            text_comp.setError("Debe ingresar el genero del bebé");
        }else
            text_comp.setError(null);

        if(peso.isEmpty()) {
            bandera = false;
            text_peso.setError("Debe ingresar el peso del bebé");
        }else
            text_peso.setError(null);

        if(estatura.isEmpty()) {
            bandera = false;
            text_estatura.setError("Debe ingresar la estatura del bebé");
        }else
            text_estatura.setError(null);

        return bandera;
    }

    public void babyButton(View view) {
        String nombre = name.getText().toString();
        String pes = peso.getText().toString();
        String fecha = fechaNac.getText().toString();
        String esta = estatura.getText().toString();
        if (esVacio(nombre, pes, fecha, esta)){
            register_baby(nombre, fecha, select_genero, pes, esta, select_comp);
        }
    }






}