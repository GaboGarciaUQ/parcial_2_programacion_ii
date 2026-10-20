package com.uniquindio.co.modelo.patrones.estructurales.facade;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.entidades.enums.CanalVoucher;
import com.uniquindio.co.modelo.patrones.creacionales.factory.Voucher;
import com.uniquindio.co.modelo.patrones.creacionales.factory.VoucherFactory;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.PasarelaPago;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.ResultadoPago;
import com.uniquindio.co.modelo.servicios.GestorCupos;
import com.uniquindio.co.modelo.servicios.ServicioCorreo;
import com.uniquindio.co.modelo.servicios.ValidadorTarjeta;

public class ConfirmarReservaFacade {

    private final Agencia agencia;
    private final GestorCupos gestorCupos;
    private final PasarelaPago pasarela;
    private final ServicioCorreo servicioCorreo;

    public ConfirmarReservaFacade(Agencia agencia, GestorCupos gestorCupos,
                                  PasarelaPago pasarela, ServicioCorreo servicioCorreo) {
        this.agencia = agencia;
        this.gestorCupos = gestorCupos;
        this.pasarela = pasarela;
        this.servicioCorreo = servicioCorreo;
    }


    public String confirmar(Reserva reserva, String numeroTarjeta, CanalVoucher canal) {
        String correo = reserva.getCliente().getCorreo();

        // 1. Verificar cupo en la fecha para todas las actividades del producto
        if (!gestorCupos.hayCupo(reserva.getProducto(), reserva.getFechaViaje(),
                                 reserva.getNumViajeros())) {
            return fallar(correo, "No hay cupo suficiente en la fecha del viaje");
        }

        // 2. Validar la tarjeta (Luhn)
        if (!ValidadorTarjeta.esValida(numeroTarjeta)) {
            return fallar(correo, "El número de tarjeta no es válido");
        }

        // 3. Cobrar en la pasarela
        String referencia = "RES-" + agencia.siguienteConsecutivo();
        ResultadoPago pago = pasarela.cobrar(numeroTarjeta, reserva.calcularValor(), referencia);
        if (!pago.isAprobado()) {
            return fallar(correo, "Pago rechazado: " + pago.getMotivo());
        }

        // 4. Descontar cupos y 5. registrar la reserva con número consecutivo
        // (los cupos se calculan desde las reservas registradas, así que
        // registrar la reserva los descuenta)
        reserva.confirmar(agencia.siguienteConsecutivo());
        agencia.registrarReserva(reserva);
        reserva.getCliente().agregarReserva(reserva);

        // 6. Emitir el voucher
        Voucher voucher = VoucherFactory.crearVoucher(canal, reserva, agencia);
        String texto = voucher.emitir();

        // 7. Enviarlo al cliente por correo
        servicioCorreo.enviar(correo, "Confirmación de reserva #" + reserva.getNumero(), texto);

        return texto;
    }

    private String fallar(String correo, String motivo) {
        servicioCorreo.enviar(correo, "No fue posible confirmar su reserva", motivo);
        throw new IllegalStateException(motivo);
    }
}