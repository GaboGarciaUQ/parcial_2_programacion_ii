package com.uniquindio.co.modelo.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {

    private final String id;
    private String nombre;
    private String telefono;
    private String correo;
    private String pais;
    private String idioma;
    private final List<Reserva> listaReservas;

    public Cliente(String id, String nombre, String telefono, String correo,
                   String pais, String idioma) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = validarTelefono(telefono);
        this.correo = validarCorreo(correo);
        this.pais = pais;
        this.idioma = idioma;
        this.listaReservas = new ArrayList<>();
    }

    public void agregarReserva(Reserva reserva) {
        listaReservas.add(reserva);
    }

    public String consultarReservas() {
        if (listaReservas.isEmpty()) {
            return "El cliente no tiene reservas.";
        }
        String detalle = "";
        for (Reserva reserva : listaReservas) {
            detalle += reserva.getProducto().getNombre() + " : "
                     + reserva.getFechaViaje() + "\n";
        }
        return detalle;
    }

    private static String validarCorreo(String correo) {
    if (correo == null || !correo.trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
        throw new IllegalArgumentException("El correo electrónico no es válido (ejemplo: nombre@dominio.com)");
    }
    return correo.trim();
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

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = validarTelefono(telefono); }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = validarCorreo(correo); }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public List<Reserva> getListaReservas() {
        return Collections.unmodifiableList(listaReservas);
    }
}