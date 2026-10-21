package com.uniquindio.co.modelo.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicioCorreo {

    public static class Correo {
        private final LocalDateTime fecha = LocalDateTime.now();
        private final String destinatario;
        private final String asunto;
        private final String cuerpo;

        public Correo(String destinatario, String asunto, String cuerpo) {
            this.destinatario = destinatario;
            this.asunto = asunto;
            this.cuerpo = cuerpo;
        }
        public LocalDateTime getFecha() { return fecha; }
        public String getDestinatario() { return destinatario; }
        public String getAsunto() { return asunto; }
        public String getCuerpo() { return cuerpo; }
    }

    private final List<Correo> enviados = new ArrayList<>();

    public void enviar(String destinatario, String asunto, String cuerpo) {
        enviados.add(new Correo(destinatario, asunto, cuerpo));
    }

    public List<Correo> getEnviados() {
        return Collections.unmodifiableList(enviados);
    }
}