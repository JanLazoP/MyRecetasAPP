package com.novita.myrecetasapp.modelos;

public class RecetaModelo {
    private String nombre;
    private String descripcion;
    private String ingredientes;
    private String pasos;
    private String img;
    private String key;
    private String recetaKey;

    public RecetaModelo(String nombre, String descripcion, String ingredientes, String pasos, String img) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.img = img;

    }

    public RecetaModelo(String nombre, String descripcion, String ingredientes, String pasos, String img, String key, String recetaKey) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.img = img;
        this.key = key;
        this.recetaKey = recetaKey;
    }

    public RecetaModelo(){}

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRecetaKey() {
        return recetaKey;
    }

    public void setRecetaKey(String recetaKey) {
        this.recetaKey = recetaKey;
    }
}
