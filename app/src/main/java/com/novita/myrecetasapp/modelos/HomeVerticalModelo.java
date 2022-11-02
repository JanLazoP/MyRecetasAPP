package com.novita.myrecetasapp.modelos;

public class HomeVerticalModelo {
    int image;
    String nombre;

    public HomeVerticalModelo(int image, String nombre) {
        this.image = image;
        this.nombre = nombre;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
