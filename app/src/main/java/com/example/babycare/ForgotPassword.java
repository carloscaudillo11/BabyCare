package com.example.babycare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends AppCompatActivity {
    TextInputEditText email;
    TextInputLayout text_email;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.email);
        text_email= findViewById(R.id.text_email);
        progressBar = findViewById(R.id.progress);
        mAuth = FirebaseAuth.getInstance();
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("No sé a podido enviar correo de recuperación");
        builder.setPositiveButton("Aceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void backToLogin(View view) {
        finish();
    }

    private void setEmail(String email){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                startActivity(new Intent(getApplicationContext(),SendEmail.class));
                finish();
            }else{
                showAlert();
            }
        });
    }

    public void resetPassword(View view){
        String correo = email.getText().toString();
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            text_email.setError("Correo Invalido");
        }else{
            setEmail(correo);
        }
    }

}