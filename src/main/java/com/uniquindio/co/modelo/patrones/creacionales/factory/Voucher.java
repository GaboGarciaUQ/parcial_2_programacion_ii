package com.uniquindio.co.modelo.patrones.creacionales.factory;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Reserva;

public abstract class Voucher {

    protected final Reserva reserva;
    protected final Agencia agencia;

    // Constantes
    protected static final String POLITICA_CANCELACION =
            "Cancelación sin costo hasta 48 horas antes del viaje.";

    protected Voucher(Reserva reserva, Agencia agencia) {
        this.reserva = reserva;
        this.agencia = agencia;
    }

    // Método plantilla: el orden es fijo y las subclases no lo pueden cambiar
    public final String emitir() {
        return generarEncabezado() + separador()
             + generarDetalle() + separador()
             + generarPie();
    }

    protected String generarEncabezado() {
        return agencia.getNombre() + " - RNT: " + agencia.getRnt();
    }

    // Lo único que cambia según el canal
    protected abstract String generarDetalle();

    protected String generarPie() {
        return POLITICA_CANCELACION;
    }

    // Por defecto multilínea; el kiosco lo sobrescribe para una sola línea
    protected String separador() {
        return "\n";
    }
}