package com.uniquindio.co.modelo.producto;
import java.util.List;
import com.uniquindio.co.modelo.Actividad;

public interface ProductoTuristico {
    
    String getCodigo();
    String getNombre();
    double getPrecioPorPersona();
    int getDuracionHoras();
    List<Actividad> getActividades();
    boolean tieneActividadSuspendida();
    boolean contiene(ProductoTuristico otro);

}
