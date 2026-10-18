package com.uniquindio.co.modelo.patrones.creacionales.factory;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Reserva;

public class VoucherMovil extends Voucher{
    public VoucherMovil(Reserva reserva, Agencia agencia){
        super(reserva, agencia);
    }

    @Override
    protected String generarDetalle() {
        String detalle = "Reserva #" + reserva.getNumero() + "\n";
        detalle += reserva.getProducto().getNombre() + "\n";
        detalle += reserva.getFechaViaje() + " · " + reserva.getNumViajeros() + " viajeros\n";
        detalle += "Total: $" + reserva.calcularValor();
        return detalle;
    }
}
