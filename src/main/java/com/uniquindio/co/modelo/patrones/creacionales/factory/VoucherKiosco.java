package com.uniquindio.co.modelo.patrones.creacionales.factory;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Reserva;

public class VoucherKiosco extends Voucher{
    public VoucherKiosco(Reserva reserva, Agencia agencia){
        super(reserva, agencia);
    }

    @Override
    protected String generarDetalle() {
        return "COD " + generarCodigo() + " | " + reserva.getCliente().getNombre()
            + " | " + reserva.getFechaViaje() + " | " + reserva.getNumViajeros() + " pax";
    }

    private String generarCodigo() {
        return "RV-" + reserva.getNumero() + "-" + reserva.getFechaViaje().toString().replace("-", "");
    }
}
