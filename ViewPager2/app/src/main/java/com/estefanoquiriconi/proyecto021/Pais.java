package com.estefanoquiriconi.proyecto021;

public class Pais {
    private String nombre;
    private int imagen;

    public Pais(String nombre, int imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImagen() {
        return imagen;
    }
}
