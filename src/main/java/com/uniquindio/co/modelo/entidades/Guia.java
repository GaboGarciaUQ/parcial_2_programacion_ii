package com.uniquindio.co.modelo.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Guia {

    private final String id;
    private String nombre;
    private final List<String> idiomas;
    private String telefono;
    private double tarifadia;

    public Guia(String id, String nombre, List<String> idiomas,
                String telefono, double tarifadia) {
        if (tarifadia <= 0) {
            throw new IllegalArgumentException("La tarifa por día debe ser mayor que cero");
        }
        if (idiomas == null || idiomas.isEmpty()) {
            throw new IllegalArgumentException("El guía debe hablar al menos un idioma");
        }
        this.id = id;
        this.nombre = nombre;
        this.idiomas = new ArrayList<>(idiomas);
        this.telefono = validarTelefono(telefono);
        this.tarifadia = tarifadia;
    }


    public boolean habla(String idioma) {
        for (String i : idiomas) {
            if (i.equalsIgnoreCase(idioma)) {
                return true;
            }
        }
        return false;
    }

    public void agregarIdioma(String idioma) {
        if (!habla(idioma)) {
            idiomas.add(idioma);
        }
    }
    private static String validarTelefono(String telefono) {
    String limpio = telefono == null ? "" : telefono.replace(" ", "");
    if (!limpio.matches("^\\+?\\d{7,15}$")) {
        throw new IllegalArgumentException(
                "El teléfono solo puede tener números (de 7 a 15 dígitos, con + opcional al inicio)");
    }
    return limpio;
    }


    public String getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<String> getIdiomas() { return Collections.unmodifiableList(idiomas); }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = validarTelefono(telefono); }

    public double getTarifadia() { return tarifadia; }
    public void setTarifadia(double tarifadia) {
        if (tarifadia <= 0) {
            throw new IllegalArgumentException("La tarifa por día debe ser mayor que cero");
        }
        this.tarifadia = tarifadia;
    }

    @Override
    public String toString() {
        return nombre + " (" + String.join(", ", idiomas) + ")";
    }
}