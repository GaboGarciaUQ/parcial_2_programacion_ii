package com.uniquindio.co.modelo.patrones.estructurales.composite;

import java.util.List;

public interface ProductoTuristico {
    String getCodigo();
    String getNombre();
    double getPrecioPorPersona();
    int getDuracionHoras();
    List<Actividad> getActividades();
    boolean tieneActividadSuspendida();
    boolean contiene(ProductoTuristico otro);

}
