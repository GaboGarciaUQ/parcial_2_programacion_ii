package com.uniquindio.co;

import java.time.LocalDate;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Cliente;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.entidades.enums.EstadoActividad;
import com.uniquindio.co.modelo.entidades.enums.NivelDificultad;
import com.uniquindio.co.modelo.patrones.creacionales.builder.ReservaBuilder;
import com.uniquindio.co.modelo.patrones.estructurales.composite.Actividad;

public class Main {
    public static void main(String []args){

        //PRUEBAS

        Agencia rutaverde = new Agencia("Ruta Verde", "900", "RNT-1", "dir", "tel", "c@c.com", "web");

        //RN-001

        Cliente ana = new Cliente("1", "Ana", "300", "ana@mail.com", "Colombia", "Español");
        Actividad act = new Actividad("A1", "Cocora", "Salento", 5, 60_000, 10,
        NivelDificultad.MEDIA, EstadoActividad.DISPONIBLE);
        Reserva reserva = new ReservaBuilder(ana, act, LocalDate.now().plusDays(5), 4).construir();

        //Caso que da true
        System.out.println(reserva.viajaEnPeriodo(LocalDate.now().plusDays(2), LocalDate.now().plusDays(6)));

        //Caso que da false
        System.out.println(reserva.viajaEnPeriodo(LocalDate.now().plusDays(6), LocalDate.now().plusDays(9)));

    }

}
