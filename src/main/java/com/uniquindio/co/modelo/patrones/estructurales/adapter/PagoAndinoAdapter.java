package com.uniquindio.co.modelo.patrones.estructurales.adapter;

import com.uniquindio.co.modelo.patrones.estructurales.adapter.externo.PagoAndinoAPI;

public class PagoAndinoAdapter implements PasarelaPago {

    private static final int CODIGO_APROBADO = 0;
    private static final int CODIGO_FONDOS_INSUFICIENTES = 51;
    private static final int CODIGO_TARJETA_INVALIDA = 14;
    private static final int CODIGO_PASARELA_NO_DISPONIBLE = 91;
    private static final int CENTAVOS_POR_PESO = 100;

    private final PagoAndinoAPI api;

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
        return traducir(codigo);
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