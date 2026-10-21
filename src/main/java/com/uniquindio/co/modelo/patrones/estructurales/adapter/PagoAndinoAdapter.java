package com.uniquindio.co.modelo.patrones.estructurales.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uniquindio.co.modelo.patrones.estructurales.adapter.externo.PagoAndinoAPI;

public class PagoAndinoAdapter implements PasarelaPago {

    public static class RegistroCobro {
        private final String referencia;
        private final long valorCentavos;
        private final boolean aprobado;
        private final String detalle;

        public RegistroCobro(String referencia, long valorCentavos, boolean aprobado, String detalle) {
            this.referencia = referencia;
            this.valorCentavos = valorCentavos;
            this.aprobado = aprobado;
            this.detalle = detalle;
        }
        public String getReferencia() { return referencia; }
        public long getValorCentavos() { return valorCentavos; }
        public boolean isAprobado() { return aprobado; }
        public String getDetalle() { return detalle; }
    }

    private static final int CODIGO_APROBADO = 0;
    private static final int CODIGO_FONDOS_INSUFICIENTES = 51;
    private static final int CODIGO_TARJETA_INVALIDA = 14;
    private static final int CODIGO_PASARELA_NO_DISPONIBLE = 91;
    private static final int CENTAVOS_POR_PESO = 100;

    private final PagoAndinoAPI api;
    private final List<RegistroCobro> cobros = new ArrayList<>();

    public PagoAndinoAdapter() {
        this.api = new PagoAndinoAPI();
    }

    public PagoAndinoAdapter(PagoAndinoAPI api) {
        this.api = api;
    }

    @Override
    public ResultadoPago cobrar(String numeroTarjeta, double valorPesos, String referencia) {
        long centavos = Math.round(valorPesos * CENTAVOS_POR_PESO);
        int codigo = api.procesarCobro(numeroTarjeta, centavos, referencia);
        ResultadoPago resultado = traducir(codigo);
        cobros.add(new RegistroCobro(referencia, centavos, resultado.isAprobado(),
                resultado.isAprobado() ? "Aprobado" : resultado.getMotivo()));
        return resultado;
    }

    /** Cobros enviados a PagoAndino, con el valor exacto en centavos. */
    public List<RegistroCobro> getCobros() {
        return Collections.unmodifiableList(cobros);
    }

    private ResultadoPago traducir(int codigo) {
        switch (codigo) {
            case CODIGO_APROBADO:
                return ResultadoPago.aprobado(api.ultimaAutorizacion());
            case CODIGO_FONDOS_INSUFICIENTES:
                return ResultadoPago.rechazado("Fondos insuficientes");
            case CODIGO_TARJETA_INVALIDA:
                return ResultadoPago.rechazado("Tarjeta inválida");
            case CODIGO_PASARELA_NO_DISPONIBLE:
                return ResultadoPago.rechazado("Pasarela no disponible");
            default:
                return ResultadoPago.rechazado("Error desconocido (código " + codigo + ")");
        }
    }
}