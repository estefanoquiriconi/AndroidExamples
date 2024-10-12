package com.example.architectureexample.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabla_nota")
public class Nota {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private String descripcion;
    private int prioridad;

    public Nota(String titulo, String descripcion, int prioridad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }
}
