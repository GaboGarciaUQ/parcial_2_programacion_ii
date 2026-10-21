package com.uniquindio.co.modelo.patrones.estructurales.adapter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PagoAndinoAdapterTest {

    private final PasarelaPago pasarela = new PagoAndinoAdapter();
    private static final String TARJETA = "4539 1488 0343 6467";

    @Test
    void cobroNormalEsAprobadoConAutorizacion() {
        ResultadoPago r = pasarela.cobrar(TARJETA, 1_291_000, "RES-1");
        assertTrue(r.isAprobado());
        assertTrue(r.getAutorizacion().startsWith("PA-"));
    }

    @Test
    void convierteALosCentavosCorrectos() {
        // 3.000.000 pesos = 300.000.000 centavos, tope de PagoAndino
        assertTrue(pasarela.cobrar(TARJETA, 3_000_000, "RES-2").isAprobado());
        assertFalse(pasarela.cobrar(TARJETA, 3_000_001, "RES-3").isAprobado());
    }

    @Test
    void traduceElCodigoDeRechazoAMotivo() {
        ResultadoPago r = pasarela.cobrar(TARJETA, 5_000_000, "RES-4");
        assertEquals("Fondos insuficientes", r.getMotivo());
        assertNull(r.getAutorizacion());
    }
}