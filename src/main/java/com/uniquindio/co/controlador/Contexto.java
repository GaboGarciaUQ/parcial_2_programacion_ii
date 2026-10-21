package com.uniquindio.co.controlador;

import java.util.function.Consumer;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.patrones.estructurales.facade.ConfirmarReservaFacade;
import com.uniquindio.co.modelo.servicios.ServicioCorreo;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.PagoAndinoAdapter;

/** Objetos del modelo y estado compartido entre todos los controladores. */
public class Contexto {

    private final Agencia agencia;
    private final ConfirmarReservaFacade facade;
    private Reserva pendiente;
    private Consumer<String> navegador;
    private final ServicioCorreo correo;
    private final PagoAndinoAdapter pasarela;


public Contexto(Agencia agencia, ConfirmarReservaFacade facade, ServicioCorreo correo,
                PagoAndinoAdapter pasarela) {
    this.agencia = agencia;
    this.facade = facade;
    this.correo = correo;
    this.pasarela = pasarela;
}




    public Agencia getAgencia() { return agencia; }
    public PagoAndinoAdapter getPasarela() { return pasarela; }

    public ConfirmarReservaFacade getFacade() { return facade; }

    /** Reserva armada con el Builder que aún no se ha pagado ni confirmado. */
    public Reserva getPendiente() { return pendiente; }

    public ServicioCorreo getCorreo() { return correo; }
    

    

    public void setPendiente(Reserva pendiente) { this.pendiente = pendiente; }

    /** Lo registra MainController para que cualquier pantalla pueda cambiar de vista. */
    public void setNavegador(Consumer<String> navegador) { this.navegador = navegador; }

    public void irA(String vista) {
        if (navegador == null) {
            throw new IllegalStateException("El navegador no está configurado");
        }
        navegador.accept(vista);
    }
    
}