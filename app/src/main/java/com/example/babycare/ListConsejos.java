package com.example.babycare;

import java.io.Serializable;
import java.net.URL;

public class ListConsejos implements Serializable {
    private String autor;
    private String fechaDePublicacion;
    private String img;
    private String texto;
    private String titulo;

    public ListConsejos(){
        autor = "";
        fechaDePublicacion = "";
        img = "";
        texto = "";
        titulo = "";
    }

    public ListConsejos(String autor, String fechaDePublicacion, String img, String texto, String titulo) {
        this.autor = autor;
        this.fechaDePublicacion = fechaDePublicacion;
        this.img = img;
        this.texto = texto;
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFechaDePublicacion() {
        return fechaDePublicacion;
    }

    public void setFechaDePublicacion(String fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
