package com.uniquindio.co.modelo.patrones.creacionales.factory;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.patrones.estructurales.composite.Actividad;

public class VoucherAsesor extends Voucher{
    public VoucherAsesor(Reserva reserva, Agencia agencia){
        super(reserva, agencia);
    }

    @Override
    protected String generarDetalle() {
        String detalle = "Reserva N° " + reserva.getNumero() + "\n";
        detalle += "Cliente: " + reserva.getCliente().getNombre() + "\n";
        detalle += "Producto: " + reserva.getProducto().getNombre() + "\n";
        for (Actividad a : reserva.getProducto().getActividades()) {
            detalle += " - " + a.getNombre() + " (" + a.getMunicipio() + ", "
                    + a.getDuracionHoras() + " h)\n";
        }
        return detalle;
    }
    
}
