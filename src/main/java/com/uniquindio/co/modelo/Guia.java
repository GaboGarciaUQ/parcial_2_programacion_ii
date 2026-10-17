package com.uniquindio.co.modelo;
import java.util.List;
public class Guia {

    private String id;
    private String nombre;
    private List<String> idiomas;
    private String telefono;
    private double tarifadia;

    public Guia(String id, String nombre, List<String> idiomas, String telefono, double tarifadia) {
        this.id = id;
        this.nombre = nombre;
        this.idiomas = idiomas;
        this.telefono = telefono;
        this.tarifadia = tarifadia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getTarifadia() {
        return tarifadia;
    }

    public void setTarifadia(double tarifadia) {
        this.tarifadia = tarifadia;
    }

}
