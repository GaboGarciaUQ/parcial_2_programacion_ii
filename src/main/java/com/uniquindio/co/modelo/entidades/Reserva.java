package com.uniquindio.co.modelo.entidades;

import java.time.LocalDate;

import com.uniquindio.co.modelo.patrones.estructurales.composite.ProductoTuristico;

public class Reserva {

    private static final int TARIFA_TRANSPORTE = 35_000;
    private static final int TARIFA_SEGURO = 15_000;
    private static final int HORAS_POR_DIA_GUIA = 8;

    private int numero; // 0 = aún sin confirmar
    private final Cliente cliente;
    private final ProductoTuristico producto;
    private final LocalDate fechaViaje;
    private final int numViajeros;
    private final Guia guia;
    private final boolean transporte;
    private final boolean seguro;
    private final String observaciones;

    public Reserva(Cliente cliente, ProductoTuristico producto, LocalDate fechaViaje,
                   int numViajeros, Guia guia, boolean transporte,
                   boolean seguro, String observaciones) {
        this.cliente = cliente;
        this.producto = producto;
        this.fechaViaje = fechaViaje;
        this.numViajeros = numViajeros;
        this.guia = guia;
        this.transporte = transporte;
        this.seguro = seguro;
        this.observaciones = observaciones;
    }

    public Cliente getCliente() { return cliente; }
    public ProductoTuristico getProducto() { return producto; }
    public LocalDate getFechaViaje() { return fechaViaje; }
    public int getNumViajeros() { return numViajeros; }
    public Guia getGuia() { return guia; }
    public String getObservaciones() { return observaciones; }
    public int getNumero() { return numero; }

    public boolean tieneTransporte() { return transporte; }
    public boolean tieneSeguro() { return seguro; }
    public boolean tieneGuia() { return guia != null; }

    public double calcularValor() {
        return producto.getPrecioPorPersona() * numViajeros
                + calcularCostoGuia()
                + calcularCostoSeguro()
                + calcularCostoTransporte();
    }
    public double calcularCostoGuia() {
        if (guia == null) {
            return 0;
        }
        int dias = (int) Math.ceil((double) producto.getDuracionHoras() / HORAS_POR_DIA_GUIA);
        return guia.getTarifadia() * dias;
    }
    public double calcularCostoTransporte() {
        return transporte ? (double) TARIFA_TRANSPORTE * numViajeros : 0;
    }
    public double calcularCostoSeguro() {
        return seguro ? (double) TARIFA_SEGURO * numViajeros : 0;
    }
    public void confirmar(int numero) {
        if (estaConfirmada()) {
            throw new IllegalStateException("La reserva ya está confirmada");
        }
        this.numero = numero;
    }
    public boolean estaConfirmada() {
        return numero > 0;
    }
    public boolean viajaEnPeriodo(LocalDate ini, LocalDate fin) {
        return !fechaViaje.isBefore(ini) && !fechaViaje.isAfter(fin);
    }
    @Override
    public String toString() {
        return "Reserva #" + numero + " - " + cliente.getNombre() + " - "
                + producto.getNombre() + " - " + fechaViaje;
    }
}