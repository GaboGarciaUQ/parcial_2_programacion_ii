package com.uniquindio.co.modelo.patrones.creacionales.factory;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Reserva;

public abstract class Voucher {
    
    protected final Reserva reserva;
    protected final Agencia agencia;

    //Constantes

    protected static final String POLITICA_CANCELACION = "Cancelación sin costo hasta 48 horas antes del viaje";

    protected Voucher (Reserva reserva, Agencia agencia){
        this.reserva=reserva;
        this.agencia=agencia;
    }

    public final void emitir(){

    }

    protected void generarEncabezado(){

    }

    protected abstract String generarDetalle();

    protected String generarPie(){
        return "";
    };


}
