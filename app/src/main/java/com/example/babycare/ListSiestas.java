package com.example.babycare;

public class ListSiestas {
    private String Dia;
    private String Hora_Inicio;
    private String Hora_Fin;
    private String Detalles;

    public ListSiestas(){
        Dia = "";
        Hora_Inicio = "";
        Hora_Fin = "";
        Detalles = "";
    }

    public ListSiestas(String dia, String hora_Inicio, String hora_Fin, String detalles) {
        Dia = dia;
        Hora_Inicio = hora_Inicio;
        Hora_Fin = hora_Fin;
        Detalles = detalles;
    }

    public String getDia() {
        return Dia;
    }

    public void setDia(String dia) {
        Dia = dia;
    }

    public String getHora_Inicio() {
        return Hora_Inicio;
    }

    public void setHora_Inicio(String hora_Inicio) {
        Hora_Inicio = hora_Inicio;
    }

    public String getHora_Fin() {
        return Hora_Fin;
    }

    public void setHora_Fin(String hora_Fin) {
        Hora_Fin = hora_Fin;
    }

    public String getDetalles() {
        return Detalles;
    }

    public void setDetalles(String detalles) {
        Detalles = detalles;
    }
}
