package com.example.babycare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;


public class Inicio extends Fragment {
    TextView hoy,fecha,nombres;
    LinearLayout sueño, guias, alimentar;
    private String nombre, bebe;
    View vista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        hoy = vista.findViewById(R.id.hoyI);
        fecha = vista.findViewById(R.id.fechaI);
        nombres = vista.findViewById(R.id.nombres);
        sueño = vista.findViewById(R.id.siesta);
        guias = vista.findViewById(R.id.guias);
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int diaMes = today.monthDay;
        int mes = today.month;
        mes= mes+1;
        int day = today.weekDay;
        fecha.setText(diaMes+ " de "+ getMes(mes));
        hoy.setText(getDia(day)+",");
        alimentar = vista.findViewById(R.id.alimentar);
        sueño.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),RegistroSiesta.class));
        });
        alimentar.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),RegistroAlimentar.class));
        });
        /*guias.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), Principal.class);
            intent.putExtra("Guias", "1");
            startActivity(intent);
        });*/
        if (getArguments() != null) {
            nombre = getArguments().getString("nombre");
            if (getArguments().getString("sinBebe") == null){
                bebe = getArguments().getString("nombreBebe");
                nombres.setText(nombre+" y "+bebe);
            }else{
                nombres.setText(nombre);
            }
        }
        return vista;
    }

    String  getDia(int dia){
        String v="";
        switch(dia){
            case 1:
                v = "Lunes";
                break;
            case 2:
                v = "Martes";
                break;
            case 3:
                v = "Miercoles";
                break;
            case 4:
                v = "Jueves";
                break;
            case 5:
                v = "Viernes";
                break;
            case 6:
                v = "Sabado";
                break;
            case 7:
                v = "Domingo";
                break;
        }
        return v;
    }

    String getMes(int mes){
        String v="";
        switch(mes){
            case 1:
                v = "Enero";
                break;
            case 2:
                v = "Febrero";
                break;
            case 3:
                v = "Marzo";
                break;
            case 4:
                v = "Abril";
                break;
            case 5:
                v = "Mayo";
                break;
            case 6:
                v = "Junio";
                break;
            case 7:
                v = "Julio";
                break;
            case 8:
                v = "Agosto";
                break;
            case 9:
                v = "Septiembre";
                break;
            case 10:
                v = "Octubre";
                break;
            case 11:
                v = "Noviembre";
                break;
            case 12:
                v = "Diciembre";
                break;
        }
        return v;
    }
}