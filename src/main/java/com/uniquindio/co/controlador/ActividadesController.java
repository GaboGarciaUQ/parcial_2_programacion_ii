package com.uniquindio.co.controlador;

import com.uniquindio.co.modelo.entidades.enums.EstadoActividad;
import com.uniquindio.co.modelo.entidades.enums.NivelDificultad;
import com.uniquindio.co.modelo.patrones.estructurales.composite.Actividad;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ActividadesController extends BaseController {

    @FXML private TextField tfCodigo, tfNombre, tfMunicipio, tfHoras, tfPrecio, tfCupo;
    @FXML private ComboBox<NivelDificultad> cbDificultad;
    @FXML private ComboBox<EstadoActividad> cbEstado;
    @FXML private TableView<Actividad> tabla;

    @Override
    protected void alIniciar() {
        cbDificultad.setItems(FXCollections.observableArrayList(NivelDificultad.values()));
        cbEstado.setItems(FXCollections.observableArrayList(EstadoActividad.values()));
        cbEstado.setValue(EstadoActividad.DISPONIBLE);
        ajustar(tabla);
        tabla.getColumns().add(col("Código", Actividad::getCodigo));
        tabla.getColumns().add(col("Nombre", Actividad::getNombre));
        tabla.getColumns().add(col("Municipio", Actividad::getMunicipio));
        tabla.getColumns().add(col("Horas", a -> String.valueOf(a.getDuracionHoras())));
        tabla.getColumns().add(col("Precio p/p", a -> dinero(a.getPrecioPorPersona())));
        tabla.getColumns().add(col("Cupo/día", a -> String.valueOf(a.getCupomaximo())));
        tabla.getColumns().add(col("Dificultad", a -> a.getDificultad().name()));
        tabla.getColumns().add(col("Estado", a -> a.getEstado().name()));
        tabla.getSelectionModel().selectedItemProperty().addListener((o, x, a) -> {
            if (a != null) {
                tfCodigo.setText(a.getCodigo()); tfNombre.setText(a.getNombre());
                tfMunicipio.setText(a.getMunicipio());
                tfHoras.setText(String.valueOf(a.getDuracionHoras()));
                tfPrecio.setText(String.valueOf((long) a.getPrecioPorPersona()));
                tfCupo.setText(String.valueOf(a.getCupomaximo()));
                cbDificultad.setValue(a.getDificultad()); cbEstado.setValue(a.getEstado());
            }
        });
        refrescar();
    }

    @FXML
    private void registrar() {
        if (hayVacios(tfCodigo, tfNombre, tfMunicipio, tfHoras, tfPrecio, tfCupo)) return;
        if (cbDificultad.getValue() == null || cbEstado.getValue() == null) {
            error("Selecciona la dificultad y el estado."); return;
        }
        try {
            ctx.getAgencia().registrarActividad(tfCodigo.getText().trim(), tfNombre.getText().trim(),
                    tfMunicipio.getText().trim(), Integer.parseInt(tfHoras.getText().trim()),
                    Double.parseDouble(tfPrecio.getText().trim()), Integer.parseInt(tfCupo.getText().trim()),
                    cbDificultad.getValue(), cbEstado.getValue());
            refrescar();
            limpiar();
        } catch (NumberFormatException e) {
            error("Horas, precio y cupo deben ser números.");
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    @FXML
    private void actualizar() {
        Actividad a = tabla.getSelectionModel().getSelectedItem();
        if (a == null) { error("Selecciona una actividad de la tabla."); return; }
        if (hayVacios(tfNombre, tfMunicipio, tfHoras, tfPrecio, tfCupo)) return;
        try {
            int horas = Integer.parseInt(tfHoras.getText().trim());
            double precio = Double.parseDouble(tfPrecio.getText().trim());
            int cupo = Integer.parseInt(tfCupo.getText().trim());
            if (horas <= 0 || precio <= 0 || cupo <= 0) {
                error("Duración, precio y cupo deben ser mayores que cero."); return;
            }
            a.setNombre(tfNombre.getText().trim()); a.setMunicipio(tfMunicipio.getText().trim());
            a.setDuracionHoras(horas); a.setPrecioPorPersona(precio); a.setCupomaximo(cupo);
            a.setDificultad(cbDificultad.getValue()); a.setEstado(cbEstado.getValue());
            tabla.refresh();
        } catch (NumberFormatException e) {
            error("Horas, precio y cupo deben ser números.");
        }
    }

    @FXML
    private void limpiar() {
        tfCodigo.clear(); tfNombre.clear(); tfMunicipio.clear(); tfHoras.clear();
        tfPrecio.clear(); tfCupo.clear(); cbDificultad.setValue(null);
        cbEstado.setValue(EstadoActividad.DISPONIBLE);
        tabla.getSelectionModel().clearSelection();
    }

    private void refrescar() {
        tabla.setItems(FXCollections.observableArrayList(ctx.getAgencia().getListaActividades()));
    }
}
