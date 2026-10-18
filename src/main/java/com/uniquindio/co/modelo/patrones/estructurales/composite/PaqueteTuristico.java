package com.uniquindio.co.modelo.patrones.estructurales.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaqueteTuristico implements ProductoTuristico {

    private static final double DESCUENTO_MIN = 0;
    private static final double DESCUENTO_MAX = 20;

    private final String codigo;
    private String nombre;
    private double descuento;
    private final List<ProductoTuristico> listaContenidos;

    public PaqueteTuristico(String codigo, String nombre, double descuento) {
        validarDescuento(descuento);
        this.codigo = codigo;
        this.nombre = nombre;
        this.descuento = descuento;
        this.listaContenidos = new ArrayList<>();
    }

    public void agregarContenido(ProductoTuristico contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser nulo");
        }
        // Sin ciclos: ni a sí mismo, ni un paquete que ya me contenga
        if (contenido == this || contenido.contiene(this)) {
            throw new IllegalArgumentException("Un paquete no puede contenerse a sí mismo, ni directa ni indirectamente");
        }
        // Sin actividades repetidas dentro del plan
        List<Actividad> actuales = getActividades();
        for (Actividad nueva : contenido.getActividades()) {
            if (actuales.contains(nueva)) {
                throw new IllegalArgumentException("La actividad '" + nueva.getNombre() + "' ya está incluida en el plan");
            }
        }
        listaContenidos.add(contenido);
    }

    @Override
    public double getPrecioPorPersona() {
        double suma = 0;
        for (ProductoTuristico producto : listaContenidos) {
            suma += producto.getPrecioPorPersona();
        }
        return suma * (1 - descuento / 100);
    }

    @Override
    public int getDuracionHoras() {
        int horas = 0;
        for (ProductoTuristico producto : listaContenidos) {
            horas += producto.getDuracionHoras();
        }
        return horas;
    }

    @Override
    public List<Actividad> getActividades() {
        List<Actividad> actividades = new ArrayList<>();
        for (ProductoTuristico producto : listaContenidos) {
            actividades.addAll(producto.getActividades());
        }
        return actividades;
    }

    @Override
    public boolean tieneActividadSuspendida() {
        for (ProductoTuristico producto : listaContenidos) {
            if (producto.tieneActividadSuspendida()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contiene(ProductoTuristico otro) {
        if (this == otro) {
            return true;
        }
        for (ProductoTuristico producto : listaContenidos) {
            if (producto.contiene(otro)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCodigo() { return codigo; }

    @Override
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getDescuento() { return descuento; }

    public void setDescuento(double descuento) {
        validarDescuento(descuento);
        this.descuento = descuento;
    }

    public List<ProductoTuristico> getListaContenidos() {
        return Collections.unmodifiableList(listaContenidos);
    }

    private void validarDescuento(double descuento) {
        if (descuento < DESCUENTO_MIN || descuento > DESCUENTO_MAX) {
            throw new IllegalArgumentException("El descuento debe estar entre 0% y 20%");
        }
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}