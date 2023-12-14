package com.example.babycare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.PatternsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {
    ImageView logo, back;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&.+=])" +  // at least 1 special character
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=\\S+$)" +            // no white spaces
                    ".{8,20}" +                // at least 4 characters
                    "$");
    private final String[] items = {"Padre", "Madre"};
    private String seleccion;
    AutoCompleteTextView relacion;
    ArrayAdapter<String> adapter;
    TextInputEditText name, lastname, fechaNac, email, password;
    TextInputLayout text_name, text_lastname, text_fechaNac, text_email, text_password, text_relacion;
    CardView register_card;
    ProgressBar progressBar;
    DatePickerDialog.OnDateSetListener setListener;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        fechaNac = findViewById(R.id.fechaNac);
        email = findViewById(R.id.email);
        relacion = findViewById(R.id.relacion);
        password = findViewById(R.id.password);
        text_name = findViewById(R.id.text_name);
        text_relacion = findViewById(R.id.text_relacion);
        text_lastname = findViewById(R.id.text_lastname);
        text_fechaNac = findViewById(R.id.text_fechaNac);
        text_email = findViewById(R.id.text_email);
        text_password = findViewById(R.id.text_password);
        register_card = findViewById(R.id.register_card);
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressBar);
        Animation editText_anim =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.edittext_anim);
        text_name.startAnimation(editText_anim);
        text_lastname.startAnimation(editText_anim);
        text_fechaNac.startAnimation(editText_anim);
        text_email.startAnimation(editText_anim);
        text_password.startAnimation(editText_anim);
        text_relacion.startAnimation(editText_anim);
        Animation field_name_anim =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.field_name_anim);
        logo.startAnimation(field_name_anim);
        Animation center_reveal_anim =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.center_reveal_anim);
        register_card.startAnimation(center_reveal_anim);
        Calendar calendar = Calendar.getInstance();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        fechaNac.setKeyListener(null);
        fechaNac.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, i, i1, i2) -> {
                        i1++;
                        String dat = i2+"/"+i1+"/"+i;
                        fechaNac.setText(dat);
                    },year,month,day);
            datePickerDialog.show();
        });

        adapter = new ArrayAdapter<>(this, R.layout.list_relacion, items);
        relacion.setAdapter(adapter);
        relacion.setOnItemClickListener((adapterView, view, i, l) ->
                seleccion = adapterView.getItemAtPosition(i).toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        getUser();
    }

    private void getUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
            startActivity(new Intent(this, Principal.class));
            finish();
        }
    }

    public void backToLogin(View view) {
        finish();
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("User", "" + currentUser);
    }

    private void register(String nombre, String apellido, String fecha_nac, String correo, String pass) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(correo, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, Object> user = new HashMap<>();
                user.put("Nombre", nombre);
                user.put("Apellidos", apellido);
                user.put("Fecha de Nacimiento", fecha_nac);
                user.put("Correo", correo);
                user.put("Contraseña", pass);
                user.put("relacion", seleccion);
                String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                db.collection("usuario").document(id).set(user)
                                .addOnSuccessListener(unused -> {
                                    Log.d("User", "Registro exitoso");
                                }).addOnFailureListener(e ->
                                Log.e("User", "Registro fallido"));
                Log.d("Respuesta", "createUserWithEmailAndPassword:success");
                getUser();
            } else {
                Log.e("Respuesta", "createUserWithEmailAndPassword:failure " +
                        Objects.requireNonNull(task.getException()).getMessage());
                progressBar.setVisibility(View.GONE);
                showAlert();
                updateUI(null);
            }
        });
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("No se a podido registrar este usuario");
        builder.setPositiveButton("Aceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean esVacio(String nombre, String apellido, String fecha_nac, String correo, String pass) {
        boolean bandera = true;
        if(nombre.isEmpty() || !nombre.matches("[a-zA-Z\\s]+")) {
            bandera = false;
            text_name.setError("Debe ingresar su nombre");
        }else
            text_name.setError(null);

        if(apellido.isEmpty() || !apellido.matches("[a-zA-Z\\s]+")) {
            bandera = false;
            text_lastname.setError("Debe ingresar su apellido");
        }else
            text_lastname.setError(null);

        if(fecha_nac.isEmpty()) {
            bandera = false;
            text_fechaNac.setError("Debe ingresar su fecha de nacimiento");
        }else
            text_fechaNac.setError(null);


        if(seleccion == null) {
            bandera = false;
            text_relacion.setError("Debe ingresar su relación con él bebe ");
        }else
            text_relacion.setError(null);

        if(correo.isEmpty()) {
            bandera = false;
            text_email.setError("Debe ingresar su email");
        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(correo).matches()){
            bandera = false;
            text_email.setError("Debe ingresar su email");
        }else
            text_email.setError(null);

        if(pass.isEmpty()) {
            bandera = false;
            text_password.setError("Debe ingresar su contraseña");
        }else if (!PASSWORD_PATTERN.matcher(pass).matches()){
            bandera = false;
            text_password.setError("Debe ingresar una contraseña segura");
        }else
            text_password.setError(null);

        return bandera;
    }

    public void registerButton(View view) {
        String nombre = Objects.requireNonNull(name.getText()).toString();
        String apellido = Objects.requireNonNull(lastname.getText()).toString();
        String fecha_nac = Objects.requireNonNull(fechaNac.getText()).toString();
        String correo = Objects.requireNonNull(email.getText()).toString();
        String pass = Objects.requireNonNull(password.getText()).toString().trim();
        if (esVacio(nombre, apellido, fecha_nac, correo, pass)) {
            register(nombre, apellido, fecha_nac, correo, pass);
        }
    }




}
