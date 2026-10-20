package com.uniquindio.co.modelo.patrones.creacionales.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.uniquindio.co.modelo.entidades.Cliente;
import com.uniquindio.co.modelo.entidades.Guia;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.entidades.enums.EstadoActividad;
import com.uniquindio.co.modelo.entidades.enums.NivelDificultad;
import com.uniquindio.co.modelo.patrones.estructurales.composite.Actividad;

class ReservaBuilderTest {

    private final Cliente ana = new Cliente("1", "Ana", "300", "ana@mail.com", "Colombia", "Español");
    private final Guia tom = new Guia("G1", "Tom", List.of("Inglés"), "300", 100_000);
    private final Actividad act = new Actividad("A1", "Cocora", "Salento", 5, 60_000, 10,
            NivelDificultad.MEDIA, EstadoActividad.DISPONIBLE);
    private final Actividad susp = new Actividad("A2", "Tirolesa", "Calarcá", 2, 30_000, 8,
            NivelDificultad.ALTA, EstadoActividad.SUSPENDIDA);
    private final LocalDate fecha = LocalDate.now().plusDays(5);

    @Test
    void construyeConOpcionales() {
        Reserva r = new ReservaBuilder(ana, act, fecha, 4)
                .conTransporte().conSeguro().conObservaciones("Alergia").construir();
        assertTrue(r.tieneTransporte() && r.tieneSeguro());
        assertEquals("Alergia", r.getObservaciones());
    }

    @Test
    void opcionalesPorDefecto() {
        Reserva r = new ReservaBuilder(ana, act, fecha, 2).construir();
        assertFalse(r.tieneGuia());
        assertEquals("", r.getObservaciones());
    }

    @Test
    void viajerosEntreUnoYQuince() {
        assertThrows(IllegalArgumentException.class, () -> new ReservaBuilder(ana, act, fecha, 0).construir());
        assertThrows(IllegalArgumentException.class, () -> new ReservaBuilder(ana, act, fecha, 16).construir());
        assertDoesNotThrow(() -> new ReservaBuilder(ana, act, fecha, 15).construir());
    }

    @Test
    void rechazaFechaPasadaClienteOProductoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new ReservaBuilder(ana, act, LocalDate.now().minusDays(1), 2).construir());
        assertThrows(IllegalArgumentException.class, () -> new ReservaBuilder(null, act, fecha, 2).construir());
        assertThrows(IllegalArgumentException.class, () -> new ReservaBuilder(ana, null, fecha, 2).construir());
    }

    @Test
    void rechazaGuiaSinIdiomaOActividadSuspendida() {
        assertThrows(IllegalArgumentException.class,
                () -> new ReservaBuilder(ana, act, fecha, 2).conGuia(tom).construir());
        assertThrows(IllegalArgumentException.class, () -> new ReservaBuilder(ana, susp, fecha, 2).construir());
    }
}