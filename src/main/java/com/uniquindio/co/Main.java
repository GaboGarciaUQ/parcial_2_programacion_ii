package com.uniquindio.co;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Cliente;
import com.uniquindio.co.modelo.entidades.Guia;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.entidades.enums.CanalVoucher;
import com.uniquindio.co.modelo.entidades.enums.EstadoActividad;
import com.uniquindio.co.modelo.entidades.enums.NivelDificultad;
import com.uniquindio.co.modelo.patrones.creacionales.builder.ReservaBuilder;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.PagoAndinoAdapter;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.PasarelaPago;
import com.uniquindio.co.modelo.patrones.estructurales.composite.Actividad;
import com.uniquindio.co.modelo.patrones.estructurales.composite.PaqueteTuristico;
import com.uniquindio.co.modelo.patrones.estructurales.composite.ProductoTuristico;
import com.uniquindio.co.modelo.patrones.estructurales.facade.ConfirmarReservaFacade;
import com.uniquindio.co.modelo.servicios.GestorCupos;
import com.uniquindio.co.modelo.servicios.ServicioCorreo;
import com.uniquindio.co.modelo.servicios.ValidadorTarjeta;

public class Main {
    public static void main(String []args){

        //PRUEBAS

        Agencia rutaverde = new Agencia("Ruta Verde", "900", "RNT-1", "dir", "tel", "c@c.com", "web");

        //RN-001
        System.out.println("PRUEBA RN-001: ");

        Cliente c001 = new Cliente("C-001", "Leo", "123421", "leo@correo.com", "Colombia", "Español");
        Actividad a001 = new Actividad("A-001", "Cocora", "Salento", 5, 60_000, 10,
        NivelDificultad.MEDIA, EstadoActividad.DISPONIBLE);
        Reserva r001 = new ReservaBuilder(c001, a001, LocalDate.of(2026, 11, 15), 4).construir();

        //Caso con resultado esperado true

        System.out.println(r001.viajaEnPeriodo(LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 20)));

        //Caso con resultado esperado false

        System.out.println(r001.viajaEnPeriodo(LocalDate.of(2026, 11, 16), LocalDate.of(2026, 11, 20)));

        //RN-002
        System.out.println("PRUEBA RN-002: ");

        //Caso que acepta

        PaqueteTuristico pt001 = new PaqueteTuristico("PT-001","Aventura Quindio", 15);
        System.out.println(pt001.getDescuento()+"%");

        //Caso que rompe

        try{
            PaqueteTuristico pt002 = new PaqueteTuristico("PT-002","Aventura Salento", 25);
            System.out.println(pt001.getDescuento()+"%");
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        //RN-003
        System.out.println("PRUEBA RN-003: ");

        Actividad a002 = new Actividad("A-002", "Peñas Blancas", "Calarca", 8, 100000, 15, NivelDificultad.MEDIA, EstadoActividad.DISPONIBLE);
        PaqueteTuristico pt002 = new PaqueteTuristico("PT-002", "Aventura Calarca", 0);
        pt002.agregarContenido(a002);
        Cliente c002 = new Cliente("C-002","Joe","39221","Joe@yahoo.com","Venezuela","Español");
        Reserva r002 = new ReservaBuilder(c002,pt002,LocalDate.of(2027,02,11),2).conTransporte().construir();

        //Caso que cumple

        if(r002.calcularValor()==270000.0){
            System.out.println(true);
        }else{
            System.out.println(false);
        }
        //Caso que rompe

        if(r002.calcularValor()==235000){
            System.out.println(true);
        }else{
            System.out.println(false);
        }

        //RN-004
        System.out.println("PRUEBA RN-004: ");

        Guia g001 = new Guia("G-001","Pablo", List.of("Español","Ingles","Italiano"),"3489723",120000);
        Reserva r003 = new ReservaBuilder(c001, a002, LocalDate.now(), 4).conGuia(g001).construir();
        Actividad a003 = new Actividad("A-003", "Reserva Kasaguadua", "Salento", 9, 100000, 12, NivelDificultad.BAJA, EstadoActividad.DISPONIBLE);
        Reserva r004 = new ReservaBuilder(c001, a003, LocalDate.now(), 7).conGuia(g001).construir();

        //Caso que cumple

        if(r003.calcularCostoGuia()==120000.0){
            System.out.println(true);
        }else{
            System.out.println(false);
        }

        //Caso que rompe

        if(r004.calcularCostoGuia()==135000.0){
            System.out.println(true);
        }else{
            System.out.println(false);
        }

        //RN-005
        System.out.println("PRUEBA RN-005: ");

        GestorCupos gc001 = new GestorCupos(rutaverde);
        ServicioCorreo sc001 = new ServicioCorreo();
        PasarelaPago pp001 = new PagoAndinoAdapter();
        ConfirmarReservaFacade crf001 = new ConfirmarReservaFacade(rutaverde, gc001, pp001, sc001);

        // Caso que cumple

        crf001.confirmar(r001, "4539 1488 0343 6467", CanalVoucher.ASESOR);

        // Caso que rompe

        try{
            crf001.confirmar(r002, "4539 1488 0343 6468", CanalVoucher.ASESOR);
        } catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
        
    }

}
