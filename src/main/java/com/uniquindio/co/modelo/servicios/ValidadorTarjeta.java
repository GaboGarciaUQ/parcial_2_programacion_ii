package com.uniquindio.co.modelo.servicios;

public class ValidadorTarjeta {

    private static final int MIN_DIGITOS = 13;
    private static final int MAX_DIGITOS = 19;

    private ValidadorTarjeta() { }

    public static boolean esValida(String numero) {
        if (numero == null) {
            return false;
        }
        String limpio = numero.replace(" ", "");
        if (limpio.length() < MIN_DIGITOS || limpio.length() > MAX_DIGITOS) {
            return false;
        }

        int suma = 0;
        boolean duplicar = false;
        for (int i = limpio.length() - 1; i >= 0; i--) {
            char c = limpio.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
            int digito = c - '0';
            if (duplicar) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
            duplicar = !duplicar;
        }
        return suma % 10 == 0;
    }
}