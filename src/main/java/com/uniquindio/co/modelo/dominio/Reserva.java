package com.uniquindio.co.modelo.dominio;

import com.uniquindio.co.modelo.Cliente;
import com.uniquindio.co.modelo.Guia;
import com.uniquindio.co.modelo.producto.ProductoTuristico;

import java.time.LocalDateTime;
public class Reserva {

    private static final int TARIFA_TRANSPORTE = 35_000;
    private static final int TARIFA_SEGURO = 15_000;
    private static final int HORA_POR_DIA_GUIA = 8;

    private int numero; //0 = aun sin confirmar
    private final Cliente cliente;
    private final ProductoTuristico producto;
    private final LocalDateTime fechaViaje;
    private final int numViajeros;
    private final Guia guia;
    private final boolean transporte;
    private final boolean seguro;
    private final String observaciones;

    public Reserva(Cliente cliente, ProductoTuristico producto, LocalDateTime fechaViaje, int numViajeros, Guia guia,
            boolean transporte, boolean seguro, String observaciones) {
        this.cliente = cliente;
        this.producto = producto;
        this.fechaViaje = fechaViaje;
        this.numViajeros = numViajeros;
        this.guia = guia;
        this.transporte = transporte;
        this.seguro = seguro;
        this.observaciones = observaciones;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ProductoTuristico getProducto() {
        return producto;
    }

    public LocalDateTime getFechaViaje() {
        return fechaViaje;
    }

    public int getNumViajeros() {
        return numViajeros;
    }

    public Guia getGuia() {
        return guia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public int getNumero() {
        return numero;
    }

    public boolean tieneTransporte(){
        return transporte;
    }

    public boolean tieneSeguro(){
        return seguro;
    }

    public void tieneGuia(){
        
    }





    // public int calcularValor(){

    // }

    // private int calcularCostoGuia(){

    // }

    public void confirmar(int numero){
        this.numero=numero;
    }

    // public boolean estaConfirmada(){
        
    // }
    
    // public LocalDateTime viajeEnPeriodo(LocalDateTime ini, LocalDateTime fin){

    // }

    // public String toString(){

    // } 
    

    
    
}
