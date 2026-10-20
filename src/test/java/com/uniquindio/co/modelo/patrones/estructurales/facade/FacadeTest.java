package com.uniquindio.co.modelo.patrones.estructurales.facade;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Cliente;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.entidades.enums.CanalVoucher;
import com.uniquindio.co.modelo.entidades.enums.EstadoActividad;
import com.uniquindio.co.modelo.entidades.enums.NivelDificultad;
import com.uniquindio.co.modelo.patrones.creacionales.builder.ReservaBuilder;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.PagoAndinoAdapter;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.PasarelaPago;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.ResultadoPago;
import com.uniquindio.co.modelo.patrones.estructurales.composite.ProductoTuristico;
import com.uniquindio.co.modelo.servicios.GestorCupos;
import com.uniquindio.co.modelo.servicios.ServicioCorreo;

class ConfirmarReservaFacadeTest {

    private static final String TARJETA_OK = "4539 1488 0343 6467";
    private static final String TARJETA_MALA = "4539 1488 0343 6468";

    private Agencia agencia;
    private Cliente ana;
    private ProductoTuristico producto;
    private GestorCupos cupos;
    private ConfirmarReservaFacade facade;
    private final LocalDate fecha = LocalDate.now().plusDays(5);

    @BeforeEach
    void preparar() {
        agencia = new Agencia("RutaVerde", "900", "RNT-1", "dir", "tel", "c@c.com", "web");
        agencia.registrarCliente("1", "Ana", "300", "ana@mail.com", "Colombia", "Español");
        agencia.registrarActividad("A1", "Cocora", "Salento", 5, 60_000, 10,
                NivelDificultad.MEDIA, EstadoActividad.DISPONIBLE);
        agencia.registrarActividad("A2", "Premium", "Armenia", 6, 250_000, 20,
                NivelDificultad.MEDIA, EstadoActividad.DISPONIBLE);
        ana = agencia.buscarCliente("1");
        producto = agencia.buscarProducto("A1");
        cupos = new GestorCupos(agencia);
        facade = new ConfirmarReservaFacade(agencia, cupos, new PagoAndinoAdapter(), new ServicioCorreo());
    }

    private Reserva reserva(ProductoTuristico p, int viajeros) {
        return new ReservaBuilder(ana, p, fecha, viajeros).construir();
    }

    @Test
    void confirmaRegistraYEmiteVoucher() {
        Reserva r = reserva(producto, 4);
        String voucher = facade.confirmar(r, TARJETA_OK, CanalVoucher.ASESOR);
        assertEquals(1, r.getNumero());
        assertEquals(1, agencia.getListaReservas().size());
        assertTrue(voucher.contains("Cocora"));
    }

    @Test
    void sinCupoNoRegistraNada() {
        facade.confirmar(reserva(producto, 8), TARJETA_OK, CanalVoucher.ASESOR);
        Reserva r = reserva(producto, 3);   // 8 + 3 > 10
        assertThrows(IllegalStateException.class, () -> facade.confirmar(r, TARJETA_OK, CanalVoucher.ASESOR));
        assertEquals(1, agencia.getListaReservas().size());
        assertFalse(r.estaConfirmada());
    }

    @Test
    void tarjetaInvalidaNoCobraNiRegistra() {
        Reserva r = reserva(producto, 2);
        assertThrows(IllegalStateException.class, () -> facade.confirmar(r, TARJETA_MALA, CanalVoucher.ASESOR));
        assertTrue(agencia.getListaReservas().isEmpty());
    }

    @Test
    void fondosInsuficientesNoDescuentaCupo() {
        ProductoTuristico premium = agencia.buscarProducto("A2");
        Reserva r = reserva(premium, 15);   // 3.750.000 > tope de PagoAndino
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> facade.confirmar(r, TARJETA_OK, CanalVoucher.ASESOR));
        assertTrue(e.getMessage().contains("Fondos insuficientes"));
        assertTrue(agencia.getListaReservas().isEmpty());
    }

    @Test
    void segundaPasarelaSeIntegraSinCambiarElFacade() {
        PasarelaPago otra = (tarjeta, pesos, ref) -> ResultadoPago.aprobado("OTRA-" + ref);
        ConfirmarReservaFacade f2 = new ConfirmarReservaFacade(agencia, cupos, otra, new ServicioCorreo());
        Reserva r = reserva(producto, 2);
        f2.confirmar(r, TARJETA_OK, CanalVoucher.MOVIL);
        assertTrue(r.estaConfirmada());
    }
}