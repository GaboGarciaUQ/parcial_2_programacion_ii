package com.uniquindio.co.modelo.patrones.creacionales.factory;

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
import com.uniquindio.co.modelo.patrones.estructurales.composite.Actividad;

class VoucherTest {

    private Agencia agencia;
    private Reserva reserva;

    @BeforeEach
    void preparar() {
        agencia = new Agencia("RutaVerde", "900", "RNT-1", "dir", "tel", "c@c.com", "web");
        Cliente ana = new Cliente("1", "Ana", "300", "ana@mail.com", "Colombia", "Español");
        Actividad act = new Actividad("A1", "Cocora", "Salento", 5, 60_000, 10,
                NivelDificultad.MEDIA, EstadoActividad.DISPONIBLE);
        reserva = new ReservaBuilder(ana, act, LocalDate.now().plusDays(5), 4).construir();
        reserva.confirmar(1);
    }

    private String emitir(CanalVoucher canal) {
        return VoucherFactory.crearVoucher(canal, reserva, agencia).emitir();
    }

    @Test
    void cadaCanalCreaSuSubclase() {
        assertInstanceOf(VoucherAsesor.class, VoucherFactory.crearVoucher(CanalVoucher.ASESOR, reserva, agencia));
        assertInstanceOf(VoucherKiosco.class, VoucherFactory.crearVoucher(CanalVoucher.KIOSCO, reserva, agencia));
        assertInstanceOf(VoucherMovil.class, VoucherFactory.crearVoucher(CanalVoucher.MOVIL, reserva, agencia));
    }

    @Test
    void encabezadoYPieSonFijosEnTodosLosCanales() {
        for (CanalVoucher canal : CanalVoucher.values()) {
            String t = emitir(canal);
            assertTrue(t.startsWith("RutaVerde - RNT: RNT-1"), canal.toString());
            assertTrue(t.contains("48 horas"), canal.toString());
        }
    }

    @Test
    void kioscoEsUnaSolaLineaConCodigo() {
        String t = emitir(CanalVoucher.KIOSCO);
        assertFalse(t.contains("\n"));
        assertTrue(t.contains("COD RV-1-"));
    }

    @Test
    void asesorEsDetalladoYMovilTienePropioFormato() {
        assertTrue(emitir(CanalVoucher.ASESOR).contains("Cocora"));
        assertTrue(emitir(CanalVoucher.MOVIL).contains("Reserva #1"));
    }
}