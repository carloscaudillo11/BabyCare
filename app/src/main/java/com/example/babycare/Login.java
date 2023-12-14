package com.example.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.PatternsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    ImageView logo;
    TextInputEditText email,password;
    TextInputLayout text_email,text_password;
    LinearLayout new_user_layout;
    CardView login_card;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar3);
        password = findViewById(R.id.password);
        logo = findViewById(R.id.logo);
        text_password = findViewById(R.id.text_password);
        text_email = findViewById(R.id.text_email);
        new_user_layout = findViewById(R.id.new_user_text);
        login_card = findViewById(R.id.login_card);
        Animation editText_anim = AnimationUtils.loadAnimation(this,R.anim.edittext_anim);
        text_email.startAnimation(editText_anim);
        text_password.startAnimation(editText_anim);
        Animation field_name_anim = AnimationUtils.loadAnimation(this,R.anim.field_name_anim);
        logo.startAnimation(field_name_anim);
        Animation center_reveal_anim = AnimationUtils.loadAnimation(this,R.anim.center_reveal_anim);
        login_card.startAnimation(center_reveal_anim);
        Animation new_user_anim = AnimationUtils.loadAnimation(this,R.anim.down_top);
        new_user_layout.startAnimation(new_user_anim);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        getUser();
    }

    private void getUser(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            updateUI(currentUser);
            startActivity(new Intent(this,Principal.class));
            finish();
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("User",""+currentUser);
    }

    public void register(View view) {
        startActivity(new Intent(this,Registro.class));
    }

    private boolean isValidate(String correo, String pass) {
        boolean bandera = true;
        if(correo.isEmpty()) {
            text_email.setError("Debe ingresar el correo");
            bandera = false;
        }if(pass.isEmpty()) {
            text_password.setError("Debe ingresar la contraseña");
            bandera = false;
        }
        return bandera;
    }

    private void login(String correo, String pass){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(correo.toLowerCase(), pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.d("Respuesta", "signInWithEmailAndPassword:success");
                getUser();
            }else{
                Log.e("Respuesta", "signInWithEmailAndPassword:failure " +
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
        builder.setMessage("No se a podido autenticar este usuario");
        builder.setPositiveButton("Aceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loginButton(View view) {
        String correo = email.getText().toString();
        String pass = password.getText().toString();
        if (isValidate(correo, pass)){
            login(correo, pass);
        }
    }

    public void forgotPassword(View view){
        startActivity(new Intent(this,ForgotPassword.class));
    }
}