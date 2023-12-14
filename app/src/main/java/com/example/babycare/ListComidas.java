package com.example.babycare;

import java.io.Serializable;

public class ListComidas implements Serializable {
    private String id;
    private String Hora;
    private String Fuente;
    private String Dia;
    private String Detalles;
    private String Cantidad;

    public ListComidas(){
        id = "";
        Hora = "";
        Fuente = "";
        Dia = "";
        Detalles = "";
        Cantidad = "";

    }

    public ListComidas(String Id, String hora, String fuente, String dia, String detalles, String cantidad) {
        id = Id;
        Hora = hora;
        Fuente = fuente;
        Dia = dia;
        Detalles = detalles;
        Cantidad = cantidad;
    }

    public String getid() {
        return id;
    }

    public void setid(String Id) {
        id = id;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getFuente() {
        return Fuente;
    }

    public void setFuente(String fuente) {
        Fuente = fuente;
    }

    public String getDia() {
        return Dia;
    }

    public void setDia(String dia) {
        Dia = dia;
    }

    public String getDetalles() {
        return Detalles;
    }

    public void setDetalles(String detalles) {
        Detalles = detalles;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }
}
