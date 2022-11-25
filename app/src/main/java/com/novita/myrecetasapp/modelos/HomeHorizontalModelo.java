package com.novita.myrecetasapp.modelos;

public class HomeHorizontalModelo {

    //modelo a mostrar de los items horizontales

    int imagen;
    String nombre;

    public HomeHorizontalModelo(int imagen, String nombre) {
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
