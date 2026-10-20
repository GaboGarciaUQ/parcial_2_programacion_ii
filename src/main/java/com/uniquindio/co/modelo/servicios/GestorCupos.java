package com.uniquindio.co.modelo.servicios;

import java.time.LocalDate;

import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.patrones.estructurales.composite.Actividad;
import com.uniquindio.co.modelo.patrones.estructurales.composite.ProductoTuristico;

public class GestorCupos {

    private final Agencia agencia;

    public GestorCupos(Agencia agencia) {
        this.agencia = agencia;
    }

    // Viajeros ya reservados en esa actividad y fecha (solo reservas registradas)
    public int cuposOcupados(Actividad actividad, LocalDate fecha) {
        int ocupados = 0;
        for (Reserva reserva : agencia.getListaReservas()) {
            if (reserva.getFechaViaje().isEqual(fecha)
                    && reserva.getProducto().getActividades().contains(actividad)) {
                ocupados += reserva.getNumViajeros();
            }
        }
        return ocupados;
    }

    public int cuposDisponibles(Actividad actividad, LocalDate fecha) {
        return actividad.getCupomaximo() - cuposOcupados(actividad, fecha);
    }

    public boolean hayCupo(Actividad actividad, LocalDate fecha, int viajeros) {
        return cuposOcupados(actividad, fecha) + viajeros <= actividad.getCupomaximo();
    }

    // Paso 1 del Facade: debe haber cupo en TODAS las actividades del producto
    public boolean hayCupo(ProductoTuristico producto, LocalDate fecha, int viajeros) {
        for (Actividad actividad : producto.getActividades()) {
            if (!hayCupo(actividad, fecha, viajeros)) {
                return false;
            }
        }
        return true;
    }
}