package com.uniquindio.co.modelo.patrones.creacionales.builder;

import java.time.LocalDate;

import com.uniquindio.co.modelo.entidades.Cliente;
import com.uniquindio.co.modelo.entidades.Guia;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.patrones.estructurales.composite.ProductoTuristico;

public class ReservaBuilder {

    // Constantes
    private static final int MIN_VIAJEROS = 1;
    private static final int MAX_VIAJEROS = 15;

    // Obligatorios
    private final Cliente cliente;
    private final ProductoTuristico producto;
    private final LocalDate fechaViaje;
    private final int numViajeros;

    // Opcionales
    private Guia guia = null;
    private boolean transporte = false;
    private boolean seguro = false;
    private String observaciones = "";

    public ReservaBuilder(Cliente cliente, ProductoTuristico producto,
                          LocalDate fechaViaje, int numViajeros) {
        this.cliente = cliente;
        this.producto = producto;
        this.fechaViaje = fechaViaje;
        this.numViajeros = numViajeros;
    }

    public ReservaBuilder conGuia(Guia guia) {
        this.guia = guia;
        return this;
    }

    public ReservaBuilder conTransporte() {
        this.transporte = true;
        return this;
    }

    public ReservaBuilder conSeguro() {
        this.seguro = true;
        return this;
    }

    public ReservaBuilder conObservaciones(String texto) {
        this.observaciones = (texto == null) ? "" : texto;
        return this;
    }


    public Reserva construir() {
        validar();
        return new Reserva(cliente, producto, fechaViaje, numViajeros,
                           guia, transporte, seguro, observaciones);
    }

    private void validar() {
        if (cliente == null) {
            throw new IllegalArgumentException("La reserva debe tener un cliente");
        }
        if (producto == null) {
            throw new IllegalArgumentException("La reserva debe tener un producto");
        }
        if (fechaViaje == null || fechaViaje.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del viaje no puede estar en el pasado");
        }
        if (numViajeros < MIN_VIAJEROS || numViajeros > MAX_VIAJEROS) {
            throw new IllegalArgumentException("El grupo debe tener entre 1 y 15 viajeros");
        }
        if (guia != null && !guia.habla(cliente.getIdioma())) {
            throw new IllegalArgumentException("El guía debe hablar el idioma del cliente: "
                    + cliente.getIdioma());
        }
        if (producto.tieneActividadSuspendida()) {
            throw new IllegalArgumentException("El producto incluye una actividad suspendida");
        }
    }
}