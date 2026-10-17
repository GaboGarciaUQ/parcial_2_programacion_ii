package com.uniquindio.co.modelo.producto;

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
