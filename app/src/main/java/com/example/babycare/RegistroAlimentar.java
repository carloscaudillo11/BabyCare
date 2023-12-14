package com.example.babycare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroAlimentar extends AppCompatActivity {
    private AutoCompleteTextView fuente;
    private TextInputEditText date, time, cantidad, detalles;
    private TextInputLayout text_date, text_fuente, text_time, txt_cantidad;
    private final String[] items = {"Seno", "Formula"};
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    ImageButton btn;
    private FirebaseFirestore db;
    String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alimentar);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        detalles = findViewById(R.id.detalles);
        cantidad = findViewById(R.id.cantidad);
        text_date = findViewById(R.id.text_date);
        btn = findViewById(R.id.backAl);
        txt_cantidad = findViewById(R.id.text_cantidad);
        text_time = findViewById(R.id.text_time);
        fuente = findViewById(R.id.fuente);
        text_fuente = findViewById(R.id.text_fuente);
        progressBar = findViewById(R.id.progress);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btn.setOnClickListener(view -> {
            finish();
        });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hora = calendar.get(Calendar.HOUR_OF_DAY);
        final int minuto = calendar.get(Calendar.MINUTE);
        date.setKeyListener(null);
        date.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, i, i1, i2) -> {
                        i1++;
                        String dat = i+"/"+i1+"/"+i2;
                        date.setText(dat);
                    },year,month,day);
            datePickerDialog.show();
        });
        time.setKeyListener(null);
        time.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (timePicker, i, i1) -> {
                        String dat = i+":"+i1;
                        time.setText(dat);
            },hora, minuto, false);
            timePickerDialog.show();
        });

        adapter = new ArrayAdapter<>(this, R.layout.list_relacion, items);
        fuente.setAdapter(adapter);
        fuente.setOnItemClickListener((adapterView, view, i, l) ->
                select = adapterView.getItemAtPosition(i).toString());
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Ocurrio un error y no se pudo registrar la comida");
        builder.setPositiveButton("Aceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void register_comida(String cantidad, String dia, String hora, String fuente, String detalles) {
        progressBar.setVisibility(View.VISIBLE);
        Map<String, Object> comida = new HashMap<>();
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        comida.put("id", id);
        comida.put("Cantidad", cantidad);
        comida.put("Dia", dia);
        comida.put("Hora", hora);
        comida.put("Fuente", fuente);
        comida.put("Detalles", detalles);
        db.collection("cicloAlimentacion").document().set(comida).addOnSuccessListener(unused -> {
            Log.d("comida", "Registro exitoso");
            finish();
        }).addOnFailureListener(e -> showAlert());
    }

    private boolean esVacio(String cantidad, String dia, String hora, String fuente, String detalles) {
        boolean bandera = true;
        if(cantidad.isEmpty() || !cantidad.matches("[0-9\\s]+")) {
            bandera = false;
            txt_cantidad.setError("Debe ingresar la cantidad en ml");
        }else
            txt_cantidad.setError(null);

        if(dia.isEmpty()) {
            bandera = false;
            text_date.setError("Debe ingresar el dia de la comida");
        }else
            text_date.setError(null);

        if(select == null) {
            bandera = false;
            text_fuente.setError("Debe ingresar ela hora de la comida");
        }else
            text_fuente.setError(null);

        if(hora.isEmpty()) {
            bandera = false;
            text_time.setError("Debe ingresar el peso del bebé");
        }else
            text_time.setError(null);

        return bandera;
    }

    public void registerComida(View view) {
        String porcion = cantidad.getText().toString();
        String day = date.getText().toString();
        String hour = time.getText().toString();
        String tipo = fuente.getText().toString();
        String detalle = detalles.getText().toString();
        if (esVacio(porcion, day, hour, tipo, detalle)){
            register_comida(porcion, day, hour, tipo, detalle);
        }
    }
}