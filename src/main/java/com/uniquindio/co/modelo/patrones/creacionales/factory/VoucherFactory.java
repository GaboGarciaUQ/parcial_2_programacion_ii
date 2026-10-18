package com.uniquindio.co.modelo.patrones.creacionales.factory;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.entidades.enums.CanalVoucher;

public class VoucherFactory {
    

public static Voucher crearVoucher(CanalVoucher canal, Reserva reserva, Agencia agencia) {
    switch (canal) {
        case ASESOR: return new VoucherAsesor(reserva, agencia);
        case KIOSCO: return new VoucherKiosco(reserva, agencia);
        case MOVIL:  return new VoucherMovil(reserva, agencia);
        default: throw new IllegalArgumentException("Canal no soportado: " + canal);
    }
}
}
