package com.uniquindio.co.modelo.patrones.estructurales.composite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uniquindio.co.modelo.entidades.enums.EstadoActividad;
import com.uniquindio.co.modelo.entidades.enums.NivelDificultad;

class CompositeTest {

    private Actividad cocora, cafe, tirolesa;
    private PaqueteTuristico salento, total;

    @BeforeEach
    void preparar() {
        cocora = new Actividad("A1", "Cocora", "Salento", 5, 60_000, 10, NivelDificultad.MEDIA, EstadoActividad.DISPONIBLE);
        cafe = new Actividad("A2", "Café", "Salento", 4, 50_000, 10, NivelDificultad.BAJA, EstadoActividad.DISPONIBLE);
        tirolesa = new Actividad("A3", "Tirolesa", "Calarcá", 2, 30_000, 8, NivelDificultad.ALTA, EstadoActividad.SUSPENDIDA);
        salento = new PaqueteTuristico("P1", "Salento", 10);
        salento.agregarContenido(cocora);
        salento.agregarContenido(cafe);
        total = new PaqueteTuristico("P2", "Total", 20);
        total.agregarContenido(salento);
    }

    @Test
    void paqueteAplicaDescuentoYSumaDuracion() {
        assertEquals(99_000, salento.getPrecioPorPersona(), 0.01);   // 110.000 x 0,90
        assertEquals(9, salento.getDuracionHoras());
    }

    @Test
    void paquetesAnidadosSeConsultanIgualQueUnaActividad() {
        assertEquals(79_200, total.getPrecioPorPersona(), 0.01);     // 99.000 x 0,80
        assertEquals(2, total.getActividades().size());
        assertEquals(1, cocora.getActividades().size());
    }

    @Test
    void detectaActividadSuspendidaEnCualquierNivel() {
        PaqueteTuristico riesgo = new PaqueteTuristico("P3", "Riesgo", 0);
        riesgo.agregarContenido(tirolesa);
        total.agregarContenido(riesgo);
        assertTrue(total.tieneActividadSuspendida());
    }

    @Test
    void rechazaCiclosYActividadesRepetidas() {
        assertThrows(IllegalArgumentException.class, () -> salento.agregarContenido(salento));
        assertThrows(IllegalArgumentException.class, () -> salento.agregarContenido(total));
        assertThrows(IllegalArgumentException.class, () -> total.agregarContenido(cocora));
    }

    @Test
    void descuentoEntreCeroYVeinte() {
        assertThrows(IllegalArgumentException.class, () -> new PaqueteTuristico("X", "X", 21));
        assertThrows(IllegalArgumentException.class, () -> new PaqueteTuristico("X", "X", -1));
        assertDoesNotThrow(() -> new PaqueteTuristico("X", "X", 20));
    }
}