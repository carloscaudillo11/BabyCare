package com.example.babycare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroSiesta extends AppCompatActivity {
    private TextInputEditText date, time, time2, detalles;
    private TextInputLayout text_date, text_time2, text_time;
    ProgressBar progressBar;
    ImageButton btn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_siesta);
        btn = findViewById(R.id.backSis);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        time2 = findViewById(R.id.time2);
        detalles = findViewById(R.id.detalles);
        text_date = findViewById(R.id.text_date);
        text_time = findViewById(R.id.text_time);
        text_time2 = findViewById(R.id.text_time2);
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
        time2.setKeyListener(null);
        time2.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (timePicker, i, i1) -> {
                        String dat = i+":"+i1;
                        time2.setText(dat);
                    },hora, minuto, false);
            timePickerDialog.show();
        });
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Ocurrio un error y no se pudo registrar la comida");
        builder.setPositiveButton("Aceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void register_sueño(String date, String time, String time2, String detalles) {
        progressBar.setVisibility(View.VISIBLE);
        Map<String, Object> siesta = new HashMap<>();
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        siesta.put("id", id);
        siesta.put("Dia", date);
        siesta.put("Hora_Inicio", time);
        siesta.put("Hora_Fin", time2);
        siesta.put("Detalles", detalles);
        db.collection("cicloSueño").document().set(siesta).addOnSuccessListener(unused -> {
            Log.d("Siesta", "Registro exitoso");
            finish();
        }).addOnFailureListener(e -> showAlert());
    }

    private boolean esVacio(String date, String time, String time2, String detalles) {
        boolean bandera = true;
        if(date.isEmpty()) {
            bandera = false;
            text_date.setError("Debe ingresar el dia de la siesta");
        }else
            text_date.setError(null);

        if(time.isEmpty()) {
            bandera = false;
            text_time.setError("Debe ingresar la hora de inicio de la siesta");
        }else
            text_time.setError(null);

        if(time2.isEmpty()) {
            bandera = false;
            text_time2.setError("Debe ingresar la hora de finalizacion de la siesta");
        }else
            text_time2.setError(null);

        return bandera;
    }

    public void registerSiesta(View view) {
        String dia = date.getText().toString();
        String hourI = time.getText().toString();
        String hourF = time2.getText().toString();
        String detalle = detalles.getText().toString();
        if (esVacio(dia, hourI, hourF, detalle)){
            register_sueño(dia, hourI, hourF, detalle);
        }
    }
}