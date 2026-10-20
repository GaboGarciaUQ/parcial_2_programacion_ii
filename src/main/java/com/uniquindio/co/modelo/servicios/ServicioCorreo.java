package com.uniquindio.co.modelo.servicios;

public class ServicioCorreo {

    public void enviar(String destinatario, String asunto, String cuerpo) {
        System.out.println("[CORREO] Para: " + destinatario);
        System.out.println("[CORREO] Asunto: " + asunto);
        System.out.println("[CORREO] " + cuerpo);
    }
}