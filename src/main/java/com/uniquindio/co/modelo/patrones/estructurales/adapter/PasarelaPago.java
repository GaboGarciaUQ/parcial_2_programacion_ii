package com.uniquindio.co.modelo.patrones.estructurales.adapter;

public interface PasarelaPago {
    ResultadoPago cobrar(String numeroTarjeta, double valorPesos, String referencia);
}    


