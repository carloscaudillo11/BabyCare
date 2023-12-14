package com.example.babycare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class contenido extends AppCompatActivity {
    private TextView texto, titulo, fecha;
    private ImageView imagen;
    private ListConsejos listConsejos;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido);
        texto = findViewById(R.id.texto);
        titulo = findViewById(R.id.tituloCont);
        imagen = findViewById(R.id.imagenTop);
        fecha = findViewById(R.id.fecha);
        listConsejos = (ListConsejos)getIntent().getExtras().getSerializable("itemDetail");
        texto.setText(listConsejos.getTexto());
        titulo.setText(listConsejos.getTitulo());
        fecha.setText(listConsejos.getFechaDePublicacion());
        Glide.with(this).load(listConsejos.getImg()).centerCrop().into(imagen);
    }

    public void back(View view) {
        finish();
    }
}